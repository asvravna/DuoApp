package no.uio.ifi.in2000.asravna.oblig1.ui.palindrome
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TextFieldContent()

        }
    }

    private fun isPalindrome(tekst: String): Boolean {
        val cleanedText = tekst.replace("\\s".toRegex(), "").lowercase()
        val reversedStr = cleanedText.reversed()
        return tekst.lowercase() == reversedStr

    }


    @Composable
    fun TextFieldContent() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center

            ) {
                Column(modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                    ){
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
                    var word: String by remember { mutableStateOf("") }
                    var isPalindrome by remember {mutableStateOf(false) }
                    var checked by remember { mutableStateOf(false) }

                    OutlinedTextField(
                    value = word,
                    onValueChange = {
                        word = it
                        checked = false
                    },
                    label = { Text("Word")

                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    isPalindrome = isPalindrome(word)
                    checked = true
                }) {
                    Text("Check")

                }
                if(checked) {
                    Text(
                        text = "$word is ${if (isPalindrome) "a palindrome" else "not a palindrome"}",
                        color = if (isPalindrome) Color(0xFF0B6623) else Color.Red
                    )
                }
            }
        }
    }


    @Preview
    @Composable
    fun IsPalindromePreview() {
        TextFieldContent()


    }


}


