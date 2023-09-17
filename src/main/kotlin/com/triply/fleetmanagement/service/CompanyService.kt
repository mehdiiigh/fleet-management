package com.triply.fleetmanagement.service

import com.triply.fleetmanagement.domain.Company
import com.triply.fleetmanagement.service.dto.CompanyDto
import java.util.Optional


interface CompanyService {
    fun save(companyDto: CompanyDto): CompanyDto
    fun findAll(): List<CompanyDto>
    fun findOne(id: Long): Optional<Company>
}