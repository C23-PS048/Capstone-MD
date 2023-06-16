package com.bangkit.capstone_project.ui.component.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var visible by remember { mutableStateOf(false) }
    val icon = if (visible) {
        painterResource(id = R.drawable.password_hide)
    } else {
        painterResource(id = R.drawable.password_peek)

    }
    Column() {
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = "Password") },
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = if (text.length < 8 && text.isNotBlank()) Color.Red else MaterialTheme.colorScheme.primary),
            placeholder = { Text(text = "Password") },
            trailingIcon = {
                IconButton(onClick = { visible = !visible }) {
                    Icon(painter = icon, contentDescription = "Password Peek Button",modifier.size(20.dp))
                }
            },
            singleLine = true,
            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation()
        )

        if (text.length < 8 && text.isNotBlank()) {
            Text(text = "Password Minimal Berjumlah 8 karakter", color = Color.Red)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordPreview() {
    CapstoneProjectTheme() {
        PasswordTextField("adw",{})
    }
}