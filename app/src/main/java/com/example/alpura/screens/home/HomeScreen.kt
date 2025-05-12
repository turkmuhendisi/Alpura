package com.example.alpura.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alpura.R
import com.example.alpura.ui.theme.Blue
import com.example.alpura.ui.theme.BlueDark
import com.example.alpura.ui.theme.BlueLight
import com.example.alpura.ui.theme.BlueGray
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Dashboard",
                        style = MaterialTheme.typography.titleLarge,
                        color = BlueDark
                    )
                },
                actions = {
                    IconButton(onClick = { /* Notification */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = BlueDark
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            // Kullanıcı profili ve genel ilerleme
            ProfileProgressSection()

            // Aktivite önerileri
            SectionTitle("Günlük Aktiviteler")
            ActivitySuggestions()

            // Eğitim İstatistikleri
            SectionTitle("Eğitim İstatistikleri")
            StatisticsSection()

            // Devam Eden Eğitimler
            SectionTitle("Devam Eden Eğitimler")
            OngoingCourses(navController)

            // Son makale aktivitesi
            SectionTitle("Son Okunan Makaleler")
            RecentArticles(navController)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileProgressSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = BlueDark
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profil Resmi
                Box(
                    modifier = Modifier
                        .size(64.dp) // Border dahil tüm boyut
                        .background(Color.White, shape = CircleShape)
                        .padding(4.dp) // Border kalınlığı
                        .clip(CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profile_picture),
                        contentDescription = "Profile",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Samet Berkant KOCA",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        "Premium Üye",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }

                // Level Badge
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Blue
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        "Lv. 6",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Overall Progress
            Text(
                "Genel İlerleme",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = 0.68f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = BlueLight,
                trackColor = Color.White.copy(alpha = 0.2f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProgressStat("Toplam Puan", "3,456")
                ProgressStat("Haftalık Hedef", "68%")
                ProgressStat("Pozisyon", "6.")
            }
        }
    }
}

@Composable
fun ProgressStat(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        ),
        color = BlueDark,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
fun ActivitySuggestions() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ActivityCard(
            "Günlük Test",
            "1/3 tamamlandı",
            Icons.Rounded.CheckCircle,
            Blue,
            modifier = Modifier.weight(1f)
        )
        ActivityCard(
            "Makale Oku",
            "10dk okuma",
            Icons.Rounded.Timeline,
            BlueLight,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActivityCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp)
            )

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = BlueDark
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = BlueGray
                )
            }
        }
    }
}

@Composable
fun StatisticsSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatisticCard("Toplam Eğitim", "12", BlueLight.copy(alpha = 0.2f), BlueDark)
        StatisticCard("Tamamlanan", "8", Blue.copy(alpha = 0.2f), BlueDark)
        StatisticCard("Makaleler", "24", BlueGray.copy(alpha = 0.2f), BlueDark)
    }
}

@Composable
fun StatisticCard(title: String, count: String, color: Color, textColor: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = title,
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp,
                color = textColor
            )
        }
    }
}

@Composable
fun OngoingCourses(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        CourseProgressCard(
            title = "Microservices Orchestration with Docker & Kubernetes",
            progress = 0.34f,
            lastActivity = "2 saat önce"
        )

        CourseProgressCard(
            title = "How to Deploy Project to Live Server",
            progress = 0.51f,
            lastActivity = "Dün"
        )
    }
}

@Composable
fun CourseProgressCard(title: String, progress: Float, lastActivity: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = BlueDark
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Blue,
                    trackColor = BlueGray.copy(alpha = 0.2f)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Son aktivite: $lastActivity",
                    fontSize = 12.sp,
                    color = BlueGray
                )

                IconButton(
                    onClick = { /* TODO: Navigate to course */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Devam Et",
                        tint = Blue,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RecentArticles(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        RecentArticleCard(
            title = "Cloud Deployment Best Practices",
            readTime = "5 dk okuma",
            category = "DevOps"
        )

        RecentArticleCard(
            title = "Modern UI Design Principles for Mobile",
            readTime = "8 dk okuma",
            category = "UI/UX"
        )
    }
}

@Composable
fun RecentArticleCard(title: String, readTime: String, category: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category indicator
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = BlueLight
                ),
                modifier = Modifier.size(40.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category.first().toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BlueDark
                )

                Text(
                    text = readTime,
                    fontSize = 12.sp,
                    color = BlueGray
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Go to Article",
                tint = Blue,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}