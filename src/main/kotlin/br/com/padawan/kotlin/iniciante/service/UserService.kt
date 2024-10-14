package br.com.padawan.kotlin.iniciante.service

import br.com.padawan.kotlin.iniciante.dto.UserDTO
import br.com.padawan.kotlin.iniciante.model.User
import br.com.padawan.kotlin.iniciante.repository.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<UserDTO> {
        return userRepository.findAll().map { user ->
            UserDTO(user.id, user.name, user.email)
        }
    }

    fun getUserById(id: Long): UserDTO {
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User not found")
        }
        return UserDTO(user.id, user.name, user.email)
    }

    fun createUser(userDTO: UserDTO): UserDTO {
        val user = User(name = userDTO.name, email = userDTO.email)
        val savedUser = userRepository.save(user)
        return UserDTO(savedUser.id, savedUser.name, savedUser.email)
    }

    fun updateUser(id: Long, userDTO: UserDTO): UserDTO {
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User not found")
        }
        val updatedUser = userRepository.save(user.copy(name = userDTO.name, email = userDTO.email))
        return UserDTO(updatedUser.id, updatedUser.name, updatedUser.email)
    }

    fun deleteUser(id: Long) {
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User not found")
        }
        userRepository.delete(user)
    }
}