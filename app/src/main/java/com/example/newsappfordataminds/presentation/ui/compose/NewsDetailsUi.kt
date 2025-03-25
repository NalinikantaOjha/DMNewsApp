package com.example.newsappfordataminds.presentation.ui.compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.newsappfordataminds.R
import com.example.newsappfordataminds.presentation.ui.compose.theme.Black
import com.example.newsappfordataminds.presentation.ui.compose.theme.Gray
import com.example.newsappfordataminds.presentation.ui.compose.theme.White

@Composable
internal fun NewsDetailScreen(
    title: String?,
    description: String?,
    imageUrl: String?,
    sourceName: String?,
    publishedDate: String?,
    author: String?
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Article Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title ?: "No Title",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "By ${author ?: "Unknown"} | ${sourceName ?: "Unknown Source"}",
                fontSize = 14.sp,
                color = Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Published: ${publishedDate ?: "Unknown Date"}",
                fontSize = 14.sp,
                color = Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description ?: stringResource(R.string.no_description),
                fontSize = 16.sp,
                color = Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            imageUrl?.let { url ->
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(stringResource(id = R.string.details))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun NewsDetailScreenPreview() {
    NewsDetailScreen(
        title = "Breaking News: Compose Rocks!",
        description = "Jetpack Compose is now the recommended way to build UI in Android.",
        imageUrl = "https://via.placeholder.com/300",
        sourceName = "Android News",
        publishedDate = "2025-03-24",
        author = "John Doe"
    )
}