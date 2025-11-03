package com.example.firstlab

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.firstlab.MainActivity.Companion.LEVEL_KEY
import com.example.firstlab.MainActivity.Companion.SCORE_KEY
import com.example.firstlab.ui.theme.FirstLabTheme

class EndGameActivity : ComponentActivity() {
    private val goToMainActivity : () -> Unit =  {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SCORE_KEY, 0)
        intent.putExtra(LEVEL_KEY, 0)
        startActivity(intent)
    }
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Recuperar los valores enviados a través del Intent
        val score = intent.getIntExtra(MainActivity.SCORE_KEY, 0)
        val level = intent.getIntExtra(MainActivity.LEVEL_KEY, 0)

        // Establecer el contenido usando Jetpack Compose
        setContent {
            FirstLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = "End Game") }) }
                ) { innerPadding ->
                    // Mostrar los valores recibidos en una columna
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when(level){
                            10 -> Text(text = "¡Felicidades, alcanzaste el nivel 10!", style = MaterialTheme.typography.headlineLarge)
                            else -> Text(text = "Juego terminado. Pulsa el botón para volver a empezar", style = MaterialTheme.typography.headlineLarge)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Score: $score", style = MaterialTheme.typography.headlineMedium)
                        Text(text = "Level: $level", style = MaterialTheme.typography.headlineMedium)
                        when (level){
                            10 -> Image(painter = painterResource(R.drawable.you_win),"")
                            else -> Image(painter = painterResource(R.drawable.game_over),"")
                        }
                        StandardButton("Restart") {
                            // Acción al hacer clic: abrir EndGameActivity
                            goToMainActivity()
                        }
                    }
                }
            }
        }
    }


}
