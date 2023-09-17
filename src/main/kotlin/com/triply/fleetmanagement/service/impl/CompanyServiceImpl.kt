package com.triply.fleetmanagement.service.impl

import com.triply.fleetmanagement.domain.Company
import com.triply.fleetmanagement.repository.CompanyRepository
import com.triply.fleetmanagement.service.CompanyService
import com.triply.fleetmanagement.service.dto.CompanyDto
import com.triply.fleetmanagement.service.dto.fromEntity
import com.triply.fleetmanagement.service.dto.toEntity
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class CompanyServiceImpl(val companyRepository: CompanyRepository) : CompanyService {

    override fun save(companyDto: CompanyDto): CompanyDto {
        return companyRepository.save(companyDto.toEntity()).fromEntity()
    }

    override fun findAll(): List<CompanyDto> {
        return companyRepository.findAll().map { it.fromEntity() }
    }

    override fun findOne(id: Long): Optional<Company> {
        return companyRepository.findById(id)
    }
}