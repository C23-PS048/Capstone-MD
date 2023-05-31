package com.bangkit.capstone_project.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Forum : Screen("forum")
    object Camera : Screen("camera")

}