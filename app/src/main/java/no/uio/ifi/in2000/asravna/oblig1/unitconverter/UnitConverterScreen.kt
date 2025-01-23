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
import no.uio.ifi.in2000.asravna.oblig1.ui.ConverterUnits


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(navController: NavController) {
    var inputText by remember { mutableStateOf("") }
    var outputText by remember { mutableStateOf("") }
    var selectedInputUnit by remember { mutableStateOf("Ounce") }
    var selectedOutputUnit by remember { mutableStateOf("Cup") }
    val units = ConverterUnits.entries.map { it.name }

    var expandedInput by remember { mutableStateOf(false) }
    var expandedOutput by remember { mutableStateOf(false) }

    fun unitConverter(value: Double, fromUnit: String, toUnit: String): Double {
        val liters = when (fromUnit) {
            "Ounce" -> value * 0.02957
            "Cup" -> value * 0.23659
            "Gallon" -> value * 3.78541
            "Hogshead" -> value * 238.481
            else -> value
        }

        return when (toUnit) {
            "Ounce" -> liters / 0.02957
            "Cup" -> liters / 0.23659
            "Gallon" -> liters / 3.78541
            "Hogshead" -> liters / 238.481
            else -> liters
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

        OutlinedTextField(
            value = inputText,
            singleLine = true,
            onValueChange = { inputText = it },
            label = { Text("Enter value") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                val inputValue = inputText.toDoubleOrNull()
                if (inputValue != null) {
                    val result = unitConverter(inputValue, selectedInputUnit, selectedOutputUnit)
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
                readOnly = false,
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
                    val result = unitConverter(inputValue, selectedInputUnit, selectedOutputUnit)
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
        Spacer(modifier = Modifier.height(62.dp))

        Button(
            onClick = { navController.navigate("unit_converter") },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Switch to Palindrome Checker")
        }
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
