package com.triply.fleetmanagement.config

import com.triply.fleetmanagement.security.JWTAuthenticationFilter
import com.triply.fleetmanagement.security.JWTAuthorizationFilter
import com.triply.fleetmanagement.security.TokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import com.triply.fleetmanagement.service.AppAuthenticationManager


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class WebConfig(
  val securityProperties: SecurityProperties,
  val authenticationManager: AppAuthenticationManager,
  val tokenProvider: TokenProvider
) {
  @Bean
  @Throws(Exception::class)
  fun filterChain(http: HttpSecurity): SecurityFilterChain? {
    return http.cors { config ->
      config.configurationSource(UrlBasedCorsConfigurationSource().also { cors ->
        CorsConfiguration().apply {
          allowedOrigins = listOf("*")
          allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
          allowedHeaders = listOf(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
          )
          exposedHeaders = listOf(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Authorization",
            "Content-Disposition"
          )
          maxAge = 3600
          cors.registerCorsConfiguration("/**", this)
        }
      })
    }
      .csrf { csrf -> csrf.disable() }
      .sessionManagement { sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      }
      .authorizeHttpRequests { authorizeRequests ->
        authorizeRequests
          .requestMatchers(HttpMethod.GET, "/actuator/health/**").permitAll()
          .requestMatchers(HttpMethod.GET, "/actuator/info/**").permitAll()
          .requestMatchers(HttpMethod.POST, "/login").permitAll()
          .anyRequest().authenticated()
      }
      .addFilter(JWTAuthenticationFilter(authenticationManager, securityProperties, tokenProvider))
      .addFilter(JWTAuthorizationFilter(authenticationManager, securityProperties, tokenProvider))
      .build()
  }
}
