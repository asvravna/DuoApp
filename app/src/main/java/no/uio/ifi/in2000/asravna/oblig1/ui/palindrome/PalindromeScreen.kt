package no.uio.ifi.in2000.asravna.oblig1.ui.palindrome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.asravna.oblig1.isPalindrome


@Composable
fun PalindromeScreen(navController: NavController) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var word by remember { mutableStateOf("") }
    var isPalindrome by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var labelText by remember { mutableStateOf("Enter word") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Palindrome Checker",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF0096FF)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(46.dp))

            OutlinedTextField(
                value = word,
                onValueChange = {
                    word = it
                    checked = false
                    if (it.isEmpty()) {
                        labelText = "Enter word"
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = labelText) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (word.isNotEmpty()) {
                            labelText = ""
                            isPalindrome = isPalindrome(word)
                            checked = true
                        } else {
                            labelText = "No words entered"

                        }
                        keyboardController?.hide()
                    }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (word.isNotEmpty()) {
                    isPalindrome = isPalindrome(word)
                    checked = true
                } else {
                    labelText = "No words entered"
                }
            }) {
                Text("Check")
            }

            if (checked && word.isNotEmpty()) {
                Text(
                    text = "$word is ${if (isPalindrome) "a palindrome" else "not a palindrome"}",
                    color = if (isPalindrome) Color(0xFF0B6623) else Color.Red
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("unit_converter") },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Switch to Unit Converter")
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PalindromePreview(){
    MaterialTheme{
        Surface{
            PalindromeScreen(navController = rememberNavController())

        }
    }


}

