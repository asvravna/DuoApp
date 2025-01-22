package no.uio.ifi.in2000.asravna.oblig1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var outputText by remember { mutableStateOf("") }
    var selectedInputUnit by remember { mutableStateOf("Ounce") }
    var selectedOutputUnit by remember { mutableStateOf("Cup") }
    val units = listOf("Ounce", "Cup", "Gallon", "Hogshead")
    var expandedInput by remember { mutableStateOf(false) }
    var expandedOutput by remember { mutableStateOf(false) }

    fun convertUnits(value: Double, fromUnit: String, toUnit: String): Double {
        // Convert input value to ounces
        val ounces = when (fromUnit) {
            "Ounce" -> value
            "Cup" -> value * 8
            "Gallon" -> value * 128
            "Hogshead" -> value * 8192
            else -> value
        }
        // Convert ounces to desired unit
        return when (toUnit) {
            "Ounce" -> ounces
            "Cup" -> ounces / 8
            "Gallon" -> ounces / 128
            "Hogshead" -> ounces / 8192
            else -> ounces
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Input Field
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Enter value") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                val inputValue = inputText.toDoubleOrNull()
                if (inputValue != null) {
                    val result = convertUnits(inputValue, selectedInputUnit, selectedOutputUnit)
                    outputText = result.toString()
                } else {
                    outputText = "Invalid input"
                }
            }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown for Input Unit Selection
        ExposedDropdownMenuBox(
            expanded = expandedInput,
            onExpandedChange = { expandedInput = !expandedInput }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedInputUnit,
                onValueChange = {},
                label = { Text("From") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedInput) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedInput,
                onDismissRequest = { expandedInput = false }
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(onClick = {
                        selectedInputUnit = unit
                        expandedInput = false
                    }, text = { Text(unit) })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown for Output Unit Selection
        ExposedDropdownMenuBox(
            expanded = expandedOutput,
            onExpandedChange = { expandedOutput = !expandedOutput }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedOutputUnit,
                onValueChange = {},
                label = { Text("To") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedOutput) },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedOutput,
                onDismissRequest = { expandedOutput = false }
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(onClick = {
                        selectedOutputUnit = unit
                        expandedOutput = false
                    }, text = { Text(unit) })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Convert Button
        Button(
            onClick = {
                val inputValue = inputText.toDoubleOrNull()
                if (inputValue != null) {
                    val result = convertUnits(inputValue, selectedInputUnit, selectedOutputUnit)
                    outputText = result.toString()
                } else {
                    outputText = "Invalid input"
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Convert")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Output Result
        Text("Converted Value: $outputText", style = MaterialTheme.typography.bodyLarge)
    }
}


@Preview
@Composable
fun UnitConverterPreview(){
    MaterialTheme{
        Surface{
            UnitConverterScreen(rememberNavController())
        }
    }
}
