package ru.amalkoott.alarmapp

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.amalkoott.alarmapp.ui.navigation.AppNavigation
import ru.amalkoott.alarmapp.ui.theme.AlarmAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmAppTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val list = listOf("text 1","text 2","text 3","text 4")
    val expandedItems = remember { mutableStateListOf<Boolean>().apply { repeat(list.size) { add(false) } } }

    LazyColumn(){
        itemsIndexed(list){ index, item ->
            Text(
                text = item,
                modifier = modifier.clickable {
                    expandedItems[index] = !expandedItems[index]
                }
            )
            if (expandedItems[index]){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(100.dp)
                    .background(Color.White)
                    .drawBehind {
                        drawInnerShadow(size, Color.Gray.copy(alpha = 0.2f))
                    }
                ){

                    Text(
                        text = "Details for $item",
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
        }
    }

}
fun DrawScope.drawInnerShadow(size: androidx.compose.ui.geometry.Size, color: Color) {
    drawRect(
        color = color,
        size = size.copy(width = size.width, height = size.height),
        topLeft = androidx.compose.ui.geometry.Offset(0f, 0f)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AlarmAppTheme {
        Greeting("Android")
    }
}