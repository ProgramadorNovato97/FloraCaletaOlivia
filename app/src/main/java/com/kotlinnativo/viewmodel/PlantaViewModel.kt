package com.kotlinnativo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kotlinnativo.data.Planta
import com.kotlinnativo.data.PlantaRepository

class PlantaViewModel(private val repository: PlantaRepository) : ViewModel() {

    private val _planta = MutableStateFlow<Planta?>(null)
    val planta: StateFlow<Planta?> = _planta.asStateFlow()

    private val _esFavorito = MutableStateFlow(false)
    val esFavorito: StateFlow<Boolean> = _esFavorito.asStateFlow()

    //*** Cargamos plantas apenas inicie y tambien recolectamos aquellos que son favs***
    fun cargarPlanta(plantaId: String) {
        viewModelScope.launch {
            _planta.value = repository.obtenerPlanta(plantaId)

            repository.esFavorito(plantaId).collect {
                _esFavorito.value = it
            }
        }
    }
    //*** Cambia de estado aquellos que son favs***
    fun cambiarFavorito() {
        viewModelScope.launch {
            _planta.value?.let { planta ->
                repository.cambiarFavorito(planta.id, !_esFavorito.value)
            }
        }
    }
}