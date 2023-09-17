package com.triply.fleetmanagement.domain

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
class User(
  @Id
  val id: Int,
  @Column(name = "username", unique = true)
  var username: String? = null,
  @Column(name = "password")
  var password: String? = null,
  @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
  @JoinTable(
    name = "user_role",
    joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
    inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
  )
  var roles: Set<Role>? = null
)

