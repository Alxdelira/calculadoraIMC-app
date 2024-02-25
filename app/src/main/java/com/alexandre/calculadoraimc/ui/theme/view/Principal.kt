package com.alexandre.calculadoraimc.ui.theme.view

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexandre.calculadoraimc.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Principal() {
    val imageLogo = painterResource(R.drawable._image_logo)
    var peso by remember { mutableStateOf("") }
    var alt by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(false) }
    var classificacao by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier.fillMaxSize(), color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontSize = 20.sp,
                text = "Calcular IMC e Calculadora de Peso Ideal",
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = imageLogo,
                contentDescription = "Imagem de uma Balança",
            )
            Spacer(modifier = Modifier.padding(top = 15.dp))
            OutlinedTextField(
                maxLines = 1,
                value = peso,
                onValueChange = { peso = it },
                label = { Text(text = "Peso") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                suffix = { Text(text = "Kg") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
            OutlinedTextField(
                maxLines = 1,
                value = alt,
                onValueChange = { alt = it },
                label = { Text(text = "Altura") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                suffix = { Text(text = "Cm") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

                )
            Button(
                onClick = {
                    keyboardController?.hide()
                    classificacao = calcularIMC(peso.toInt(), alt.toDouble())
                    result = true
                },
                colors = ButtonDefaults.buttonColors(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Calcular")
            }
            if (result) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ), modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = classificacao,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    when (classificacao) {
                        "Adequado" -> {
//                            Image(painter = , contentDescription = )
                            Text(
                                text = "Adequado: Seu peso está dentro da faixa considerada adequada.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        "Magreza Grau I" -> {
                            Text(
                                text = "Magreza Grau I: Você está abaixo do peso considerado saudável.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        "Magreza Grau II" -> {
                            Text(
                                text = "Magreza Grau II: Você está muito abaixo do peso considerado saudável.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        "Pré-Obeso" -> {
                            Text(
                                text = "Pré-Obeso: Seu peso está ligeiramente acima do considerado saudável.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        "Obesidade Grau I" -> {
                            Text(
                                text = "Obesidade Grau I: Você está com sobrepeso e isso pode trazer riscos à saúde.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        "Obesidade Grau II" -> {
                            Text(
                                text = "Obesidade Grau II: Seu peso está significativamente acima do considerado saudável.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        "Obesidade Grau III" -> {
                            Text(
                                text = "Obesidade Grau III: Seu peso está muito acima do considerado saudável e representa um sério risco à saúde.",
                                modifier = Modifier.padding(16.dp),
                                textAlign = TextAlign.Center,
                            )
                        }

                        else -> {

                            Text(
                                text = "Classificação não disponível.",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center,

                                )
                        }
                    }
                }
            } else {
                Text(
                    text = "", color = Color.Red
                )
            }
        }
    }
}

// Função para calcular o IMC
fun calcularIMC(peso: Int, altura: Double): String {
    val altInMetros = altura / 100
    val imc = peso / (altInMetros * altInMetros)

    return when {
        imc < 16.0 -> "Magreza Grau II"
        imc in 16.0..16.9 -> "Magreza Grau II"
        imc in 17.0..18.4 -> "Magreza Grau I"
        imc in 18.5..24.9 -> "Adequado"
        imc in 25.0..29.9 -> "Pré-Obeso"
        imc in 30.0..34.9 -> "Obesidade Grau I"
        imc in 35.0..39.9 -> "Obesidade Grau II"
        else -> "Obesidade Grau III"
    }
}

@Composable
@Preview
fun PrincipalPreview() {
    Principal()
}



