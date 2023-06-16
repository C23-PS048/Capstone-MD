package com.bangkit.capstone_project.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.data.network.user.UserFactory
import com.bangkit.capstone_project.data.network.user.UserInjection
import com.bangkit.capstone_project.data.network.user.UserViewModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.input.InputTextField
import com.bangkit.capstone_project.ui.component.input.PasswordTextField


@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    navigateLogin: () -> Unit,
    viewModel: UserViewModel,
    showToast: (String) -> Unit
) {


    RegisterContent(

        showToast = showToast,
        navigateLogin = navigateLogin,
        onBack = onBack,
        viewModel = viewModel
    )
}


@Composable
fun RegisterContent(
    onBack: () -> Unit,

    modifier: Modifier = Modifier,
    navigateLogin: () -> Unit,
    viewModel: UserViewModel,
    showToast: (String) -> Unit
) {
    var inputEmail by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {


        Scaffold(topBar = {
            IconButton(
                onClick = onBack,
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Return To Login")
            }
        }) {
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
                            .padding(it)
                            .padding(start = 16.dp, bottom = 32.dp, top = 64.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.register_title),
                            fontSize = 48.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W500
                        )
                        Text(
                            text = stringResource(R.string.register_info),
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
                            label = "Name",
                            keyboardType = KeyboardType.Text,
                            placeholder = "ex. John Doe",
                            modifier = modifier.fillMaxWidth(),
                            text = username,
                            onValueChange = { username = it }
                        )
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

                                if (username.isEmpty()) {
                                    showToast("Username Tidak Boleh Kosong")
                                } else if (inputEmail.isEmpty()) {
                                    showToast("Email Tidak Boleh Kosong")

                                } else if (password.isEmpty()) {
                                    showToast("Password Tidak Boleh Kosong")

                                } else if (password.length < 8) {
                                    showToast("Password Tidak Valid")

                                } else {
                                    viewModel.registerUser(
                                        name = username,
                                        email = inputEmail,
                                        password = password
                                    )
                                }
                            },
                            modifier = modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.daftar),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 21.sp
                            )
                        }
                    }
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.have_account), fontSize = 16.sp)
                        TextButton(onClick = navigateLogin) {
                            Text(text = stringResource(R.string.login), fontSize = 16.sp)

                        }
                    }
                }
            }
        }
        viewModel.responseState.collectAsState().value.let { responseState ->
            when (responseState) {
                is UiState.Loading -> {

                    CircularProgressIndicator()
                }

                is UiState.Success -> {

                    val response = responseState.data


                    viewModel.resetResponseState()
                    showToast("Register Berhasil")
                    showToast("Silahkan Login")
                    navigateLogin()
                }

                is UiState.Error -> {
                    showToast(responseState.errorMessage)
                }
                else -> {}
            }
        }

    }
}

@Preview
@Composable
fun RegisterPreview() {
    RegisterScreen(onBack = {},
        navigateLogin = {},
        viewModel = androidx.lifecycle.viewmodel.compose.viewModel(
            factory = UserFactory(UserInjection.provideRepository())
        ),
        showToast = { }
    )
}
