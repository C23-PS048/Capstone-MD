package com.bangkit.capstone_project.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.user.UserViewModel
import com.bangkit.capstone_project.model.UserModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.input.InputTextField
import com.bangkit.capstone_project.ui.component.input.PasswordTextField
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel


@Composable
fun LoginScreen(
    prefViewModel: PreferenceViewModel,
    viewModel: UserViewModel,
    navigateMain: () -> Unit,
    navigateRegis: () -> Unit,
    showToast: (String) -> Unit
) {

    LoginContent(
        viewModel = viewModel,
        navigateMain = navigateMain,
        navigateRegis = navigateRegis,
        prefViewModel = prefViewModel,
        showToast = showToast
    )
}


@Composable
fun LoginContent(
    navigateRegis: () -> Unit,
    modifier: Modifier = Modifier,
    navigateMain: () -> Unit,
    prefViewModel: PreferenceViewModel,
    viewModel: UserViewModel,
    showToast: (String) -> Unit
) {
    var inputEmail by remember {
        mutableStateOf("")
    }


    var password by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Column() {
            Box(
                modifier = Modifier

                    .fillMaxWidth()
                    .paint(
                        painter = painterResource(id = R.drawable.bg_input),
                        contentScale = ContentScale.FillBounds
                    )
            ) {

                Column(
                    modifier = Modifier

                        .padding(start = 16.dp, bottom = 32.dp, top = 64.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Login",
                        fontSize = 48.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W500
                    )
                    Text(
                        text = "Masukkan Informasi Akun Kamu",
                        fontSize = 18.sp,
                        color = Color.LightGray
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxHeight()
                    .padding(vertical = 16.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    InputTextField(
                        label = "Email",
                        keyboardType = KeyboardType.Email,
                        placeholder = "Email",
                        modifier = modifier.fillMaxWidth(),
                        text = inputEmail,
                        onValueChange = { inputEmail = it }
                    )
                    PasswordTextField(
                        text = password,
                        onValueChange = { password = it },
                        modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            if (inputEmail.isEmpty()) {
                                showToast("Email Tidak Boleh Kosong")

                            } else if (password.isEmpty()) {
                                showToast("Password Tidak Boleh Kosong")

                            } else if (password.length < 8) {
                                showToast("Password Tidak Valid")

                            } else {

                                viewModel.loginUser(
                                    inputEmail,
                                    password
                                )
                            }


                        },
                        modifier = modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        Text(text = "Login", fontWeight = FontWeight.SemiBold, fontSize = 21.sp)
                    }
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Belum punya akun? ", fontSize = 16.sp)
                    TextButton(onClick = navigateRegis) {
                        Text(text = "Daftar", fontSize = 16.sp)

                    }
                }
            }
        }
        viewModel.loginState.collectAsState().value.let { responseState ->
            when (responseState) {
                is UiState.Loading -> {

                    CircularProgressIndicator()
                }

                is UiState.Success -> {

                    val response = responseState.data
                    if (response != null) {
                        prefViewModel.saveSession(
                            UserModel(
                                response.loginResult.name,
                                response.loginResult.token,
                                response.loginResult.id
                            )
                        )
                        showToast(stringResource(R.string.login_response))
                        viewModel.resetResponseState()
                        navigateMain()
                    }


                }

                is UiState.Error -> {
                    showToast(responseState.errorMessage)
                }

                else -> {}
            }
        }


    }
}


