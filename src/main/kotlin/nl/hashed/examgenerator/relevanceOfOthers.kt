package nl.hashed.examgenerator

import nl.hashed.examgenerator.models.Question

/**
 * Here the results of other students on this subject should be analysed.
 * After this the worst performed question should be returned.
 *
 * @param category the category of which the question should be about
 * @return [Question] the first made question of the student
 */
internal fun getMostRelevantNotAssessedQuestionOfCategory(category: String, questions: ArrayList<Question>): Question? {
    val assessedQuestionsOfOthers = QuestionLoader.getResultsOfOthers()

    // Filter questions to only contain current category
    val questionsOfCategory = questions.filter { it.categories.contains(category) }
    val questionIdsOfCategory = questionsOfCategory.map { it.questionId }
    val filteredQuestions = assessedQuestionsOfOthers.filter { questionIdsOfCategory.contains(it.questionId) }

    var mostRelevantQuestion: Pair<Int, Double>? = null

    if (filteredQuestions.isEmpty() && questionsOfCategory.isNotEmpty()) {
        // add question that hasn't been answered by anyone
        mostRelevantQuestion = Pair(questionsOfCategory[0].questionId, 100.0)
    } else {
        filteredQuestions.forEach {
            // The lower the rating, the more relevant the question is because of the low score on the question
            val rating = it.nGood.toDouble() / it.nResults

            mostRelevantQuestion?.let {
                if (rating < it.second)
                    mostRelevantQuestion = Pair(it.first, rating)
            } ?: run {
                mostRelevantQuestion = Pair(it.questionId, rating)
            }
        }
    }

    return questions.find { it.questionId == mostRelevantQuestion?.first }
}

/**
 * Here a request should be made to get the question of the category that is the furthest into the past.
 * Probably best to let the db do this
 *
 * @param questionsInCategory the questions in the category
 * @return [Question] the first made question of the student
 */
internal fun getFirstAskedQuestion(questionsInCategory: List<Question>, studentNr: Int): Question {
    return questionsInCategory.sortedBy { it.answeredOn }.first()
}