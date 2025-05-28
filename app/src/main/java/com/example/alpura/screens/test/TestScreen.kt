package com.example.alpura.screens.test

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import com.example.alpura.ui.theme.Blue
import com.example.alpura.ui.theme.BlueDark
import com.example.alpura.ui.theme.BlueGray
import com.example.alpura.ui.theme.NavyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    testQuestions: List<TestQuestion>,
    onTestFinished: (Int) -> Unit,
    navController: NavController,
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }

    // Her soru için seçilen şık (index) tutulur
    var selectedChoiceIndex by remember {
        mutableStateOf(
            MutableList<Int?>(testQuestions.size) { null }
        )
    }

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler(enabled = true) {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    navController.popBackStack()
                }) {
                    Text("Çık", color = NavyBlue)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Vazgeç", color = NavyBlue)
                }
            },
            title = { Text("Testten çıkılsın mı?", style = MaterialTheme.typography.titleLarge) },
            text = { Text("Testi bitirmeden çıkarsan ilerlemen kaydedilmez.") },
            containerColor = Color.White,
            titleContentColor = NavyBlue,
            textContentColor = NavyBlue
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Test", style = MaterialTheme.typography.titleLarge, color = NavyBlue)
                },
                actions = {
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Kapat", tint = NavyBlue)
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            val question = testQuestions[currentQuestionIndex]
            val progress = (currentQuestionIndex + 1).toFloat() / testQuestions.size
            val selectedIndex = selectedChoiceIndex[currentQuestionIndex]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    color = Blue,
                    trackColor = BlueGray
                )

                Text(
                    "Soru ${currentQuestionIndex + 1}/${testQuestions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    color = BlueDark
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(6.dp),
                    colors = CardDefaults.cardColors(containerColor = BlueDark),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            question.question,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                question.choices.forEachIndexed { index, choice ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedIndex == index) BlueDark else Blue
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable {
                                selectedChoiceIndex = selectedChoiceIndex
                                    .toMutableList()
                                    .also {
                                        it[currentQuestionIndex] = index
                                    }
                            }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (selectedIndex == index) {
                                Icon(
                                    Icons.Default.ArrowRight,
                                    contentDescription = "Seçildi",
                                    tint = Color.White,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                            }
                            Text(choice, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { if (currentQuestionIndex > 0) currentQuestionIndex-- },
                        enabled = currentQuestionIndex > 0,
                        colors = ButtonDefaults.buttonColors(containerColor = Blue),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Geri")
                    }

                    if (currentQuestionIndex == testQuestions.lastIndex) {
                        Button(
                            onClick = {
                                val correctCount = selectedChoiceIndex.mapIndexed { i, selected ->
                                    selected == testQuestions[i].correctAnswerIndex
                                }.count { it }
                                onTestFinished(correctCount)
                            },
                            enabled = selectedIndex != null,
                            colors = ButtonDefaults.buttonColors(containerColor = Blue),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Testi Bitir")
                        }
                    } else {
                        Button(
                            onClick = {
                                if (selectedIndex != null) {
                                    currentQuestionIndex++
                                }
                            },
                            enabled = selectedIndex != null,
                            colors = ButtonDefaults.buttonColors(containerColor = Blue),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("İleri")
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.ArrowForward, contentDescription = "İleri")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TestResultScreen(correctAnswers: Int, totalQuestions: Int, navController: NavController) {
    val score = (correctAnswers.toFloat() / totalQuestions.toFloat()) * 100
    val scoreText = "%.1f".format(score) + "%"

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Test Tamamlandı",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Blue,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                Blue,
                            )
                            .padding(vertical = 12.dp, horizontal = 24.dp)
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = scoreText,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Doğru: $correctAnswers / $totalQuestions",
                        style = MaterialTheme.typography.titleMedium,
                        color = Blue,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(64.dp))
                    val performanceText = when {
                        score >= 90 -> "Mükemmel! 🎉"
                        score >= 70 -> "Çok iyi! 👍"
                        score >= 50 -> "İyi 👌"
                        else -> "Daha fazla çalışman gerek 💪"
                    }
                    Text(
                        text = performanceText,
                        style = MaterialTheme.typography.titleLarge,
                        color = Blue,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            navController.navigate("article_list") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(Blue)
                    ) {
                        Text(
                            "Yazılar sayfasına dön",
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

