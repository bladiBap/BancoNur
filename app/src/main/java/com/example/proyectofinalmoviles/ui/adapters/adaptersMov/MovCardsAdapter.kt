package com.example.proyectofinalmoviles.ui.adapters.adaptersMov

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.models.Card

class MovCardsAdapter (
    private val listaTrajetas : MutableList<Card>,
    private val listener: OnClickListenerCard
        ): RecyclerView.Adapter<MovCardsAdapter.MovCardsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovCardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list_card, parent, false)
        return MovCardsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaTrajetas.size
    }

    override fun onBindViewHolder(holder: MovCardsViewHolder, position: Int) {
        val card = listaTrajetas[position]
        holder.nroTarjeta.text = card.numero
        holder.saldo.text = card.saldo
        holder.idCard.text = card.id.toString()
        holder.itemView.setOnClickListener {
            listener.onClickCard(card.id)
        }
    }

    class MovCardsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView ) {
        val nroTarjeta = itemView.findViewById<TextView>(R.id.nroCuenta)
        val saldo = itemView.findViewById<TextView>(R.id.saldo)
        val idCard = itemView.findViewById<TextView>(R.id.idCard)
    }

    interface OnClickListenerCard {
        fun onClickCard(id: Int)
    }

}