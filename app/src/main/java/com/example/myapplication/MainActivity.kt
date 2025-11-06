package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.res.Configuration
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardsAdapter
    private lateinit var fab: FloatingActionButton

    private companion object {
        const val ITEMS_KEY = "items_key"
    }

    private val items = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        fab = findViewById(R.id.fab)

        savedInstanceState?.getIntArray(ITEMS_KEY)?.let { savedItems ->
            items.clear()
            items.addAll(savedItems.toList())
        }

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = CardsAdapter()

        val spanCount = getColumnCount()
        val layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter.updateItems(items)
    }

    private fun getColumnCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            resources.getInteger(R.integer.landscape_columns)
        } else {
            resources.getInteger(R.integer.portrait_columns)
        }
    }

    private fun setupClickListeners() {
        fab.setOnClickListener {
            addNewItem()
        }
    }

    private fun addNewItem() {
        items.add(items.size)
        adapter.updateItems(items)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray(ITEMS_KEY, items.toIntArray())
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        val spanCount = getColumnCount()
        val layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.layoutManager = layoutManager

        adapter.notifyDataSetChanged()
    }
}
