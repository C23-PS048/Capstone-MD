package com.bangkit.capstone_project.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bangkit.capstone_project.data.network.plant.PlantResult
import com.bangkit.capstone_project.data.network.plant.PlantViewModel
import com.bangkit.capstone_project.ui.UiState
import com.bangkit.capstone_project.ui.component.cards.PlantCards
import com.bangkit.capstone_project.ui.theme.GrayLight

@Composable
fun ListScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onclick: () -> Unit,
    plantViewModel: PlantViewModel,
    token: String?,
    navController: NavHostController
) {
    plantViewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                token.let {  it1 ->
                    if (it1 != null) {
                        plantViewModel.getAll(it1)
                    }
                }
            }

            is UiState.Success -> {

                val data = uiState.data?.plantList
                Log.d("TAG", "ListScreen: $data")

                if (data != null) {
                    ListContent(
                        onBack = onBack, listPlant = data, onclick = onclick,
                        navController =navController)
                }

            }

            is UiState.Error -> {}

        }
    }


}

@Composable
fun ListContent(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    listPlant: List<PlantResult>,
    onclick: () -> Unit,
    navController: NavHostController
) {
    Log.d("TAG", "ListContent: $listPlant")
    Scaffold(topBar = {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Text(text = "List Plant", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
            Divider(thickness = 3.dp, color = GrayLight)
        }

    }, modifier = modifier
        .fillMaxWidth()) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(24.dp),
            modifier = modifier.padding(padding)
        ) {

              items(listPlant) {
                  Box(Modifier.width(150.dp), contentAlignment = Alignment.Center) {
                      PlantCards(onClick = { navController.navigate(Screen.DetailPlant.createRoute(it.slug)) }, data = it)
                  }
              }
          }
        }

    }
