package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
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

        val recyclerView = binding.recyclerView
        val agregarButton = binding.btnAgregar

        animalController = AnimalController()
        adapter = AnimalAdapter(
            AnimalController.getAnimales(),
            ::onBorrarAnimal,
            ::onEditarAnimal
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        AnimalController.agregarAnimal(Animal(1, "Perro", 3))
        AnimalController.agregarAnimal(Animal(2, "Gato", 1))
        AnimalController.agregarAnimal(Animal(3, "Cobaya", 2))

        agregarButton.setOnClickListener {
            agregarAnimal()
        }
    }
    //metodo para agregar un animal
    private fun agregarAnimal() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregar Animal")

        val inputLayout = LinearLayout(this)
        inputLayout.orientation = LinearLayout.VERTICAL

        val inputNombre = EditText(this)
        inputNombre.hint = "Nombre"
        inputLayout.addView(inputNombre)

        val inputEdad = EditText(this)
        inputEdad.hint = "Edad"
        inputEdad.inputType = InputType.TYPE_CLASS_NUMBER
        inputLayout.addView(inputEdad)

        builder.setView(inputLayout)

        builder.setPositiveButton("Añadir") { _, _ ->
            val nuevoNombre = inputNombre.text.toString()
            val nuevaEdad = inputEdad.text.toString().toIntOrNull()

            if (nuevoNombre.isNotEmpty() && nuevaEdad != null) {
                val nuevoAnimal = Animal(AnimalController.getAnimales().size + 1, nuevoNombre, nuevaEdad)
                AnimalController.agregarAnimal(nuevoAnimal)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Datos no validos, añada datos validos", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    // Método de borrar animal
    private fun onBorrarAnimal(posicion: Int) {
        AnimalController.borrarAnimal(posicion)
        adapter.notifyDataSetChanged()
    }
    //Metodo para editar un animal
    private fun onEditarAnimal(posicion: Int) {
        val animal = AnimalController.obtenerAnimal(posicion)

        if (animal != null) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Editar Animal")

            val inputLayout = LinearLayout(this)
            inputLayout.orientation = LinearLayout.VERTICAL

            val inputNombre = EditText(this)
            inputNombre.hint = "Nombre"
            inputNombre.setText(animal.nombre)
            inputLayout.addView(inputNombre)

            val inputEdad = EditText(this)
            inputEdad.hint = "Edad"
            inputEdad.inputType = InputType.TYPE_CLASS_NUMBER
            inputEdad.setText(animal.edad.toString())
            inputLayout.addView(inputEdad)

            builder.setView(inputLayout)

            builder.setPositiveButton("Guardar") { _, _ ->
                val nuevoNombre = inputNombre.text.toString()
                val nuevaEdad = inputEdad.text.toString().toIntOrNull()

                if (nuevoNombre.isNotEmpty() && nuevaEdad != null) {
                    val nuevoAnimal = Animal(animal.id, nuevoNombre, nuevaEdad)
                    AnimalController.actualizarAnimal(posicion, nuevoAnimal)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Datos no validos, añada datos validos", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }
    }


}

