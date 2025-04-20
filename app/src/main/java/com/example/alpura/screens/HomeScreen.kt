import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.alpura.R

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        // Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Samet Berkant KOCA", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        LinearProgressIndicator(
                            progress = 0.6f,
                            modifier = Modifier
                                .width(100.dp)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Lv. 6",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Gray
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { /* Notification */ }, modifier = Modifier.size(48.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications),
                        contentDescription = "Notifications",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Stats
        Text("İstatistik", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        )
        {
            item { StatCard("Alınan Eğitim", "2", Color.LightGray) }
            item { StatCard("Devam Eden Eğitimler", "2", Color.LightGray) }
            item { StatCard("Tamamlanan Eğitimler", "0", Color.LightGray) }
            item { StatCard("Okunan Makale", "12", Color.LightGray) }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Text("Son Ziyaret Edilenler", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.height(16.dp))

        // Courses List
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                CourseCard(
                    title = "How to Deploy Project to Live Server",
                    progress = 0.51f,
                    author = "Serhat Erdem",
                    color = Color.LightGray
                )
            }
            item {
                CourseCard(
                    title = "Microservices Orchestration with Docker & Kubernetes",
                    progress = 0.34f,
                    author = "Samet Berkant Koca",
                    color = Color.LightGray
                )
            }
            item {
                CourseCard(
                    title = "How to Launch Project on Linux Server ",
                    progress = 0.23f,
                    author = "Ahmet Suat Can",
                    color = Color.LightGray
                )
            }
            item {
                CourseCard(
                    title = "Enhance UI/UX Skills",
                    progress = 0.34f,
                    author = "Yavuz Sultan Samet Kan",
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, count: String, color: Color) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            Text(count, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun CourseCard(title: String, progress: Float, author: String, color: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text("By $author", fontSize = 14.sp)
        }
    }
}