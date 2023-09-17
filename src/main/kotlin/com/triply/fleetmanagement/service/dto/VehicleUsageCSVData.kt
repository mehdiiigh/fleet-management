package com.triply.fleetmanagement.service.dto

import com.opencsv.bean.CsvBindByName
import com.triply.fleetmanagement.domain.Employee
import com.triply.fleetmanagement.domain.Vehicle
import com.triply.fleetmanagement.domain.VehicleUsage

data class VehicleUsageCSVData(
    @CsvBindByName(column = "employee_id")
    var employeeId: Long? = null,
    @CsvBindByName(column = "vehicle_type")
    var vehicleType: String? = null,
    @CsvBindByName(column = "average_weekly_mileage")
    var averageWeeklyMileage: Double? = null
)

fun VehicleUsageCSVData.toEntity(employee: Employee, vehicle: Vehicle) = VehicleUsage(
    employee = employee,
    vehicle = vehicle,
    averageWeeklyMileage = averageWeeklyMileage!!,
)
