package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.Adapters.AnimalAdapter
import com.example.proyecto.controllers.AnimalController
import com.example.proyecto.databinding.ActivityMainBinding
import com.example.proyecto.modelo.Animal

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var animalController: AnimalController
    private lateinit var adapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Utiliza binding para obtener la referencia del RecyclerView
        val recyclerView = binding.recyclerView

        animalController = AnimalController()
        adapter = AnimalAdapter(AnimalController.getAnimales(), ::onBorrarAnimal)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Agregar algunos animales de ejemplo
        AnimalController.agregarAnimal(Animal(1, "Perro", 3))
        AnimalController.agregarAnimal(Animal(2, "Gato", 2))
        AnimalController.agregarAnimal(Animal(3, "Pájaro", 1))
    }

    // Método llamado cuando se quiere borrar un animal
    private fun onBorrarAnimal(posicion: Int) {
        AnimalController.borrarAnimal(posicion)
        adapter.notifyDataSetChanged()
    }
}

