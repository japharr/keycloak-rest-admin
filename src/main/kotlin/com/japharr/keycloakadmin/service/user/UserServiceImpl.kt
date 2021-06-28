package com.japharr.keycloakadmin.service.user


import com.japharr.keycloakadmin.model.dto.ChangePasswordDto
import com.japharr.keycloakadmin.model.dto.RegisterDto
import com.japharr.keycloakadmin.model.dto.UserDto
import com.japharr.keycloakadmin.service.oauth.OauthService
import com.japharr.keycloakadmin.utils.SecurityUtils
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserServiceImpl (private val ouathService: OauthService) : UserService {
  // to return Mono of Tuple<HttpStatus, String>
  override fun register(userDto: RegisterDto, roles: Set<String>): Mono<String> {
    return ouathService.createUser(userDto, roles)
  }

  override fun findAll() = ouathService.getUsers()

  override fun findById(id: String) = ouathService.getUser(id)

  override fun findByLoggedIn(): Mono<UserDto> {
    return SecurityUtils.currentUserSubject().flatMap { id ->
      ouathService.getUser(id)
    }
  }

  override fun resetPassword(dto: ChangePasswordDto): Mono<String> {
    return SecurityUtils.currentUserSubject().flatMap {
      ouathService.resetPassword(it, dto)
    }
  }
}