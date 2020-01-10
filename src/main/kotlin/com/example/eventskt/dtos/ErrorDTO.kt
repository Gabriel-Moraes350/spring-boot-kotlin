package com.example.eventskt.dtos

import java.time.LocalDateTime

data class ErrorDTO(val error: String, var errors: List<String> = arrayListOf(""), val timestamp: LocalDateTime = LocalDateTime.now()) {
}