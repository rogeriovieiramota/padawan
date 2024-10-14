package br.com.padawan.kotlin.iniciante.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.*


@Entity
@Table(name = "usuario")
data class User(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String
)

