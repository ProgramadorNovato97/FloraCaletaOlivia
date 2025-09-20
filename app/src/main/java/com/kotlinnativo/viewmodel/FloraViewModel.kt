package com.kotlinnativo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kotlinnativo.data.Planta
import com.kotlinnativo.data.PlantaRepository

class FloraViewModel(private val repository: PlantaRepository) : ViewModel() {

    private val _plantas = MutableStateFlow<List<Planta>>(emptyList())
    val plantas: StateFlow<List<Planta>> = _plantas.asStateFlow()

    init {
        cargarPlantas()
    }

    private fun cargarPlantas() {
        viewModelScope.launch {
            repository.obtenerPlantas().collect {
                _plantas.value = it
            }
        }
    }
}