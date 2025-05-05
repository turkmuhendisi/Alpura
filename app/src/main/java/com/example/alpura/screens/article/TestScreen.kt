package com.example.alpura.screens.article

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TestScreen(
    testQuestions: List<TestQuestion>,
    onTestFinished: (Int) -> Unit,
    navController: NavController,
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedChoiceIndex by remember { mutableStateOf<Int?>(null) }
    var correctAnswers by remember { mutableStateOf(0) }

    var answers by remember { mutableStateOf(List(testQuestions.size) { null as Boolean? }) }

    val question = testQuestions[currentQuestionIndex]

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Soru ${currentQuestionIndex + 1}/${testQuestions.size}",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = question.question, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(24.dp))

        question.choices.forEachIndexed { index, choice ->
            Button(
                onClick = {
                    selectedChoiceIndex = index
                    answers = answers.toMutableList().also { list ->
                        list[currentQuestionIndex] = (index == question.correctAnswerIndex)
                    }},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedChoiceIndex == index) Color.Gray else Color.LightGray
                )
            ) {
                Text(text = choice)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
                enabled = currentQuestionIndex > 0
            ) {
                Text("Geri")
            }

            if (currentQuestionIndex == testQuestions.lastIndex) {
                Button(
                    onClick = {
                        if (selectedChoiceIndex != null) {
                            if (selectedChoiceIndex == question.correctAnswerIndex) {
                                correctAnswers++
                            }
                            onTestFinished(correctAnswers)
                        }
                    },
                    enabled = selectedChoiceIndex != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Testi Bitir")
                }
            } else {
                Button(
                    onClick = {
                        if (selectedChoiceIndex != null) {
                            currentQuestionIndex++
                            selectedChoiceIndex = null
                        }
                    },
                    enabled = selectedChoiceIndex != null,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("İleri")
                }
            }
        }
    }
}

@Composable
fun TestResultScreen(correctAnswers: Int, totalQuestions: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Test Sonuçları",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Doğru cevap sayısı: $correctAnswers / $totalQuestions",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

