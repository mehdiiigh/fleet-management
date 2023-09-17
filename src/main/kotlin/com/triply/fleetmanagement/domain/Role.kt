package com.triply.fleetmanagement.domain

import jakarta.persistence.*

@Entity
@Table(name = "app_role")
class Role(
  @Id
  val id: Int,
  @Column(name = "role_name", updatable = false)
  var roleName: String? = null,
  @ManyToMany(mappedBy = "roles")
  var users: MutableSet<User>? = null
)
