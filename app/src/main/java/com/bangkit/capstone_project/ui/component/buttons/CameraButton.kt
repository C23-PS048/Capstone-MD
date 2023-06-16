package com.bangkit.capstone_project.ui.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ui.theme.GreenMed
import com.bangkit.capstone_project.ui.theme.Ivory

@Composable
fun CameraButton(onclick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(75.dp)
            .clip(CircleShape)
            .background(Ivory)
            .padding(8.dp)
    ) {

        OutlinedButton(
            onClick = onclick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = GreenMed
            ), border = BorderStroke(2.dp, GreenMed),
            shape = CircleShape,
            modifier = Modifier.size(65.dp),
            contentPadding = PaddingValues(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.leaf),
                "",

            )
        }
    }
}

@Preview
@Composable
fun ButtonPrev() {
    CameraButton(onclick = {})
}