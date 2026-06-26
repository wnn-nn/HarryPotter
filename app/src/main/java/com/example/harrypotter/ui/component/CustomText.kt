package com.example.harrypotter.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.harrypotter.ui.theme.*

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    isReadOnly: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        readOnly = isReadOnly,
        enabled = !isReadOnly,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = warnaAksen,
            unfocusedBorderColor = warnaTinta,
            focusedTextColor = warnaTinta,
            unfocusedTextColor = warnaTinta,
            focusedLabelColor = warnaAksen,
            // Warna khusus jika TextField ini dikunci (read-only di halaman profil)
            disabledTextColor = warnaTinta,
            disabledBorderColor = warnaTinta.copy(alpha = 0.3f),
            disabledLabelColor = warnaTinta.copy(alpha = 0.8f)
        )
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = warnaTinta.copy(alpha = 0.7f), style = MaterialTheme.typography.bodyLarge)
        Text(value, color = warnaTinta, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyLarge)
    }
}
