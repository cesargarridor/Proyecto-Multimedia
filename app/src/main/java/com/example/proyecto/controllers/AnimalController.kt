package com.example.proyecto.controllers

import com.example.proyecto.modelo.Animal

class AnimalController {
    companion object {
        private val animales: MutableList<Animal> = mutableListOf()

        fun getAnimales(): List<Animal> {
            return animales
        }

        fun agregarAnimal(animal: Animal) {
            animales.add(animal)
        }

        fun borrarAnimal(posicion: Int) {
            if (posicion in 0 until animales.size) {
                animales.removeAt(posicion)
            }
        }
    }
}

