package com.triply.fleetmanagement.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "company")
data class Company (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    var id: Long? = null,
    @Column(name = "company_name", nullable = false)
    var name: String,
    @Column(name = "created_at", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT NOW()")
    var createdAt: Date? = null,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    var employees: List<Employee> = emptyList(),
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    var vehicles: List<Vehicle> = emptyList()
)

