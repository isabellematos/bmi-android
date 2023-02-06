package br.senai.sp.jandira.bmicompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.VapeFree
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicompose.ui.theme.BMIComposeTheme
import br.senai.sp.jandira.bmicompose.utils.bmiCalculate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BMICalculator()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun BMICalculator() {

    var weightState by rememberSaveable() {
        mutableStateOf("")
    }
    var heightState by rememberSaveable() {
        mutableStateOf("")
    }

    var expandState by remember {
        mutableStateOf(false)
    }

    var bmiScoreState by remember {
        mutableStateOf(0.0)
    }

    var isWeightError by remember {
        mutableStateOf(false)
    }

    var isHeightError by remember {
        mutableStateOf(false)
    }

    //Objeto que controla a requisicao de foco (RequestFocus)
    val weightFocusRequester = FocusRequester()

    //Content
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(//header
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.bmi),
                contentDescription = "icone da aplicacao",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = stringResource(id = R.string.app_title),
                color = Color.Blue,
                fontSize = 40.sp,
                letterSpacing = 4.sp
            )

        }
        // Form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 44.dp)
        ) {
            Text(
                text = stringResource(id = R.string.weight),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 18.sp,
            )
            OutlinedTextField(
                value = weightState,
                onValueChange = { newWeight ->
                    var lastChar = if (newWeight.length == 0)
                        newWeight
                    else
                        newWeight.get(newWeight.length - 1)
                    var newValue = if (lastChar == '.' || lastChar == ',')
                        newWeight.dropLast(1) else newWeight
                    weightState = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(weightFocusRequester),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.VapeFree, contentDescription = "")
                },
                trailingIcon = {
                    Icon(imageVector = Icons.Rounded.Warning, contentDescription = "")
                },
                isError = isWeightError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.height),
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 18.sp,
            )
            OutlinedTextField(
                value = heightState,
                onValueChange = { newHeight ->
                    var lastChar = if (newHeight.length == 0)
                        newHeight
                    else
                        newHeight.get(newHeight.length - 1)
                    var newValue = if (lastChar == '.' || lastChar == ',')
                        newHeight.dropLast(1)
                    else
                        newHeight
                    heightState = newValue
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = isHeightError,
                singleLine = true,
                shape = RoundedCornerShape(16.dp)

            )
            Button(
                onClick = {
                    isWeightError = weightState.length == 0
                    isHeightError = heightState.length == 0
                    if (isHeightError == false && isWeightError == false) {
                        bmiScoreState = bmiCalculate(weightState.toInt(), heightState.toDouble())
                        expandState = true
                    }
                },

                //if (validate(heightState, weightState)) {
                // bmiScoreState = bmiCalculate(weightState.toInt(), heightState.toDouble())
                // expandState = true
                //}
                //},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults
                    .buttonColors(
                        Color(
                            red = 100,
                            green = 200,
                            blue = 150
                        )
                    )
            ) {
                Text(
                    text = stringResource(id = R.string.calculate),
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Spacer(
                modifier = Modifier
                    .height(32.dp)
            )


            //OutlinedTextField(
            // value = "",
            // onValueChange = {},
            // modifier = Modifier.fillMaxWidth(),
            // label = {
            //     Text(text = "Digite algo")
            // }
            // )
            // Spacer(modifier = Modifier.height(32.dp))
            // TextField(
            //    value = "",
            //   onValueChange = {},
            //    modifier = Modifier.fillMaxWidth(),
            //  label = {
            //      Text(text = )
            //   }
            // )
        }
        //Footer
        AnimatedVisibility(
            visible = expandState,
            enter = slideIn(tween(durationMillis = 500)) {
                IntOffset(it.width, 0)
            },
            exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.yourscore),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = String.format("%.2f", bmiScoreState),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Congratulations! Your weight is ideal!",
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Row() {
                        Button(
                            onClick = {
                                expandState = false
                                weightState = ""
                                heightState = ""
                                weightFocusRequester.requestFocus()
                            },
                            modifier = Modifier.padding(end = 12.dp),
                            colors = ButtonDefaults
                                .buttonColors(
                                    Color(red = 112, green = 58, blue = 238, alpha = 255)
                                )

                        ) {
                            Text(
                                text = stringResource(id = R.string.reset),
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                Color(red = 112, green = 58, blue = 238, alpha = 255)
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.share),
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                    }

                }
            }
        }
    }
}


//@Composable
//fun Teste() {
//    Row() {
//       for (x in 1 .. 3){
//       Button(onClick = {  }) {
//           Text(text = "Botao $x")
//            }
//       }
//   }
//}


@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun BMICalculatorPreview() {
    BMICalculator()

}