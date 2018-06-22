package nl.hashed.examgenerator.models

import java.util.*

data class Question(
        val questionId: Int,
        val questionText: String,
        val categories: Array<String>,
        val type: String,
        val answeredOn: Date? = null,
        val wasCorrect: Boolean)
