package no.uio.ifi.in2000.asravna.oblig1.ui.unitconverter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import no.uio.ifi.in2000.asravna.oblig1.converter
import no.uio.ifi.in2000.asravna.oblig1.ui.ConverterUnits
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun UnitConverter(navController: NavController) {
    var valueInput by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf(ConverterUnits.OUNCE) }
    var conversionResult by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(96.dp))

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
        Spacer(modifier = Modifier.padding(78.dp))

        OutlinedTextField(
            value = valueInput,
            singleLine = true,
            onValueChange = { valueInput = it },
            label = { Text("Enter value") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    conversionResult = converter(valueInput.toInt(), selectedUnit).toString()
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
        UnitDropdownMenu(title = "Convert from", selectedUnit = selectedUnit) { selectedUnit = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Convert Button
        Button(
            onClick = {
                conversionResult = converter(valueInput.toInt(), selectedUnit).toString()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Convert")
        }
        Spacer(modifier = Modifier.height(96.dp))

        ReadOnlyOutlinedTextField(value = conversionResult, label = "Result")

        Text(
            "something"
        )
        Button(
            onClick = { navController.navigate("palindrome_checker") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Switch to Palindrome Checker")
        }
    }
}


@Composable
fun ReadOnlyOutlinedTextField(label: String, value: String) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(text = label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
@Composable
fun UnitDropdownMenu(title: String, selectedUnit: ConverterUnits, onUnitSelected: (ConverterUnits) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

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
@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    MaterialTheme {
        Surface {
            UnitConverter(navController = rememberNavController())
        }
    }
}
