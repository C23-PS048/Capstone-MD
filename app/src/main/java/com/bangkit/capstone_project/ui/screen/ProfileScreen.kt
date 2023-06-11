package com.bangkit.capstone_project.ui.screen

import android.util.Log
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.model.User
import com.bangkit.capstone_project.ui.theme.BlackMed

@Composable
fun ProfileScreen(onLogout:()->Unit, user: User) {
    Log.d("TAG", "user: ${user.foto}")
    UserProfilePage(onLogout = onLogout, user = user)

}

/*@Preview
@Composable
fun ProfilePreview() {
    UserProfilePage()
}*/

@Composable
fun UserProfilePage(onLogout:()->Unit, user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ProfileHeaderSection(user= user,onLogout=onLogout)

        Divider(color = Color.LightGray, thickness = 0.5.dp)
        Spacer(modifier = Modifier.size(2.dp))
        ProfileMenu()
    }
}

@Composable
fun ProfileMenu() {
    Column(  modifier = Modifier
        .fillMaxSize()

        .background(Color.White)) {
        Text(text = "Aktifitas", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
        Column( modifier = Modifier
            .fillMaxSize()) {

            TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(contentColor = BlackMed, containerColor = Color.Transparent),contentPadding= PaddingValues(vertical = 0.dp, horizontal = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Image(painter = painterResource(id = R.drawable.chat), contentDescription = null, colorFilter = ColorFilter.tint(
                        BlackMed), modifier = Modifier.size(20.dp))
                    Text(text = "Diskusikan Tanaman mu", fontSize = 16.sp, fontWeight = FontWeight.W400)
                }
            }
            TextButton(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(contentColor = BlackMed, containerColor = Color.Transparent),contentPadding= PaddingValues(vertical = 0.dp, horizontal = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Image(imageVector = Icons.Outlined.ShoppingCart, contentDescription = null, colorFilter = ColorFilter.tint(
                        BlackMed), modifier = Modifier.size(20.dp))
                    Text(text = "Jual Panen kamu", fontSize = 16.sp, fontWeight = FontWeight.W400)
                }
            }
        }
    }
}
@Composable
fun ProfileHeaderSection(user: User, onLogout: () -> Unit) {
    Log.d("TAG", "ProfileHeaderSection: ${user.foto}")
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
                    model = user.foto,
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )

            }


            Column(modifier = Modifier.padding(start = 16.dp)) {
                user.name?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                user.email?.let {
                    Text(
                        text = it,
                        fontSize = 18.sp,

                        )
                }
            }
        }
        Box(

        ) {
            var expanded by remember { mutableStateOf(false) }

            IconButton(onClick = { expanded = !expanded }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = {},
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = null
                        )
                    })
                DropdownMenuItem(
                    text = { Text("logout") },
                    onClick = onLogout,
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.ExitToApp,
                            contentDescription = null
                        )
                    })
            }

        }

    }
}

