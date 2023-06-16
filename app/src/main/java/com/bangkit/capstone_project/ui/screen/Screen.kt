package com.bangkit.capstone_project.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Camera : Screen("camera")
    object ListPlant : Screen("listplant")
    object Splash : Screen("splash")
    object DetailPlant : Screen("detailPlant/{slug}"){
        fun createRoute(slug:String) = "detailPlant/$slug"
    }
    object Task : Screen("task/{slug}"){
        fun createRoute(slug:String) = "task/$slug"
    }

    object Login : Screen("login")

    object Register : Screen("register")
    object OwnedPlant : Screen("ownPlant/{id}"){
        fun createRoute(id:Int) = "ownPlant/$id"
    }

    object EditTask : Screen("editTask/{id}"){
        fun createRoute(id:Int) = "editTask/$id"
    }
    object UserCamera : Screen("userCamera")

    object DiseaseCam : Screen("diseaseCam/{slug}"){
        fun createRoute(slug:String) = "diseaseCam/$slug"
    }

}