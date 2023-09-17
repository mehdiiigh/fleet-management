package com.triply.fleetmanagement.web.rest

import com.triply.fleetmanagement.service.CompanyService
import com.triply.fleetmanagement.service.dto.CompanyDto
import com.triply.fleetmanagement.service.dto.fromEntity
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/api")
class CompanyController(val companyService: CompanyService) {

    @PostMapping("/company")
    @ResponseStatus(CREATED)
    fun createCompany(@RequestBody companyDto: CompanyDto): ResponseEntity<CompanyDto> {
        return ResponseEntity.created(URI("/api/company/" + companyDto.companyId)).body(companyService.save(companyDto))
    }

    @GetMapping("/company/{id}")
    @ResponseStatus(OK)
    fun getCompany(@PathVariable id: Long): ResponseEntity<CompanyDto> {
        val company = companyService.findOne(id)
        return if(!company.isPresent){
            throw ResponseStatusException(NOT_FOUND)
        } else {
            ResponseEntity.ok(company.get().fromEntity())
        }
    }
}