package com.bangkit.capstone_project.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bangkit.capstone_project.R
import com.bangkit.capstone_project.ui.theme.CapstoneProjectTheme
import com.bangkit.capstone_project.ui.theme.GreenDark
import com.bangkit.capstone_project.ui.theme.Ivory
import com.bangkit.capstone_project.viewmodel.preference.PreferenceViewModel
import kotlinx.coroutines.delay


@Composable
fun AnimatedSplash(navController: NavHostController, prefViewModel: PreferenceViewModel,) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )
    val session by prefViewModel.getLoginSession().collectAsState(initial = null)
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(4000)
        navController.popBackStack()
        if (session==null){

        navController.navigate(Screen.Login.route)
        }else{
        navController.navigate(Screen.Home.route)

        }
    }
    SplashScreen(alpha = alphaAnim.value)
}

@Composable
fun SplashScreen(alpha: Float) {
    Box(
        modifier = Modifier
            .background(Ivory)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(120.dp)
                .alpha(alpha = alpha),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Icon",

        )
    }
}

@Preview
@Composable
fun SplashPreview() {
    CapstoneProjectTheme {
        SplashScreen(0.6f)
    }
}