package com.affan.capstone_project.ui.component.dialog

import androidx.compose.foundation.background
import com.affan.capstone_project.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.affan.capstone_project.ui.component.buttons.ButtonIcon
import com.affan.capstone_project.ui.theme.CapstoneProjectTheme


@Composable
fun HomeDialog(
    btnPlanning:()->Unit,
    btnScan:()->Unit,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .heightIn(max = 300.dp)
            .fillMaxSize()
            .background(Color.White, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = modifier
                .height(3.dp)
                .width(70.dp)
                .background(Color.LightGray)
        )
        Spacer(
            modifier = modifier.height(20.dp)
        )
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            ButtonIcon(onClick =  btnScan , title =  "Already Planted", description = "button" , icon = painterResource(
                id = R.drawable.scanner
            ), corner = 15,modifier = Modifier.weight(1f),style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            ButtonIcon(onClick = btnPlanning , title =  "Planning To Plant", description = "button" , icon = painterResource(
                id = R.drawable.leaf
            ), corner = 15,modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        }


    }
}

@Preview
@Composable
fun DetailPrev() {
    CapstoneProjectTheme() {
        HomeDialog(btnPlanning = {}, btnScan = {})
    }
}