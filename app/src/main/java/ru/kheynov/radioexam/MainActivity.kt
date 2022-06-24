package ru.kheynov.radioexam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.kheynov.radioexam.data.Tickets
import ru.kheynov.radioexam.data.getTicket
import ru.kheynov.radioexam.ui.theme.RadioExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tickets.init(context = applicationContext)
            val ticketsList = Tickets.tickets
            RadioExamTheme {
                ExamTicket(
                    ticket = ticketsList.getTicket(137)!!
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RadioExamTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Greeting("Android")
        }
    }
}