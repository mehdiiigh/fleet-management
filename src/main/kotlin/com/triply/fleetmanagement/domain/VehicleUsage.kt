package com.triply.fleetmanagement.domain

import jakarta.persistence.*

@Entity
@Table(name = "vehicle_usage")
data class VehicleUsage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_type")
    var vehicle: Vehicle,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    var employee: Employee,
    @Column(name = "average_weekly_mileage" , nullable = false)
    var averageWeeklyMileage: Double,
)