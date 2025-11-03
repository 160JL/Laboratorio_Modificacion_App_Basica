package com.example.firstlab

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.firstlab.MainActivity.Companion.LEVEL_KEY
import com.example.firstlab.MainActivity.Companion.SCORE_KEY
import com.example.firstlab.MainActivity.Companion.USER_KEY
import com.example.firstlab.ui.theme.FirstLabTheme
import androidx.core.net.toUri

class EndGameActivity : ComponentActivity() {

    private var name: String = ""
    private var score: Int = 0
    private var level: Int = 0

    private var hasWon: Boolean = false
    private val goToMainActivity: () -> Unit = {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(SCORE_KEY, 0)
        intent.putExtra(LEVEL_KEY, 0)
        intent.putExtra(USER_KEY, name)
        startActivity(intent)
    }
    private val goToMail:() -> Unit = {
        val intent = Intent(Intent.ACTION_SENDTO);
        intent.data = "mailto:".toUri();
        intent.putExtra(Intent.EXTRA_SUBJECT, "Puntuación del jugador $name");
        intent.putExtra(Intent.EXTRA_TEXT, "El jugador $name ha obtenido una puntuación de $score puntos y ha alcanzado el nivel $level")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent);
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Recuperar los valores enviados a través del Intent
        score = intent.getIntExtra(MainActivity.SCORE_KEY, 0)
        level = intent.getIntExtra(MainActivity.LEVEL_KEY, 0)
        name = intent.getStringExtra(MainActivity.USER_KEY).toString()
        hasWon = intent.getBooleanExtra(MainActivity.HAS_WON, false)

        // Establecer el contenido usando Jetpack Compose
        setContent {
            FirstLabTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopAppBar(title = { Text(text = stringResource(R.string.end_game)) }) }
                ) { innerPadding ->
                    // Mostrar los valores recibidos en una columna
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (hasWon) {
                            true -> Text(
                                text = stringResource(R.string.you_win),
                                style = MaterialTheme.typography.headlineLarge
                            )

                            false -> Text(
                                text = stringResource(R.string.game_over),
                                style = MaterialTheme.typography.headlineLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Score: $score",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            text = "Level: $level",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Row {
                            when (hasWon) {
                                true -> Image(painter = painterResource(R.drawable.you_win), "")
                                false -> Image(painter = painterResource(R.drawable.game_over), "")
                            }
                            StandardButton(stringResource(R.string.send_data)) {
                                goToMail()
                            }
                        }
                        StandardButton(stringResource(R.string.restart)) {
                            // Acción al hacer clic: abrir EndGameActivity
                            goToMainActivity()
                        }
                    }
                }
            }
        }
    }


}
