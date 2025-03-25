package com.example.newsappfordataminds.presentation.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.newsappfordataminds.R
import com.example.newsappfordataminds.presentation.model.RecentNewsUiModel
import com.example.newsappfordataminds.presentation.model.RecentNewsUiState
import com.example.newsappfordataminds.presentation.ui.compose.theme.Black
import com.example.newsappfordataminds.presentation.ui.compose.theme.Blue
import com.example.newsappfordataminds.presentation.ui.compose.theme.Pink
import com.example.newsappfordataminds.presentation.ui.compose.theme.Red
import com.example.newsappfordataminds.presentation.ui.compose.theme.Sky
import com.example.newsappfordataminds.presentation.ui.compose.theme.White


@Composable
internal fun RecentNewsUi(uiState: RecentNewsUiState, onNewsClick: (RecentNewsUiModel) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(26.dp))

        Header(title = stringResource(R.string.title))

        Spacer(modifier = Modifier.height(26.dp))

        when (uiState) {
            is RecentNewsUiState.Error -> ErrorUi()
            RecentNewsUiState.Loading -> CircularLoader()
            is RecentNewsUiState.Success -> NewsList(newsList = uiState.news, onNewsClick)
        }
    }
}

@Composable
internal fun Header(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(listOf(Red, Blue, Pink, Sky)),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 16.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge.copy(color = White),
        )
    }
}

@Composable
internal fun NewsList(newsList: List<RecentNewsUiModel>, onNewsClick: (RecentNewsUiModel) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(newsList) { news ->
            RecentNewsItem(news, onNewsClick)
        }
    }
}

@Composable
internal fun CircularLoader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
internal fun ErrorUi() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error),
            color = MaterialTheme.colorScheme.onErrorContainer,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun RecentNewsItem(
    recentNews: RecentNewsUiModel,
    onNewsClick: (RecentNewsUiModel) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable { onNewsClick(recentNews) }
            .height(350.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column {
            NewsImage(imageUrl = recentNews.imageUrl)
            Spacer(modifier = Modifier.height(10.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                recentNews.title?.let {
                    NewsText(
                        it,
                        MaterialTheme.typography.headlineMedium,
                        MaterialTheme.colorScheme.primary,
                        25,
                        1
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                NewsText(
                    recentNews.description.orEmpty(),
                    MaterialTheme.typography.bodyLarge,
                    MaterialTheme.colorScheme.onSurface,
                    16,
                    2
                )
                Spacer(modifier = Modifier.height(10.dp))

                NewsMetaInfo(recentNews)
            }
        }
    }
}

@Composable
internal fun NewsMetaInfo(recentNews: RecentNewsUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        recentNews.sourceName?.let {
            NewsText(
                it, MaterialTheme.typography.labelMedium, MaterialTheme.colorScheme.tertiary, 10
            )
        }

        recentNews.publishedDate?.let {
            NewsText(
                it, MaterialTheme.typography.labelMedium, MaterialTheme.colorScheme.tertiary, 10
            )
        }
    }
}

@Composable
internal fun NewsText(
    text: String,
    style: androidx.compose.ui.text.TextStyle,
    color: Color,
    fontSize: Int,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        text = text,
        style = style,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        fontSize = fontSize.sp,
    )
}

@Composable
internal fun NewsImage(imageUrl: String?) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(context).data(imageUrl).crossfade(true)
                    .placeholder(R.drawable.music_audio)
                    .transformations(RoundedCornersTransformation(16f)).build()
            ),
            contentDescription = "News Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RecentNewsUiPreview() {
    RecentNewsUi(RecentNewsUiState.Loading, onNewsClick = {})
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun HeaderPreview() {
    Header("Sample Header")
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RecentNewsItemPreview() {
    RecentNewsItem(getSampleNews(), onNewsClick = {})
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun CircularLoaderPreview() {
    CircularLoader()
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ErrorUiPreview() {
    ErrorUi()
}

private fun getSampleNews() = RecentNewsUiModel(
    title = "🚀 AI Breakthrough: New Model Achieves 99% Accuracy",
    description = "The AI industry is experiencing rapid advancements, with a groundbreaking model achieving new records.",
    imageUrl = "https://dummyimage.com/600x400/000/fff",
    sourceName = "TechInsider",
    publishedDate = "March 17, 2024",
    author = "John"
)
