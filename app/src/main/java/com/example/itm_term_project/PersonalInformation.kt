package com.example.itm_term_project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity

class PersonalInformation  : AppCompatActivity(){

    var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_information)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val switching_button : Button = findViewById(R.id.switching_button)

        val name_switcher : ViewSwitcher = findViewById(R.id.name_switcher)
        val id_switcher : ViewSwitcher = findViewById(R.id.id_switcher)
        val phone_switcher : ViewSwitcher = findViewById(R.id.phone_switcher)
        val email_switcher : ViewSwitcher = findViewById(R.id.email_switcher)

        switching_button.setOnClickListener{
            name_switcher.showNext()
            id_switcher.showNext()
            phone_switcher.showNext()
            email_switcher.showNext()
            if(isEditing == false){
                switching_button.setText("SAVE")
                isEditing = true
            }else{
                switching_button.setText("EDIT")
                isEditing = false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.pi -> {
            val intent = Intent(this, PersonalInformation::class.java)
            startActivity(intent)
            true
        }

        R.id.str -> {
            val intent = Intent(this, ScoreStorage::class.java)
            startActivity(intent)
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }


}