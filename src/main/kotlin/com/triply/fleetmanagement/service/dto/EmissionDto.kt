package com.triply.fleetmanagement.service.dto

data class EmissionDto(
    var details: MutableList<EmissionDetailDto> = mutableListOf(),
    var sumOfAverageWeeklyMileage: Double = 0.0,
    var sumOfCalculatedEmissions: Double = 0.0
)

data class EmissionDetailDto(
    var vehicleType: String? = null,
    var vehicleEmissionFactors: Double = 0.0,
    var averageWeeklyMileage : Double = 0.0,
    var calculatedEmission: Double = 0.0,
)