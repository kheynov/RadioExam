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
import ru.kheynov.radioexam.data.Ticket
import ru.kheynov.radioexam.data.Tickets
import ru.kheynov.radioexam.ui.theme.RadioExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tickets.init(context = applicationContext)
            RadioExamTheme {
                ExamTicket(
                    ticket = Ticket(
                        23,
                        11,
                        3,
                        "Чем в основном определяется коэффициент усиления схемы с применением операционного усилителя?",
                        "Типом операционного усилителя.",
                        "Глубиной частотной коррекции.",
                        "Глубиной отрицательной обратной связи, задаваемой внешними элементами.",
                        "Напряжением питания операционного усилителя.",
                    )
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