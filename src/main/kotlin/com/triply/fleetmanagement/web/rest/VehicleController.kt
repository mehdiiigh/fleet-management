package com.triply.fleetmanagement.web.rest

import com.triply.fleetmanagement.service.VehicleService
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class VehicleController(
    val vehicleService: VehicleService
) {

    @PostMapping("/upload-vehicles-csv")
    @ResponseStatus(OK)
    fun uploadVehiclesCsv(@RequestParam("companyId", required = true) companyId: Long,
        @RequestParam("file", required = true) file: MultipartFile): ResponseEntity<String> {
        val size = vehicleService.insertVehicleToDB(companyId, file)
        return ResponseEntity.ok("$size vehicle record added to database")
    }

    @PostMapping("/upload-vehicle-usage-csv")
    @ResponseStatus(OK)
    fun uploadVehicleUsageCsv(@RequestParam("file", required = true) file: MultipartFile): ResponseEntity<String> {
        val size = vehicleService.insertVehicleUsageCSVToDB(file)
        return ResponseEntity.ok("$size vehicle usage record added to database")
    }
}