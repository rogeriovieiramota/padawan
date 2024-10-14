package br.com.padawan.kotlin.iniciante.repository

import br.com.padawan.kotlin.iniciante.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
