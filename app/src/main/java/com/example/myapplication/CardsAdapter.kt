package com.example.myapplication

import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View

class CardsAdapter(
    private val onItemClick: () -> Unit
) : RecyclerView.Adapter<CardsAdapter.ViewHolder>() {

    private val items = mutableListOf<Int>()

    fun updateItems(newItems: List<Int>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        holder.itemView.post {
            val width = holder.itemView.width
            if (width > 0) {
                val layoutParams = holder.itemView.layoutParams
                layoutParams.height = width
                holder.itemView.layoutParams = layoutParams
            }
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(
        itemView: View,
        private val onItemClick: () -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val cardView: CardView = itemView.findViewById(R.id.cardView)
        private val numberText: TextView = itemView.findViewById(R.id.numberText)

        init {
            itemView.setOnClickListener {
                onItemClick()
            }
        }

        fun bind(number: Int) {
            numberText.text = number.toString()
            numberText.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))

            val colorRes = if (number % 2 == 0) {
                R.color.color_even
            } else {
                R.color.color_odd
            }

            cardView.setCardBackgroundColor(
                ContextCompat.getColor(itemView.context, colorRes)
            )
        }
    }
}