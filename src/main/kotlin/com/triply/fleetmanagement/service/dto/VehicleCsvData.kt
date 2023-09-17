package com.triply.fleetmanagement.service.dto

import com.opencsv.bean.CsvBindByName
import com.triply.fleetmanagement.domain.Company
import com.triply.fleetmanagement.domain.Vehicle
import kotlin.random.Random

data class VehicleCsvData(
    @CsvBindByName(column = "vehicle_type")
    var vehicleType: String? = null
)

fun VehicleCsvData.toEntity(company: Company) = Vehicle(
    company = company,
    type = vehicleType,
    emissionFactors = Random.nextDouble(1.0, 5.0)
)