package com.example.todo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var noteTitleEdt: EditText
    private lateinit var noteDescriptionEdt: EditText
    private lateinit var addUpdateButton: Button
    private lateinit var viewModel: NoteViewModel
    private lateinit var mapButton: FloatingActionButton

    private var noteID = -1
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            deleteSharedPreferences("sharedPrefs")
        }
        this.finish()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)
        noteTitleEdt = findViewById(R.id.idEditNoteTitle)
        noteDescriptionEdt = findViewById(R.id.idEditNoteDescription)
        addUpdateButton = findViewById(R.id.idBtnAddUpdate)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)
        mapButton = findViewById(R.id.fab_map)
        if (supportActionBar != null) {
            val actionBar: ActionBar? = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }

        val noteType = intent.getStringExtra("noteType")

        if(noteType.equals("New")){
            load()
            val latitude = intent.getDoubleExtra("latitude", 91.0)
            val longitude = intent.getDoubleExtra("longitude", 181.0)
            if (latitude!=91.0 && longitude!=181.0 && isValid()){
                enable()
                addUpdateButton.text = "Save note"
            }else disable()
            mapButton.setOnClickListener {
                if (isValid()){
                    save()
                    val intent = Intent(applicationContext, ShowMapActivity::class.java)
                    intent.putExtra("noteType", "New")
                    startActivity(intent)
                }else Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
            }
            addUpdateButton.setOnClickListener {
                if (isValid()){
                    val title = noteTitleEdt.text.toString()
                    val description = noteDescriptionEdt.text.toString()
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addNote(Note(title, description, currentDate, latitude, longitude))
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        deleteSharedPreferences("sharedPrefs")
                    }
                }else Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
            }
        }



        if (noteType.equals("Edit")){
            load()
            val latitude = intent.getDoubleExtra("latitude", 91.0)
            val longitude = intent.getDoubleExtra("longitude", 181.0)
            addUpdateButton.text = "Update note"
            addUpdateButton.setOnClickListener {
                if (isValid()){
                    val title = noteTitleEdt.text.toString()
                    val description = noteDescriptionEdt.text.toString()
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updatedNote = Note(title, description, currentDate, latitude, longitude)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        deleteSharedPreferences("sharedPrefs")
                    }
                }else Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
            }
            mapButton.setOnClickListener {
                if (isValid()){
                    save()
                    val intent = Intent(applicationContext, ShowMapActivity::class.java)
                    intent.putExtra("noteType", "Edit")
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    startActivity(intent)
                }else Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun disable(){
        addUpdateButton.isClickable = false
        addUpdateButton.isEnabled = false
    }

    private fun enable(){
        addUpdateButton.isClickable = true
        addUpdateButton.isEnabled = true
    }

    private fun isValid(): Boolean{
        return noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()
    }

    private fun load(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val title = sharedPreferences.getString("TITLE", null)
        val description = sharedPreferences.getString("DESCRIPTION", null)
        noteID = sharedPreferences.getInt("ID", -1)
        noteTitleEdt.setText(title)
        noteDescriptionEdt.setText(description)

    }

   private fun save(){
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putString("TITLE", noteTitleEdt.text.toString())
            putString("DESCRIPTION", noteDescriptionEdt.text.toString())
        }.apply()
   }

}