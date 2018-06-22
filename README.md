# practice-exam-generator
A practice exam generator algorithm.

This is part of a project I did for school. The code contains an algorithm that generates exams based on previous results. The algorithm uses the concept of questions that are linked to one or multiple categories. A simple rundown of the workings of the algorithm is as follows:

1. Check if previous results are available
2. If not, add questions to the exam of *all* the available categories
3. If there are, calculate the relevance of the previous exams. This works by spreading percentages exponentially over the previously made exams.
4. Calculate the percentage of questions that are made incorrectly within each category, in each exam.
5. Combine the relevance of the previous exams with the relevance of each category within these exams, so there's a distribution like this:

   Category 1: 20%

   Category 2: 40%

   Category 3: 100%

6. Start the process with the most relevant category, in this case category 3.
7. Throw a dice that determines if a question of this category will be added right now
8. If the dice hits, check if there are not-yet asked questions available
9. If there are, add the question that was made the worst by other students
10. If there isn't, add the question that was made furthest into the past
11. Once a question has been added, remove it from the list of all questions
12. Check if the exam complies to prerequisites
13. If it does, bundle in exam
14. If it doesn't, go to the next most relevant category, and thus go back to step 7.
