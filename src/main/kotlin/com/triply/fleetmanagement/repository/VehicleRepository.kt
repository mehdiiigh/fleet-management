package com.triply.fleetmanagement.repository

import com.triply.fleetmanagement.domain.Vehicle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository : JpaRepository<Vehicle, String>