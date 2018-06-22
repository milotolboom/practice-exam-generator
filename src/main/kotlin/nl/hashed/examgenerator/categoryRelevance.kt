package nl.hashed.examgenerator

import nl.hashed.examgenerator.models.Results

internal fun categoriesWithRelevancePercentages(studentNr: Int, results: ArrayList<Results>, categories: List<String>): List<Pair<String, Double>> {
    val examRelevance: ArrayList<Pair<Int, Double>> = getExamRelevance(results)

    val mapOfCategoriesAndTheirRelevancePercentages = mutableMapOf<String, Double>()
    for (result in results) {
        if (result.studentNr != studentNr) continue
        categoryLoop@ for (category in categories) {
            val questionsOfCategoryInExam = result.questions.filter { it.categories.contains(category) }
            if (questionsOfCategoryInExam.isEmpty())
                continue@categoryLoop

            val percentageQuestionsAnsweredCorrectly = questionsOfCategoryInExam
                    .map { q -> if (q.wasCorrect) 0.0 else 100.0 }
                    .reduce { acc, i -> acc + i } / questionsOfCategoryInExam.size
            val currentExamRelevance = examRelevance.find { it.first == result.examId } ?: continue@categoryLoop
            val reducedPercentage = (percentageQuestionsAnsweredCorrectly * currentExamRelevance.second) / 100

            if (mapOfCategoriesAndTheirRelevancePercentages.containsKey(category))
                mapOfCategoriesAndTheirRelevancePercentages[category] = (mapOfCategoriesAndTheirRelevancePercentages[category]
                        ?: continue@categoryLoop) + reducedPercentage
            else
                mapOfCategoriesAndTheirRelevancePercentages[category] = reducedPercentage
        }
    }

    mapOfCategoriesAndTheirRelevancePercentages.forEach { if (it.value == 0.0) mapOfCategoriesAndTheirRelevancePercentages[it.key] = 10.0 }

    val sorted = mapOfCategoriesAndTheirRelevancePercentages.toList().sortedBy { it.second }
    val multiplier = 100 / sorted.last().second

    return sorted.map { Pair(it.first, it.second * multiplier) }
}