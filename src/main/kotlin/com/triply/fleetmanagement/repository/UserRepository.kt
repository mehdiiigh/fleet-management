package com.triply.fleetmanagement.repository

import com.triply.fleetmanagement.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Int> {
  fun findByUsername(username: String): Optional<User>
}
