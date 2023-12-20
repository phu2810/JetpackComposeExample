package io.sjf.jetpackcomposeexample.ui.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import io.sjf.jetpackcomposeexample.R

data class FeedItem(
    val avatarUrl: String,
    val name: String,
    val date: String,
    val title: String,
    val content: String,
    val likes: Int, // Number of likes
    val shares: Int // Number of shares
)

@Composable
fun FeedItemCard(feedItem: FeedItem) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier.padding(18.dp).fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(model = feedItem.avatarUrl),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(text = feedItem.name)
                    Text(text = feedItem.date.toString())
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${feedItem.title}: ${feedItem.content}")
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { /* TODO: Handle like */ }) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_like),
                        contentDescription = "Like"
                    )
                    Text(text = "Like (${feedItem.likes})")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /* TODO: Handle share */ }) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_share),
                        contentDescription = "Share"
                    )
                    Text(text = "Share (${feedItem.shares})")
                }
            }
        }
    }
}

@Composable
fun LayoutExample(feedItems: List<FeedItem> = listOf(
    FeedItem("https://www.w3schools.com/w3images/avatar2.png", "John Doe", "2023-12-20", "Title 1", "Content 1", 10, 5),
    FeedItem("https://www.w3schools.com/w3images/avatar1.png", "Jane Smith", "2023-12-21", "Title 2", "Content 2", 20, 3),
)) {
    LazyColumn {
        items(feedItems) {

            FeedItemCard(it)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewFeedsList() {
    Column {
        LayoutExample()
    }
}
