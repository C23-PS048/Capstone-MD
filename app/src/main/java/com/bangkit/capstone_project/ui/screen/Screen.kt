package com.bangkit.capstone_project.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Forum : Screen("forum")
    object Camera : Screen("camera")
    object ListPlant : Screen("listplant")
    object DetailPlant : Screen("detailPlant")
    object Task : Screen("task")

    object Login : Screen("login")

    object Register : Screen("register")
    object OwnedPlant : Screen("ownedplant")

}