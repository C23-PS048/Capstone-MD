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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.bangkit.capstone_project.data.network.user.UserViewModel
import com.bangkit.capstone_project.helper.maskEmail
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.theme.BlackMed
import com.bangkit.capstone_project.ui.theme.OrangeLight
import com.bangkit.capstone_project.ui.theme.OrangeReg
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    prefViewModel: PreferenceViewModel,
    userViewModel: UserViewModel,
    showToast: (String) -> Unit,
    navigateToCam: () -> Unit
) {
    val session by prefViewModel.getLoginSession().collectAsState(initial = null)
    userViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->

        when (uiState) {
            is UiState.Loading -> {
             Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                 CircularProgressIndicator()
             }
                session?.apply {
                    if (id != null) {
                        if (token != null) {
                            userViewModel.getUser(id, token)
                        }
                    }

                }
            }

            is UiState.Success -> {
                val data = uiState.data?.userResult
                data?.apply {
                    if (name != null) {
                        if (email != null) {

                            UserProfilePage(
                                onLogout = onLogout,
                                userEmail = maskEmail(email),
                                userName = name,
                                showToast = showToast,
                                navigateToCam =
                                navigateToCam
                            )

                        }
                    }
                }


            }

            is UiState.Error -> {}
        }

    }


}


@Composable
fun UserProfilePage(
    onLogout: () -> Unit,
    userName: String,
    userEmail: String,
    showToast: (String) -> Unit,
    navigateToCam: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ProfileHeaderSection(
            userEmail = userEmail,
            userName = userName,
            onLogout = onLogout,
            navigateToCam = navigateToCam
        )

        Divider(color = Color.LightGray, thickness = 0.5.dp)
        Spacer(modifier = Modifier.size(2.dp))
        ProfileMenu(showToast = showToast)
    }
}

@Composable
fun ProfileMenu(showToast: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(Color.White)
    ) {
        Text(
            text = "Aktifitas",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            TextButton(
                onClick = { showToast("Forum Coming soon!!!") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = BlackMed,
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.chat),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                BlackMed
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Diskusikan Tanaman mu",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(shape = RoundedCornerShape(50), color = OrangeLight)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Coming Soon",
                            color = OrangeReg,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            TextButton(
                onClick = { showToast("Marketplace Coming soon!!!") },
                colors = ButtonDefaults.buttonColors(
                    contentColor = BlackMed,
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(

                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            imageVector = Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(
                                BlackMed
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Jual Panen kamu",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W400
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(shape = RoundedCornerShape(50), color = OrangeLight)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Coming Soon",
                            color = OrangeReg,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileHeaderSection(
    userName: String,
    userEmail: String,
    onLogout: () -> Unit,
    navigateToCam: () -> Unit
) {

    val openDialog = remember { mutableStateOf(false) }

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
                    model = "https://st3.depositphotos.com/6672868/13701/v/450/depositphotos_137014128-stock-illustration-user-profile-icon.jpg",
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )

            }


            Column(modifier = Modifier.padding(start = 16.dp)) {

                Text(
                    text = userName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = userEmail,
                    fontSize = 18.sp,

                    )

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
                    text = { Text("logout") },
                    onClick = {
                        openDialog.value = !openDialog.value
                        expanded = !expanded
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.ExitToApp,
                            contentDescription = null
                        )
                    })
            }

        }

    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            icon = { Icon(Icons.Outlined.ExitToApp, contentDescription = null, tint = Color.DarkGray) },
            title = {
                Text(text = "Logout")
            },
            text = {
                Text(
                    "Apa Anda yakin mau Keluar Aplikasi?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onLogout
                ) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Tidak")
                }
            }
        )
    }

}

