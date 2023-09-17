package com.triply.fleetmanagement.service.dto

import com.triply.fleetmanagement.domain.Company

data class CompanyDto(
    var companyId: Long?,
    var companyName: String
)

fun CompanyDto.toEntity() = Company(
    id = companyId,
    name = companyName
)

fun Company.fromEntity() = CompanyDto(
    companyId = id,
    companyName = name
)