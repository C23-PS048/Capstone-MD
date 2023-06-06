package com.bangkit.capstone_project.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ui.theme.BlackMed

@Composable
fun ForumScreen() {
    UserProfilePage()
}

@Preview
@Composable
fun ProfilePreview() {
    ForumScreen()
}

@Composable
fun UserProfilePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ProfileHeaderSection()

        Divider(color = Color.LightGray, thickness = 0.5.dp)
        ProfileMenu()
    }
}

@Composable
fun ProfileMenu() {
    Column(  modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .background(Color.White)) {
        Text(text = "Aktifitas", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(contentColor = BlackMed, containerColor = Color.Transparent),contentPadding= PaddingValues(0.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(painter = painterResource(id = R.drawable.chat), contentDescription = null, colorFilter = ColorFilter.tint(
                    BlackMed), modifier = Modifier.size(16.dp))
                Text(text = "Forum", fontSize = 16.sp, fontWeight = FontWeight.W400)
            }
        }
        TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(contentColor = BlackMed, containerColor = Color.Transparent),contentPadding= PaddingValues(0.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(painter = painterResource(id = R.drawable.chat), contentDescription = null, colorFilter = ColorFilter.tint(
                    BlackMed), modifier = Modifier.size(16.dp))
                Text(text = "Forum", fontSize = 16.sp, fontWeight = FontWeight.W400)
            }
        }
        TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(contentColor = BlackMed, containerColor = Color.Transparent),contentPadding= PaddingValues(0.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(painter = painterResource(id = R.drawable.chat), contentDescription = null, colorFilter = ColorFilter.tint(
                    BlackMed), modifier = Modifier.size(16.dp))
                Text(text = "Forum", fontSize = 16.sp, fontWeight = FontWeight.W400)
            }
        }
    }
}
@Composable
fun ProfileHeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    model = "https://toolset.com/wp-content/uploads/2018/06/909657-profile_pic.png",
                    contentDescription = null
                )

            }


            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Username",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "email",
                    fontSize = 18.sp,

                    )
            }
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
        }

    }
}

