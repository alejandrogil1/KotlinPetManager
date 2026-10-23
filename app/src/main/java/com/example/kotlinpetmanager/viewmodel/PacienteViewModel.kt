package com.example.kotlinpetmanager.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.kotlinpetmanager.model.Paciente

class PacienteViewModel : ViewModel() {

    // Lista de pacientes (Estado observable)
    private val _listaPacientes = mutableStateListOf<Paciente>()
    val listaPacientes: List<Paciente> get() = _listaPacientes

    // Campos del formulario
    var nombre by mutableStateOf("")
        private set

    var especie by mutableStateOf("")
        private set

    var edad by mutableStateOf("")
        private set

    var contactoEmergencia by mutableStateOf("")
        private set

    var esUrgencia by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf("")
        private set

    // Actualizaciones de estado
    fun onNombreChange(nuevo: String) { nombre = nuevo }
    fun onEspecieChange(nuevo: String) { especie = nuevo }
    fun onEdadChange(nuevo: String) { edad = nuevo }
    fun onContactoChange(nuevo: String) { contactoEmergencia = nuevo }
    fun onUrgenciaChange(nuevo: Boolean) { esUrgencia = nuevo }

    // Validación e inserción
    fun agregarPaciente(): Boolean {
        if (nombre.isBlank() || especie.isBlank() || edad.isBlank() || contactoEmergencia.isBlank()) {
            mensajeError = "Por favor, completa todos los campos."
            return false
        }

        val edadInt = edad.toIntOrNull()
        if (edadInt == null || edadInt < 0) {
            mensajeError = "Ingresa una edad válida."
            return false
        }

        val nuevoPaciente = Paciente(
            id = (System.currentTimeMillis() % 10000).toInt(),
            nombre = nombre.trim(),
            especie = especie.trim(),
            edad = edadInt,
            contactoEmergencia = contactoEmergencia.trim(),
            esUrgencia = esUrgencia
        )

        _listaPacientes.add(0, nuevoPaciente)
        limpiarFormulario()
        return true
    }

    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edad = ""
        contactoEmergencia = ""
        esUrgencia = false
        mensajeError = ""
    }
}