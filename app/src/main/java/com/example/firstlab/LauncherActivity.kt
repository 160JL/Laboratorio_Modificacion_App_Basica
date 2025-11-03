package com.example.firstlab

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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

        setContent {
            FirstLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.launcher)) }) }
                ) { innerPadding ->
                    // Mostrar los valores recibidos en una columna
                    TextField(innerPadding)
                }
            }
        }
    }

    @Composable
    private fun TextField(innerPadding: PaddingValues) {
        var username by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.enter_name))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
            )
            StandardButton(stringResource(R.string.play)) {
                // Acción al hacer clic: abrir EndGameActivity
                if (!username.isBlank()){goToMainActivity(username)}

            }
        }
    }
}

