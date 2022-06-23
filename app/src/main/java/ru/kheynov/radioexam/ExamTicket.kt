package ru.kheynov.radioexam

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ru.kheynov.radioexam.data.Ticket
import ru.kheynov.radioexam.data.Tickets

@Composable
fun ExamTicket(
    ticket: Ticket,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = ticket.text)
            Tickets.images[ticket.image_id]?.let {
                Image(
                    painter = painterResource(
                        id = it
                    ),
                    contentDescription = ""
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExamTicketPreview() {
    ExamTicket(
        ticket = Ticket(
            23,
            2,
            "b",
            "How much?",
            "a) 1",
            "b) 2",
            "c) 3",
            "d) 4",
        )
    )
}