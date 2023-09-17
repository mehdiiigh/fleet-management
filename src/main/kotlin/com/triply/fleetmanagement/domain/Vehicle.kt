package com.triply.fleetmanagement.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "vehicle")
data class Vehicle(
    @Id
    @Column(name = "vehicle_type")
    var type: String? = null,
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    var createdAt: Date? = null,
    @Column(name = "emission_factors")
    var emissionFactors: Double,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company,
)
