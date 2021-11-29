package com.example.itm_term_project

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.nambimobile.widgets.efab.ExpandableFab
import com.nambimobile.widgets.efab.ExpandableFabLayout
import com.nambimobile.widgets.efab.FabOption

class ScoreStorage : AppCompatActivity() {

    lateinit var slAdapter: ScoreListAdapter
    val datas = mutableListOf<ScoreList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_stroage)

        var title_number = 0
        var new_number = 0

        //app bar
        setSupportActionBar(findViewById(R.id.my_toolbar))

        //score list adapter declaration
        slAdapter = ScoreListAdapter(this)

        //recycler view declaration
        val slRecyclerView: RecyclerView = findViewById(R.id.sl_recycler)

        //bind view with adapter
        slRecyclerView.adapter = slAdapter

        //floating action button declaration
        val fab: FabOption = findViewById(R.id.fab_menu_setting)

        // database declaration
        val database = Firebase.database
        val myRef = database.getReference("ScoreList")

        myRef.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value:ArrayList<String> = snapshot.getValue() as ArrayList<String>
                Log.d(TAG, "length value is: "+ value.size)
                Log.d(TAG, "Value is: " + value.toString())

                title_number = value.size-1
                new_number = value.size

                for( i in 1..(value.size-1) ){
                    datas.apply{
                        add(ScoreList(i, value.get(i) ))
                        slAdapter.datas = datas
                        slAdapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

        // Read from the database
        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value:ArrayList<String> = snapshot.getValue() as ArrayList<String>
                Log.d(TAG, "length value is: "+ value.size)
                Log.d(TAG, "Value is: " + value.toString())

                title_number = value.size-1
                new_number = value.size

                datas.apply{
                    add(ScoreList(new_number, "title"+"$new_number"))
                    slAdapter.datas = datas
                    slAdapter.notifyDataSetChanged()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        fab.setOnClickListener(View.OnClickListener {
            myRef.child("$new_number").setValue("title"+"$new_number")
        })


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