package com.triply.fleetmanagement.service.impl

import com.triply.fleetmanagement.domain.Vehicle
import com.triply.fleetmanagement.domain.VehicleUsage
import com.triply.fleetmanagement.repository.CompanyRepository
import com.triply.fleetmanagement.repository.EmployeeRepository
import com.triply.fleetmanagement.repository.VehicleRepository
import com.triply.fleetmanagement.repository.VehicleUsageRepository
import com.triply.fleetmanagement.service.EmissionService
import com.triply.fleetmanagement.service.dto.EmissionDetailDto
import com.triply.fleetmanagement.service.dto.EmissionDto
import com.triply.fleetmanagement.service.dto.SuggestionDetailDto
import com.triply.fleetmanagement.service.dto.SuggestionDto
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class EmissionServiceImpl(
    val companyRepository: CompanyRepository,
    val employeeRepository: EmployeeRepository,
    val vehicleUsageRepository: VehicleUsageRepository,
    val vehicleRepository : VehicleRepository
) : EmissionService {

    private val electricType = "Electric"
    private val electricEmissionFactors = 2.0

    override fun calculateEmissionsByEmployee(employeeId: Long): EmissionDto? {
        return if(employeeRepository.existsById(employeeId)){
            calculateTotalEmissions(vehicleUsageRepository.findByEmployeeId(employeeId))
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun calculateEmissionsByCompany(companyId: Long): EmissionDto? {
        return if(companyRepository.existsById(companyId)){
            calculateTotalEmissions(vehicleUsageRepository.findByCompanyId(companyId))
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun suggestionEmissionsByEmployee(employeeId: Long): SuggestionDto? {
        return if(employeeRepository.existsById(employeeId)){
            suggestions(vehicleUsageRepository.findByEmployeeId(employeeId))
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    override fun suggestionEmissionsByCompany(companyId: Long): SuggestionDto? {
        return if(companyRepository.existsById(companyId)){
            suggestions(vehicleUsageRepository.findByCompanyId(companyId))
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    private fun calculateEmissions(emissionFactors: Double, averageWeeklyMileage: Double): Double {
        // Calculate emissions based on the emission factor and weekly mileage
        return emissionFactors * averageWeeklyMileage
    }

    private fun calculateTotalEmissions(vehicleUsageList :List<VehicleUsage>) : EmissionDto{
        val emissionDto = EmissionDto()
        vehicleUsageList.forEach {
            val emissionDetailDto = EmissionDetailDto()
            emissionDetailDto.vehicleType = it.vehicle.type
            emissionDetailDto.vehicleEmissionFactors = it.vehicle.emissionFactors
            emissionDetailDto.averageWeeklyMileage = it.averageWeeklyMileage
            emissionDto.sumOfAverageWeeklyMileage += emissionDetailDto.averageWeeklyMileage
            emissionDetailDto.calculatedEmission = calculateEmissions(it.vehicle.emissionFactors, it.averageWeeklyMileage)
            emissionDto.sumOfCalculatedEmissions += emissionDetailDto.calculatedEmission
            emissionDto.details.add(emissionDetailDto)
        }
        return emissionDto
    }

    private fun suggestions(vehicleUsageList :List<VehicleUsage>): SuggestionDto {
        val suggestionDto = SuggestionDto()
        vehicleUsageList.forEach {
            val calculatedEmission = calculateEmissions(it.vehicle.emissionFactors, it.averageWeeklyMileage)
            val suggestionEmission = calculateEmissions(electricEmissionFactors, it.averageWeeklyMileage)
            if(suggestionEmission < calculatedEmission){
                val suggestionDetailDto = SuggestionDetailDto()
                suggestionDetailDto.vehicleType = electricType
                suggestionDetailDto.vehicleEmissionFactors = electricEmissionFactors
                suggestionDetailDto.averageWeeklyMileage = it.averageWeeklyMileage
                suggestionDto.sumOfAverageWeeklyMileage += suggestionDetailDto.averageWeeklyMileage
                suggestionDetailDto.calculatedEmission = calculatedEmission
                suggestionDto.sumOfCalculatedEmissions += calculatedEmission
                suggestionDetailDto.suggestionEmission = suggestionEmission
                suggestionDto.sumOfSuggestionEmission += suggestionEmission
                suggestionDto.details.add(suggestionDetailDto)
            }
        }
        return suggestionDto
    }


    override fun calculate5TopVehicles(): List<Vehicle> {
         var defaultWeaklyMillage = 500.0
         val map = mutableMapOf<Double, Vehicle>()

         vehicleRepository.findAll().forEach {
             var emission = calculateEmissions(it.emissionFactors, defaultWeaklyMillage)
             map[emission] = it
         }

         map.toList()
             .sortedBy { (key, value) -> key }
             .toMap()

         val result = mutableListOf<Vehicle>()
         var cout = 0
         val itr = map.keys.iterator()
         while (itr.hasNext() && cout < 5) {
             cout++
             result.add(map[itr.next()]!!)
         }

         return result.toList()
     }
}