package com.triply.fleetmanagement.repository

import com.triply.fleetmanagement.domain.Company
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyRepository : JpaRepository<Company, Long> {
}