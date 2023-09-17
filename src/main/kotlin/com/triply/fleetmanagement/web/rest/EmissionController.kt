package com.triply.fleetmanagement.web.rest

import com.triply.fleetmanagement.service.EmissionService
import com.triply.fleetmanagement.service.dto.EmissionDto
import com.triply.fleetmanagement.service.dto.SuggestionDto
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class EmissionController(
    val emissionService: EmissionService,
) {

    @GetMapping("/emission/employee/{id}")
    @ResponseStatus(OK)
    fun getEmployeeEmissions(@PathVariable id: Long): ResponseEntity<EmissionDto> {
        return ResponseEntity.ok(emissionService.calculateEmissionsByEmployee(id))
    }

    @GetMapping("/emission/company/{id}")
    @ResponseStatus(OK)
    fun getCompanyEmissions(@PathVariable id: Long): ResponseEntity<EmissionDto> {
        return ResponseEntity.ok(emissionService.calculateEmissionsByCompany(id))
    }

    @GetMapping("/emission-suggestion/employee/{id}")
    @ResponseStatus(OK)
    fun getEmployeeSuggestion(@PathVariable id: Long): ResponseEntity<SuggestionDto> {
        return ResponseEntity.ok(emissionService.suggestionEmissionsByEmployee(id))
    }

    @GetMapping("/emission-suggestion/company/{id}")
    @ResponseStatus(OK)
    fun getCompanySuggestion(@PathVariable id: Long): ResponseEntity<SuggestionDto> {
        return ResponseEntity.ok(emissionService.suggestionEmissionsByCompany(id))
    }
}