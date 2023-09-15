package com.triply.fleetmanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FleetManagementApplication

fun main(args: Array<String>) {
	runApplication<FleetManagementApplication>(*args)
}
