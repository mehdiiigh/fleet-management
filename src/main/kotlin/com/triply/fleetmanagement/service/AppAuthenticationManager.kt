package com.triply.fleetmanagement.service

import com.triply.fleetmanagement.domain.User
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import com.triply.fleetmanagement.repository.UserRepository


@Component
class AppAuthenticationManager(
  private val userRepository: UserRepository,
  val bCryptPasswordEncoder: BCryptPasswordEncoder,
) : AuthenticationManager {
  @Throws(AuthenticationException::class)
  override fun authenticate(authentication: Authentication): Authentication {
    val password = authentication.credentials.toString()
    val user: User = userRepository.findByUsername(authentication.name).orElseThrow {
      UsernameNotFoundException("The username ${authentication.name} doesn't exist")
    }
    if (!bCryptPasswordEncoder.matches(password, user.password)) {
      throw BadCredentialsException("Bad credentials")
    }
    val authorities = ArrayList<GrantedAuthority>()
    if (user.roles != null) {
      user.roles!!.forEach { authorities.add(SimpleGrantedAuthority(it.roleName)) }
    }
    return UsernamePasswordAuthenticationToken(user.username, user.password, authorities)
  }
}
