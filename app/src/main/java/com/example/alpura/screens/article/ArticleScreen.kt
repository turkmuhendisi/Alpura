package com.example.alpura.screens.article

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.alpura.ui.theme.Blue
import com.example.alpura.ui.theme.BlueDark
import com.example.alpura.ui.theme.BlueLight
import com.example.alpura.ui.theme.BlueGray
import io.noties.markwon.Markwon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    article: Article,
    onBackClick: () -> Unit,
    onTestClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    /*Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = Blue,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )*/
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = "Geri",
                            tint = BlueDark,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = BlueDark,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = BlueLight),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    AsyncImage(
                        model = article.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = article.author, 
                            style = MaterialTheme.typography.titleMedium,
                            color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = article.created_at, 
                            style = MaterialTheme.typography.bodySmall,
                            color = BlueGray
                        )
                    }
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.ThumbUp,
                            contentDescription = "Beğeni",
                            modifier = Modifier.size(20.dp),
                            tint = Blue
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        /*Text(
                            "${article.likeStatus}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BlueDark
                        )*/

                        Spacer(modifier = Modifier.width(20.dp))

                        Icon(
                            Icons.Outlined.ChatBubble,
                            contentDescription = "Yorum",
                            modifier = Modifier.size(20.dp),
                            tint = BlueGray
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        /*Text(
                            "${article.comments.size}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BlueDark
                        )*/
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = BlueGray, thickness = 1.dp)
                Spacer(modifier = Modifier.height(16.dp))

                MarkdownView(article.content)

                Spacer(modifier = Modifier.height(24.dp))

                /*if (article.tests.isNotEmpty()) {
                    Button(
                        onClick = { onTestClick() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Blue,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Kendini Dene", 
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }*/
            }
        }
    }
}

@Composable
fun MarkdownView(content: String) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            val markwon = Markwon.create(context)
            markwon.setMarkdown(this, content)
            textSize = 16f
            setLineSpacing(1.4f, 1.4f)
            setPadding(0, 0, 0, 0)
            setTextIsSelectable(true)
            linksClickable = true
            movementMethod = LinkMovementMethod.getInstance()
            setTextColor(android.graphics.Color.parseColor("#1A237E")) // BlueDark
        }
    })
}