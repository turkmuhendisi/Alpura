package com.example.alpura.screens.article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArticleListScreen(
    articles: List<Article>,
    categories: List<String>,
    onArticleClick: (String) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("Tümü") }

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        CategoryBar(categories, selectedCategory) {
            selectedCategory = it
        }

        LazyColumn(
            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
        ) {
            val filteredArticles = if (selectedCategory == "Tümü") {
                articles
            } else {
                articles.filter { article -> article.category.any { it.equals(selectedCategory) } }
            }

            items(filteredArticles, key = { it.id }) { article ->
                ArticleCard(
                    imageUrl = article.imageUrl,
                    title = article.title,
                    author = article.author,
                    createdAt = article.created_at,
                    likeStatus = article.likedUsers,
                    onClick = { onArticleClick(article.id) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


