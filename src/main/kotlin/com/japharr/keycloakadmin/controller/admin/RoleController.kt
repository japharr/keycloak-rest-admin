package com.japharr.keycloakadmin.controller.admin


import com.japharr.keycloakadmin.model.dto.RoleDto
import com.japharr.keycloakadmin.service.role.RoleService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/admin/roles")
@PreAuthorize("hasRole('ROLE_ADMIN')")
class RoleController (val roleService: RoleService) {
  @GetMapping
  fun getAll() = roleService.getRoles()

  @GetMapping("/{name}")
  fun getByName(@PathVariable("name") name: String) =
    roleService.getRole(name)

  @PostMapping
  fun create(@Valid @RequestBody role: RoleDto): Mono<RoleDto> {
    return roleService.createRole(role)
  }

  @PutMapping("/{name}")
  fun update(@PathVariable("name") name: String, @Valid @RequestBody role: RoleDto): Mono<RoleDto> {
    return roleService.updateRole(name, role)
  }

  @DeleteMapping("/{name}")
  fun delete(@PathVariable("name") name: String): Mono<Void> {
    return roleService.deleteRole(name)
  }
}