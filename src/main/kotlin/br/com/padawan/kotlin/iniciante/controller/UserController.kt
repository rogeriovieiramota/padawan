package br.com.padawan.kotlin.iniciante.controller

import br.com.padawan.kotlin.iniciante.dto.UserDTO
import br.com.padawan.kotlin.iniciante.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAllUsers(): List<UserDTO> {
        return userService.getAllUsers()
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        return try {
            ResponseEntity.ok(userService.getUserById(id))
        } catch (e: Exception) {
            ResponseEntity(NOT_FOUND)
        }
    }

    @PostMapping
    fun createUser(@RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        val newUser = userService.createUser(userDTO)
        return ResponseEntity(newUser, CREATED)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        return try {
            ResponseEntity.ok(userService.updateUser(id, userDTO))
        } catch (e: Exception) {
            ResponseEntity(NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            userService.deleteUser(id)
            ResponseEntity(NO_CONTENT)
        } catch (e: Exception) {
            ResponseEntity(NOT_FOUND)
        }
    }
}