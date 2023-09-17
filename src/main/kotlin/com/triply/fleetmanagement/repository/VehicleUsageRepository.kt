package com.triply.fleetmanagement.repository

import com.triply.fleetmanagement.domain.VehicleUsage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface VehicleUsageRepository : JpaRepository<VehicleUsage, Long> {
    @Query("select vu from VehicleUsage vu inner join fetch vu.employee e where e.id = :employeeId")
    fun findByEmployeeId(@Param("employeeId") employeeId: Long) : List<VehicleUsage>

    @Query("select vu from VehicleUsage vu inner join fetch vu.employee e where e.company.id = :companyId")
    fun findByCompanyId(@Param("companyId") companyId: Long) : List<VehicleUsage>
}