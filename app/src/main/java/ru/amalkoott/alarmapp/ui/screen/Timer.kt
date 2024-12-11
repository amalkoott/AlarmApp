package ru.amalkoott.alarmapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TimerScreen(){
    Text("Timer")

    // задержать для большей прибавки

    Column {
        Button(
            onClick = {

            }
        ){
            Text(
                text = "START"
            )
        }

        Button(
            onClick = {

            }
        ){
            Text(
                text = "STOP"
            )
        }

        Button(
            onClick = {

            }
        ){
            Text(
                text = "RESET"
            )
        }
    }
}