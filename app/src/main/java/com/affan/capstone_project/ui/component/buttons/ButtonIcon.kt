package com.affan.capstone_project.ui.component.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.affan.capstone_project.R
import com.affan.capstone_project.ui.theme.GreenMed

@Composable
fun ButtonIcon(
    onClick: () -> Unit,
    title: String,
    description:String,
    icon: Painter,
    corner:Int

    ) {
    Button(
        onClick =onClick,
        shape = RoundedCornerShape(corner),
        colors = ButtonDefaults.buttonColors(
            GreenMed
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(painter = icon, contentDescription = description)
            Text(text = title)
        }
    }
}

@Preview
@Composable
fun ButtonIconPrev() {
    ButtonIcon(onClick = {},
    title = "Plant",
    description = "Button",
    icon = painterResource(id = R.drawable.cloud_sun),
    50)
}