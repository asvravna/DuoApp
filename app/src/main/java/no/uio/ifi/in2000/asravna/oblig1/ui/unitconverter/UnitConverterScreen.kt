package no.uio.ifi.in2000.asravna.oblig1.ui.unitconverter

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.asravna.oblig1.R
import no.uio.ifi.in2000.asravna.oblig1.converter
import no.uio.ifi.in2000.asravna.oblig1.ui.ConverterUnits


@Composable
fun UnitConverterScreen(navController: NavController) {

    var valueInput by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf(ConverterUnits.OUNCE) }
    var conversionResult by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }
    var isDirty by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }


    @Composable
    fun UnitDropdownMenu(
        title: String,
        selectedUnit: ConverterUnits,
        onUnitSelected: (ConverterUnits) -> Unit
    ) {

        Box {
            TextButton(onClick = { expanded = true }) {
                Text("$title: ${selectedUnit.name}")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                ConverterUnits.entries.forEach { unit ->
                    DropdownMenuItem(onClick = {
                        onUnitSelected(unit)
                        expanded = false
                    }, text = { Text(unit.name) })
                }
            }
        }
    }

    val updateResult = {
        conversionResult = if (isValid) {
            converter(valueInput.toInt(), selectedUnit).toString()
        } else {
            ""
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(110.dp))

        Text(
            "Unit Converter",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFF0096FF)
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(30.dp))

        OutlinedTextField(
            value = valueInput,
            singleLine = true,
            isError = !isValid && isDirty,
            onValueChange = { textInput ->
                valueInput = textInput
                isValid = textInput.checkIfInt()
                isDirty = true
            },
            label = { Text("Enter integer value in $selectedUnit") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    updateResult()
                }

            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (!isValid && isDirty) {
            Text(text = "Please enter valid text", color = Color.Red)
        }
        Box(
            modifier = Modifier.clickable { expanded = true }
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                UnitDropdownMenu(
                    title = "Convert from",
                    selectedUnit = selectedUnit,
                ) { selectedUnit = it }
                val swapIcon: Painter = painterResource(id = R.drawable.swap_vert_24dp_5f6368)
                Image(
                    painter = swapIcon,
                    contentDescription = "Swap Symbol",
                    modifier = Modifier.size(54.dp)
                        .padding(7.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF6A0DAD))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = updateResult,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Convert")
        }
        Spacer(modifier = Modifier.height(36.dp))


        ReadOnlyOutlinedTextField(value = conversionResult, label = "Result in liters")
        Spacer(modifier = Modifier.height(36.dp))

        Button(
            onClick = { navController.navigate("palindrome_checker") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Switch to Palindrome Checker")
        }
    }

}


fun String.checkIfInt(): Boolean {
    try {
        this.toInt()
    } catch (e: NumberFormatException) {
        return false
    }
    return true
}

@Composable
fun ReadOnlyOutlinedTextField(label: String, value: String) {
    OutlinedTextField(
        value = "$value L",
        onValueChange = {},
        label = { Text(text = label) },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    MaterialTheme {
        Surface {
            UnitConverterScreen(navController = rememberNavController())
        }
    }
}
