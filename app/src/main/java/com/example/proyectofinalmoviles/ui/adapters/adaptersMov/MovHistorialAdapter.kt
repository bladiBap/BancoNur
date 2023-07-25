package com.example.proyectofinalmoviles.ui.adapters.adaptersMov

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalmoviles.R
import com.example.proyectofinalmoviles.models.Cuenta

class MovHistorialAdapter (
    private val listaMovimientos : MutableList<Cuenta>
): RecyclerView.Adapter<MovHistorialAdapter.MovHistorialViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovHistorialViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_list_historial, parent, false)
        return MovHistorialViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaMovimientos.size
    }

    override fun onBindViewHolder(holder: MovHistorialViewHolder, position: Int) {
        val movimiento = listaMovimientos[position]
        if (movimiento.tipo == -1){
            holder.imagenTipo.setImageResource(R.drawable.egreso)
            holder.tipo.text = "Egreso"
            holder.monto.text = "${movimiento.monto} Bs."
            holder.descripcion.text = movimiento.descripcion
        }else{
            holder.imagenTipo.setImageResource(R.drawable.ingreso)
            holder.tipo.text = "Ingreso"
            holder.monto.text = "+${movimiento.monto} Bs."
            holder.descripcion.text = movimiento.descripcion
        }
    }

    class MovHistorialViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView ) {
        val imagenTipo = itemView.findViewById<ImageView>(R.id.imageTipo)
        val tipo = itemView.findViewById<TextView>(R.id.textTipo)
        val monto = itemView.findViewById<TextView>(R.id.textMonto)
        val descripcion = itemView.findViewById<TextView>(R.id.textDescripcion)
    }

}