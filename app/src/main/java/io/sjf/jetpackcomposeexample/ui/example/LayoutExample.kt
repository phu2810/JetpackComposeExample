package io.sjf.jetpackcomposeexample.ui.example

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.sjf.jetpackcomposeexample.R
import io.sjf.jetpackcomposeexample.ui.theme.Grey50

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutExample() {
    // set background color to white
    Scaffold(
        containerColor = Color.White
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Discover",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        letterSpacing = 0.sp,
                        color = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "News from all over the world",
                    style = TextStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.sp,
                        letterSpacing = 0.15.sp,
                        color = Color.Gray
                    )
                )
            }
            SearchBar()
            CategoryChips()
            NewsList()
        }
    }
}

@Composable
fun SearchBar() {
    val textFieldValue = remember { mutableStateOf(TextFieldValue()) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = MaterialTheme.shapes.small,
        color = Grey50
    ) {
        BasicTextField(
            value = textFieldValue.value,
            onValueChange = { textFieldValue.value = it },
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black),
            decorationBox = { innerTextField ->
                Row(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search), // Replace with your search icon
                        contentDescription = "Search",
                        modifier = Modifier.size(20.dp)
                    )

                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        if (textFieldValue.value.text.isEmpty()) {
                            Text("Search", color = Color.Gray)
                        }
                        innerTextField()
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.ic_config), // Replace with your mic icon
                        contentDescription = "Mic",
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}


@Composable
fun CategoryChips(selectedCategory: String = "Politics") {
    var selectedCategory by remember { mutableStateOf(selectedCategory) }
    val categories = listOf("Health", "Politics", "Art", "Food", "Science")
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        items(categories) { category ->
            CategoryChip(category, category == selectedCategory, onSelected = { selectedCategory = it })
        }
    }
}

@Composable
fun CategoryChip(category: String, isSelected: Boolean, onSelected: (String) -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(end = 16.dp)
            .selectable(
                selected = isSelected,
                onClick = { onSelected(category) }
            )
    ) {
        Text(
            text = category,
            color = if (isSelected) Color.Black else Color.Gray,
            fontSize = 20.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            textDecoration = TextDecoration.None
        )
        Spacer(modifier = Modifier.height(5.dp))
        if (isSelected) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(color = Color.Black)
            )
        }
    }
}

@Composable
fun NewsList() {
    val newsItems = listOf(
        NewsItem("Candidate Biden Called Saudi Arabia a ‘Pariah.’", "4 hours ago", 376),
        NewsItem("A New Coronavirus Variant Is Spreading in New York", "6 hours ago", 1006),
    )

    LazyColumn {
        items(newsItems) { newsItem ->
            NewsCard(newsItem)
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsItem) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.thumbnail),
            contentDescription = "Article Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(newsItem.title, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_clock),
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(15.dp),
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(newsItem.subtitle, fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(R.drawable.ic_eye),
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(20.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("${newsItem.views} views", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

data class NewsItem(val title: String, val subtitle: String, val views: Int)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutExample()
}
