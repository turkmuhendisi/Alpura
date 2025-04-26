package com.example.alpura.screens.article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ArticleListScreen(
    articles: List<Article>,
    categories: List<String>,
    onArticleClick: (String) -> Unit,
) {
    var selectedCategory by remember { mutableStateOf("Tümü") }

    Column(modifier = Modifier.background(MaterialTheme.colorScheme.onPrimary)) {
        CategoryBar(categories, selectedCategory) {
            selectedCategory = it
        }

        LazyColumn {

            val filteredArticles = if (selectedCategory == "Tümü") {
                articles
            } else {
                articles.filter { article -> article.category.any { it.name == selectedCategory } }
            }

            items(filteredArticles, key = { it.id }) { article ->
                ArticleCard(
                    imageUrl = article.imageUrl,
                    author = article.author,
                    createdAt = article.created_at,
                    likeStatus = article.likeStatus,
                    commentStatus = article.comments.size,
                    onClick = { onArticleClick(article.id) }
                )
            }
        }
    }
}


