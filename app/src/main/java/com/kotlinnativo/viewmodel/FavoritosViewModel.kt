package com.kotlinnativo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kotlinnativo.data.Planta
import com.kotlinnativo.data.PlantaRepository

class FavoritosViewModel(private val repository: PlantaRepository) : ViewModel() {

    private val _plantasFavoritas = MutableStateFlow<List<Planta>>(emptyList())
    val plantasFavoritas: StateFlow<List<Planta>> = _plantasFavoritas.asStateFlow()

    init {
        cargarPlantasFavoritas()
    }

    private fun cargarPlantasFavoritas() {
        viewModelScope.launch {
            repository.obtenerPlantasFavoritas().collect {
                _plantasFavoritas.value = it
            }
        }
    }
}