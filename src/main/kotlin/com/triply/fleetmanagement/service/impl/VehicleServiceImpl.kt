package com.triply.fleetmanagement.service.impl

import com.opencsv.bean.HeaderColumnNameMappingStrategy
import com.triply.fleetmanagement.domain.Employee
import com.triply.fleetmanagement.domain.Vehicle
import com.triply.fleetmanagement.repository.CompanyRepository
import com.triply.fleetmanagement.repository.EmployeeRepository
import com.triply.fleetmanagement.repository.VehicleRepository
import com.triply.fleetmanagement.repository.VehicleUsageRepository
import com.triply.fleetmanagement.service.CSVHandler
import com.triply.fleetmanagement.service.VehicleService
import com.triply.fleetmanagement.service.dto.VehicleCsvData
import com.triply.fleetmanagement.service.dto.VehicleUsageCSVData
import com.triply.fleetmanagement.service.dto.toEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class VehicleServiceImpl(
    val vehicleUsageRepository: VehicleUsageRepository,
    val vehicleRepository: VehicleRepository,
    val employeeRepository: EmployeeRepository,
    val companyRepository: CompanyRepository
) : VehicleService {

    override fun insertVehicleToDB(companyId: Long, file: MultipartFile): Int {
        val company = companyRepository.findById(companyId)
        if (!company.isPresent) throw Exception()
        val mappingStrategy: HeaderColumnNameMappingStrategy<VehicleCsvData> = HeaderColumnNameMappingStrategy<VehicleCsvData>()
        mappingStrategy.type = VehicleCsvData::class.java
        val data = CSVHandler<VehicleCsvData>(mappingStrategy).parseCsv(file.inputStream)
        var insert = 0
        if (data.isNotEmpty()) {
            data.forEach { csv ->
                try {
                    vehicleRepository.save(csv.toEntity(company.get()))
                    insert++
                } catch (_: Throwable) {
                }
            }
        }
        return insert
    }

    override fun insertVehicleUsageCSVToDB(file: MultipartFile): Int {
        val mappingStrategy: HeaderColumnNameMappingStrategy<VehicleUsageCSVData> = HeaderColumnNameMappingStrategy<VehicleUsageCSVData>()
        mappingStrategy.type = VehicleUsageCSVData::class.java
        val data = CSVHandler<VehicleUsageCSVData>(mappingStrategy).parseCsv(file.inputStream)
        var insert = 0
        if (data.isNotEmpty()) {
            data.forEach { csv ->
                try {
                    val vehicle = getVehicle(csv.vehicleType!!) ?: return@forEach
                    val employee = getEmployee(csv.employeeId!!, vehicle)
                    vehicleUsageRepository.save(csv.toEntity(employee, vehicle))
                    insert++
                } catch (_: Throwable) {
                }
            }
        }
        return insert
    }

    private fun getVehicle(vehicleId: String) = if (vehicleId.isNotBlank()) {
        vehicleRepository.findById(vehicleId).orElse(null)
    } else null

    private fun getEmployee(employeeId: Long, vehicle: Vehicle) =
        employeeRepository.findById(employeeId)
            .orElseGet { employeeRepository.save(Employee(company = vehicle.company)) }

}