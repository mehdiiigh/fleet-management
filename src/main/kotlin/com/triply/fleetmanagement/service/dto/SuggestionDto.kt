package com.triply.fleetmanagement.service.dto

data class SuggestionDto(
    var details: MutableList<SuggestionDetailDto> = mutableListOf(),
    var sumOfAverageWeeklyMileage: Double = 0.0,
    var sumOfCalculatedEmissions: Double = 0.0,
    var sumOfSuggestionEmission: Double = 0.0
)

data class SuggestionDetailDto(
    var vehicleType: String? = null,
    var vehicleEmissionFactors : Double = 0.0,
    var averageWeeklyMileage : Double = 0.0,
    var calculatedEmission: Double = 0.0,
    var suggestionEmission: Double = 0.0,
)