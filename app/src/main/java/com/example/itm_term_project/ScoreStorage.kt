package com.example.itm_term_project

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.nambimobile.widgets.efab.ExpandableFab
import com.nambimobile.widgets.efab.ExpandableFabLayout
import com.nambimobile.widgets.efab.FabOption

class ScoreStorage : AppCompatActivity() {

    lateinit var slAdapter: ScoreListAdapter
    val datas = mutableListOf<ScoreList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_stroage)

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

        fab.setOnClickListener(View.OnClickListener {
            datas.apply{
                add(ScoreList("title1"))
                slAdapter.datas = datas
                slAdapter.notifyDataSetChanged()
            }
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