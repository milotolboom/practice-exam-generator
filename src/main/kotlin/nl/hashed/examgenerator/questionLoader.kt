package nl.hashed.examgenerator

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import nl.hashed.examgenerator.models.Question
import poc.models.QuestionResultStats
import java.io.FileReader

object QuestionLoader {
    private var results: Array<QuestionResultStats>? = null
    private var questions: Array<Question>? = null

    fun getResultsOfOthers(): Array<QuestionResultStats> {
        return if (results == null) {
            val reader = JsonReader(FileReader("src/main/kotlin/nl/hashed/examgenerator/resources/questionsMadeByOtherStudents.json"))
            results = Gson().fromJson(reader, Array<QuestionResultStats>::class.java)
            results ?: throw Exception("Couldn't load resource")
        } else {
            results ?: throw Exception("Couldn't load resource")
        }
    }

    fun getQuestionsNotAnswered(): Array<Question> {
        return if (questions == null) {
            val reader = JsonReader(FileReader("src/main/kotlin/nl/hashed/examgenerator/resources/questionBankNotAnswered.json"))
            questions = Gson().fromJson(reader, Array<Question>::class.java)
            questions ?: throw Exception("Couldn't load resource")
        } else {
            questions ?: throw Exception("Couldn't load resource")
        }
    }
}