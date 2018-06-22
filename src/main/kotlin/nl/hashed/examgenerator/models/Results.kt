package nl.hashed.examgenerator.models

data class Results(val examId: Int, val studentNr: Int, val questions: Array<Question>)