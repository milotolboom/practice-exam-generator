package nl.hashed.examgenerator

import nl.hashed.examgenerator.models.Results
import nl.hashed.examgenerator.models.Question
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
    simulateResults(1000, 123, QuestionLoader.getQuestionsNotAnswered().toCollection(arrayListOf()))
}

private fun simulateResults(amountOfResults: Int, studentNr: Int, questionsNotAnswered: ArrayList<Question>, iterator: Int = 0, questionsAnswered: ArrayList<Question> = ArrayList(), previousResults: ArrayList<Results> = ArrayList()) {
    if (iterator == amountOfResults) return
    var exam: ArrayList<Question> = arrayListOf()
    val memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    val timeElapsed = measureTimeMillis {
        println("Exam ${iterator + 1} results ------------------------")

        // questionsNotAnswered should be all the questions in the course, if there is no exam generated yet
        exam = generateExam(previousResults, 1, 123, questionsNotAnswered, questionsAnswered)
    }
    println("Memory before generating exam $iterator: $memBefore")
    println("Memory after generating exam $iterator: ${Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()}")

    // Simulate results
    val results = simulateCorrectAndFalseAnswers(exam, studentNr, iterator)
    for (question in results.questions) {
        println(question)
    }

    // Limit to 10 previous results
    if (previousResults.size > 10)
        previousResults.removeAt(0)

    previousResults.add(results)

    // Add questions to answered list
    val newQuestionsAnswered = questionsAnswered.plus(results.questions).distinct()
    // Remove just answered questions from list
    questionsNotAnswered.removeIf { r -> newQuestionsAnswered.any { it.questionId == r.questionId } }

    println("Time for generating exam $iterator: $timeElapsed ms")

    simulateResults(amountOfResults, studentNr, questionsNotAnswered, iterator + 1, newQuestionsAnswered.toCollection(arrayListOf()), previousResults)
}

private fun simulateCorrectAndFalseAnswers(questions: ArrayList<Question>, studentNr: Int, examId: Int): Results {
    val questionResults = questions.map { Question(questionId = it.questionId, categories = it.categories, wasCorrect = ThreadLocalRandom.current().nextBoolean(), questionText = it.questionText, type = it.type, answeredOn = Date()) }.toTypedArray()
    return Results(examId, studentNr, questionResults)
}