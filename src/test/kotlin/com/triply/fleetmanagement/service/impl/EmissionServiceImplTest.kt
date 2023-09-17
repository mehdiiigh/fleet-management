package com.triply.fleetmanagement.service.impl

import com.triply.fleetmanagement.domain.Company
import com.triply.fleetmanagement.domain.Employee
import com.triply.fleetmanagement.domain.Vehicle
import com.triply.fleetmanagement.domain.VehicleUsage
import com.triply.fleetmanagement.repository.CompanyRepository
import com.triply.fleetmanagement.repository.EmployeeRepository
import com.triply.fleetmanagement.repository.VehicleUsageRepository
import com.triply.fleetmanagement.service.dto.EmissionDetailDto
import com.triply.fleetmanagement.service.dto.EmissionDto
import com.triply.fleetmanagement.service.dto.SuggestionDetailDto
import com.triply.fleetmanagement.service.dto.SuggestionDto
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import java.util.*

class EmissionServiceImplTest {

    private lateinit var emissionService: EmissionServiceImpl
    @Mock
    private lateinit var companyRepository: CompanyRepository
    @Mock
    private lateinit var employeeRepository: EmployeeRepository
    @Mock
    private lateinit var vehicleUsageRepository: VehicleUsageRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        emissionService = EmissionServiceImpl(companyRepository, employeeRepository, vehicleUsageRepository)
    }

    @Test
    fun calculateEmissionsByEmployeeExists() {
        val employeeId = 1L
        val vehicle1 = Vehicle(
                type = "Car",
                emissionFactors = 2.0,
                company = Company(name = "triply")
        )
        val employee1 = Employee(id = 2L, company = Company(name = "triply"))
        val vehicleUsage1 = VehicleUsage(
                vehicle = vehicle1,
                employee = employee1,
                averageWeeklyMileage = 100.0
        )
        val vehicleUsageList = listOf(vehicleUsage1)
        `when`(employeeRepository.existsById(employeeId)).thenReturn(true)
        `when`(vehicleUsageRepository.findByEmployeeId(employeeId)).thenReturn(vehicleUsageList)

        val result = emissionService.calculateEmissionsByEmployee(employeeId)
        val emissionDto = EmissionDto(
                details = mutableListOf(
                        EmissionDetailDto(
                                vehicleType = "Car",
                                vehicleEmissionFactors = 2.0,
                                averageWeeklyMileage = 100.0,
                                calculatedEmission = 200.0
                        )
                ),
                sumOfAverageWeeklyMileage = 100.0,
                sumOfCalculatedEmissions = 200.0
        )

        assertEquals(emissionDto, result)
    }

    @Test
    fun calculateEmissionsByEmployeeNotExists() {
        val employeeId = 2L
        `when`(employeeRepository.existsById(employeeId)).thenReturn(false)

        try {
            emissionService.calculateEmissionsByEmployee(employeeId)
            fail("Expected ResponseStatusException")
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.NOT_FOUND.value(), 404)
        }
    }

    @Test
    fun calculateEmissionsByCompanyExists() {
        val companyId = 1L
        val vehicle1 = Vehicle(
                type = "Car",
                emissionFactors = 2.0,
                company = Company(name = "triply")
        )
        val employee1 = Employee(id = 2L, company = Company(name = "triply"))
        val vehicleUsage1 = VehicleUsage(
                vehicle = vehicle1,
                employee = employee1,
                averageWeeklyMileage = 100.0
        )
        val vehicleUsageList = listOf(vehicleUsage1)
        `when`(companyRepository.existsById(companyId)).thenReturn(true)
        `when`(vehicleUsageRepository.findByCompanyId(companyId)).thenReturn(vehicleUsageList)

        val result = emissionService.calculateEmissionsByCompany(companyId)
        val emissionDto = EmissionDto(
                details = mutableListOf(
                        EmissionDetailDto(
                                vehicleType = "Car",
                                vehicleEmissionFactors = 2.0,
                                averageWeeklyMileage = 100.0,
                                calculatedEmission = 200.0
                        )
                ),
                sumOfAverageWeeklyMileage = 100.0,
                sumOfCalculatedEmissions = 200.0
        )
        assertEquals(emissionDto, result)
    }

    @Test
    fun calculateEmissionsByCompanyNotExists() {
        val companyId = 2L
        `when`(companyRepository.existsById(companyId)).thenReturn(false)

        try {
            emissionService.calculateEmissionsByCompany(companyId)
            fail("Expected ResponseStatusException")
        } catch (e: ResponseStatusException) {
            assertEquals(HttpStatus.NOT_FOUND.value(), 404)
        }
    }

    @Test
    fun testSuggestionEmissionsByEmployeeWhenEmployeeExists() {
        val employeeId = 1L
        val company = Company(id = 1, name = "triply")
        val vehicleUsageList = listOf(
                VehicleUsage(
                        vehicle = Vehicle(type = "Car", createdAt = Date(), emissionFactors = 4.0, company),
                        employee = Employee(id = 1L, company = Company(name = "Example Company")),
                        averageWeeklyMileage = 100.0
                )
        )

        `when`(employeeRepository.existsById(employeeId)).thenReturn(true)
        `when`(vehicleUsageRepository.findByEmployeeId(employeeId)).thenReturn(vehicleUsageList)

        val result = emissionService.suggestionEmissionsByEmployee(employeeId)

        assertNotNull(result)
        val suggestionDto = SuggestionDto(
                details = mutableListOf(
                        SuggestionDetailDto(
                                vehicleType = "Electric",
                                vehicleEmissionFactors = 2.0,
                                averageWeeklyMileage = 100.0,
                                calculatedEmission = 400.0,
                                suggestionEmission = 200.0
                        )
                ),
                sumOfAverageWeeklyMileage = 100.0,
                sumOfCalculatedEmissions = 400.0,
                sumOfSuggestionEmission = 200.0
        )

        assertEquals(suggestionDto, result)
    }

    @Test
    fun testSuggestionEmissionsByEmployeeWhenEmployeeDoesNotExist() {
        val employeeId = 1L

        `when`(employeeRepository.existsById(employeeId)).thenReturn(false)

        assertThrows<ResponseStatusException> {
            emissionService.suggestionEmissionsByEmployee(employeeId)
        }
    }

    @Test
    fun testSuggestionEmissionsByCompanyWhenCompanyExists() {
        val companyId = 1L
        val company = Company(id = 1, name = "triply")
        val vehicleUsageList = listOf(
                VehicleUsage(
                        vehicle = Vehicle(type = "Car", createdAt = Date(), emissionFactors = 4.0, company),
                        employee = Employee(id = 1L, company = Company(name = "Example Company")),
                        averageWeeklyMileage = 100.0
                )
        )

        `when`(companyRepository.existsById(companyId)).thenReturn(true)
        `when`(vehicleUsageRepository.findByCompanyId(companyId)).thenReturn(vehicleUsageList)

        val result = emissionService.suggestionEmissionsByCompany(companyId)

        assertNotNull(result)
        val suggestionDto = SuggestionDto(
                details = mutableListOf(
                        SuggestionDetailDto(
                                vehicleType = "Electric",
                                vehicleEmissionFactors = 2.0,
                                averageWeeklyMileage = 100.0,
                                calculatedEmission = 400.0,
                                suggestionEmission = 200.0
                        )
                ),
                sumOfAverageWeeklyMileage = 100.0,
                sumOfCalculatedEmissions = 400.0,
                sumOfSuggestionEmission = 200.0
        )

        assertEquals(suggestionDto, result)
    }

    @Test
    fun testSuggestionEmissionsByCompanyWhenCompanyDoesNotExist() {
        val companyId = 1L

        `when`(companyRepository.existsById(companyId)).thenReturn(false)

        assertThrows<ResponseStatusException> {
            emissionService.suggestionEmissionsByCompany(companyId)
        }
    }
}
