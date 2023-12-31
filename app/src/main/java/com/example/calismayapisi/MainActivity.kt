package com.example.calismayapisi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.calismayapisi.ui.theme.CalismaYapisiTheme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalismaYapisiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SayfaGecisleri()
                }
            }
        }
    }
}
@Composable
fun SayfaGecisleri() {
    val  navController = rememberNavController()
    NavHost(navController = navController, startDestination = "anasayfa") {
        composable("anasayfa") {
            AnaSayfa(navController = navController)
        }
        composable("sayfa_a/{nesne}",
            arguments = listOf( //degerleri alma
                navArgument("nesne") { type = NavType.StringType }
            )
        ) {// degerleri donusturme
            val json = it.arguments?.getString("nesne")
            val nesne = Gson().fromJson(json,Kisiler::class.java)
            SayfaA(navController = navController, nesne)
        }
        composable("sayfa_b") {
            SayfaB()
        }
    }
}
@Composable
fun AnaSayfa(navController: NavController) {

    val sayac = remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
        ){
        Text(text = "Anasayfa", fontSize = 50.sp)

        Button(onClick = {
            val kisi = Kisiler("ahmet",18,1.78f,true)
            val kisiJson = Gson().toJson(kisi) //nesnemi alacak ve JSON turune donusturecek, nesnemi stringlestirdim.
            navController.navigate("sayfa_a/$kisiJson")
        }) {
            Text(text = "Sayfa A'ya Git")
        }

        Text(text = "Sayac : ${sayac.value}")

        Button(onClick = {
            sayac.value = sayac.value + 1
        }) {
            Text(text = "Tikla")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalismaYapisiTheme {

    }
}