package com.example.firstlab

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firstlab.MainActivity.Companion.LEVEL_KEY
import com.example.firstlab.MainActivity.Companion.SCORE_KEY
import com.example.firstlab.MainActivity.Companion.USER_KEY
import com.example.firstlab.ui.theme.FirstLabTheme

class LauncherActivity : ComponentActivity() {
    private val goToMainActivity : (username : String) -> Unit =  {username ->
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(USER_KEY, username)
        startActivity(intent)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Recuperar los valores enviados a través del Intent
        val score = intent.getIntExtra(SCORE_KEY, 0)
        val level = intent.getIntExtra(LEVEL_KEY, 0)
        

        // Establecer el contenido usando Jetpack Compose
        setContent {
            FirstLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = "End Game") }) }
                ) { innerPadding ->
                    // Mostrar los valores recibidos en una columna
                    extracted(innerPadding)
                }
            }
        }
    }

    @Composable
    private fun extracted(innerPadding: PaddingValues) {
        var username by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Enter Your name:")
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
            )
            StandardButton("Play") {
                // Acción al hacer clic: abrir EndGameActivity
                goToMainActivity(username)
            }
        }
    }
}

