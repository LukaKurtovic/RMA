package com.example.todo

import android.annotation.SuppressLint
import android.content.Intent
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

        if (noteType.equals("New")){
            addUpdateButton.text = "Save note"
            addUpdateButton.isClickable = false
            addUpdateButton.isEnabled = false
            mapButton.setOnClickListener {
                val title = noteTitleEdt.text.toString()
                val description = noteDescriptionEdt.text.toString()
                if (title.isNotBlank() && description.isNotBlank()){
                    val intent = Intent(applicationContext, ShowMapActivity::class.java)
                    intent.putExtra("noteType", "New")
                    intent.putExtra("title", title)
                    intent.putExtra("description", description)
                    startActivity(intent)
                }else {
                   Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (noteType.equals("NewMap")){
            var title = intent.getStringExtra("title")
            var description = intent.getStringExtra("description")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            addUpdateButton.text = ("Save note")
            addUpdateButton.isClickable = true
            addUpdateButton.isEnabled = true
            noteTitleEdt.setText(title)
            noteDescriptionEdt.setText(description)
            addUpdateButton.setOnClickListener {
                if(noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()){
                    title = noteTitleEdt.text.toString()
                    description = noteDescriptionEdt.text.toString()
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.addNote(Note(title!!, description!!, currentDate, latitude, longitude))
                    Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                }
            }
            mapButton.setOnClickListener {
                if (noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()){
                    val intent = Intent(applicationContext, ShowMapActivity::class.java)
                    title = noteTitleEdt.text.toString()
                    description = noteDescriptionEdt.text.toString()
                    intent.putExtra("noteType", "Edit")
                    intent.putExtra("title", title)
                    intent.putExtra("description", description)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
                }
            }
        }

        if (noteType.equals("Edit")){
            var title = intent.getStringExtra("title")
            var description = intent.getStringExtra("description")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            noteID = intent.getIntExtra("noteID", -1)
            addUpdateButton.text = "Update note"
            noteTitleEdt.setText(title)
            noteDescriptionEdt.setText(description)
            mapButton.setOnClickListener {
                if (noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()){
                    val intent = Intent(applicationContext, ShowMapActivity::class.java)
                    title = noteTitleEdt.text.toString()
                    description = noteDescriptionEdt.text.toString()
                    intent.putExtra("noteType", "EditExisting")
                    intent.putExtra("title", title)
                    intent.putExtra("description", description)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    intent.putExtra("noteID", noteID)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
                }
            }
            addUpdateButton.setOnClickListener {
                if (noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()){
                    title = noteTitleEdt.text.toString()
                    description = noteDescriptionEdt.text.toString()
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updatedNote = Note(title!!, description!!, currentDate, latitude, longitude)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                }else{
                    Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
                }
            }
        }
        if (noteType.equals("ExistingMap")){
            var title = intent.getStringExtra("title")
            var description = intent.getStringExtra("description")
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            noteID = intent.getIntExtra("noteID", -1)
            addUpdateButton.text = "Update note"
            noteTitleEdt.setText(title)
            noteDescriptionEdt.setText(description)
            mapButton.setOnClickListener {
                if (noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()){
                    val intent = Intent(applicationContext, ShowMapActivity::class.java)
                    title = noteTitleEdt.text.toString()
                    description = noteDescriptionEdt.text.toString()
                    intent.putExtra("noteType", "EditExisting")
                    intent.putExtra("title", title)
                    intent.putExtra("description", description)
                    intent.putExtra("latitude", latitude)
                    intent.putExtra("longitude", longitude)
                    startActivity(intent)
                }else {
                    Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
                }
            }
            addUpdateButton.setOnClickListener {
                if (noteTitleEdt.text.toString().isNotBlank() && noteDescriptionEdt.text.toString().isNotBlank()){
                    title = noteTitleEdt.text.toString()
                    description = noteDescriptionEdt.text.toString()
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updatedNote = Note(title!!, description!!, currentDate, latitude, longitude)
                    updatedNote.id = noteID
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    this.finish()
                }else{
                    Toast.makeText(this, "Please fill out title and description", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}