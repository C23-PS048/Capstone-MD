package com.bangkit.capstone_project.ui.component.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Date() {

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()
    Text("Selected date timestamp: ${datePickerState.selectedDateMillis ?: "no selection"}")
Button(onClick = { openDialog.value = true }) {
    Text(text = "Input")
}
    if (openDialog.value) {
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
      Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)), contentAlignment = Alignment.Center) {
          DatePickerDialog(
              onDismissRequest = {

                  openDialog.value = false
              },
              confirmButton = {
                  TextButton(
                      onClick = {
                          openDialog.value = false
                          snackScope.launch {
                              snackState.showSnackbar(
                                  "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                              )
                          }
                      },
                      enabled = confirmEnabled.value
                  ) {
                      Text("OK")
                  }
              },
              dismissButton = {
                  TextButton(
                      onClick = {
                          openDialog.value = false
                      }
                  ) {
                      Text("Cancel")
                  }
              }
          ) {
              DatePicker(state = datePickerState)
          }
      }
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun InputDate() {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)) {
        DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp))
        Row(horizontalArrangement = Arrangement.End) {
            TextButton(onClick = { showDialog = false}) {
                Text(text = "Ok")
            }
        }
    }
}*/
