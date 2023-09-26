package com.triply.fleetmanagement.service

import com.triply.fleetmanagement.domain.Vehicle
import com.triply.fleetmanagement.service.dto.EmissionDto
import com.triply.fleetmanagement.service.dto.SuggestionDto

interface EmissionService {
    fun calculateEmissionsByEmployee(employeeId: Long): EmissionDto?
    fun calculateEmissionsByCompany(companyId: Long): EmissionDto?
    fun suggestionEmissionsByEmployee(employeeId: Long): SuggestionDto?
    fun suggestionEmissionsByCompany(companyId: Long): SuggestionDto?
    fun calculate5TopVehicles(): List<Vehicle>
}