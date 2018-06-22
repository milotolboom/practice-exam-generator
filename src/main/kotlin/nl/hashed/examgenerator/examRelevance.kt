package nl.hashed.examgenerator

import nl.hashed.examgenerator.models.Results
import kotlin.math.pow

internal fun getExamRelevance(results: ArrayList<Results>): ArrayList<Pair<Int, Double>> {
    return results.map { Pair(it.examId, calculateRelevanceOfPracticeExam(results.size, results.indexOf(it) + 1)) }
            .toCollection(arrayListOf())
}

internal tailrec fun recurPow(n: Int, iterator: Int = 0, total: Double = 0.0): Double {
    if (iterator >= n) return total
    return recurPow(n, iterator + 1, total + 2.0.pow(iterator))
}

internal tailrec fun recurMultiplication(n: Int, iterator: Int = 1, total: Int = 1): Int {
    if (iterator >= n) return total
    return recurMultiplication(n, iterator + 1, total * 2)
}

internal fun calculateRelevanceOfPracticeExam(amountOfPracticeExams: Int, importanceRanking: Int): Double {
    return 100 / (recurPow(amountOfPracticeExams)) * recurMultiplication(importanceRanking)
}
