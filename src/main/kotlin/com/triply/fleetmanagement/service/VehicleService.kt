package com.triply.fleetmanagement.service

import org.springframework.web.multipart.MultipartFile


interface VehicleService {
    fun insertVehicleToDB(companyId: Long, file: MultipartFile) : Int
    fun insertVehicleUsageCSVToDB(file: MultipartFile) : Int
}