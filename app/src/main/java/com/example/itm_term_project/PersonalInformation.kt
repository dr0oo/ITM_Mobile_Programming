package com.example.itm_term_project

import android.os.Bundle
import android.widget.ViewSwitcher
import androidx.appcompat.app.AppCompatActivity

class PersonalInformation  : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.personal_information)

        setSupportActionBar(findViewById(R.id.my_toolbar))

        val name_switcher : ViewSwitcher = findViewById(R.id.name_switcher)



    }
}