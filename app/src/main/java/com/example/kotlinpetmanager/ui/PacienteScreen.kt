package com.example.kotlinpetmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinpetmanager.model.Paciente
import com.example.kotlinpetmanager.viewmodel.PacienteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacienteScreen(
    viewModel: PacienteViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KotlinPet Manager 🐾") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Formulario de Registro
            Text(
                text = "Registrar Nuevo Paciente",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre del Paciente") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = viewModel.especie,
                onValueChange = { viewModel.onEspecieChange(it) },
                label = { Text("Especie / Raza") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = viewModel.edad,
                    onValueChange = { viewModel.onEdadChange(it) },
                    label = { Text("Edad (años)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = viewModel.contactoEmergencia,
                    onValueChange = { viewModel.onContactoChange(it) },
                    label = { Text("Contacto Emergencia") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1.5f),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = viewModel.esUrgencia,
                    onCheckedChange = { viewModel.onUrgenciaChange(it) }
                )
                Text(text = "Marcar como Atención de Urgencia")
            }

            if (viewModel.mensajeError.isNotBlank()) {
                Text(
                    text = viewModel.mensajeError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.agregarPaciente() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Paciente")
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Lista Dinámica
            Text(
                text = "Pacientes Registrados (${viewModel.listaPacientes.size})",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(viewModel.listaPacientes) { paciente ->
                    PacienteItem(paciente = paciente)
                }
            }
        }
    }
}

@Composable
fun PacienteItem(paciente: Paciente) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (paciente.esUrgencia)
                MaterialTheme.colorScheme.errorContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = paciente.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                if (paciente.esUrgencia) {
                    Text(
                        text = "🚨 URGENCIA",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
            Text(text = "Especie: ${paciente.especie} | Edad: ${paciente.edad} año(s)")
            Text(text = "Contacto: ${paciente.contactoEmergencia}")
        }
    }
}