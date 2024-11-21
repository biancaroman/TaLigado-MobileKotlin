package com.example.taligado.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taligado.R
import com.example.taligado.model.Filial

class FilialAdapter(private var filiais: List<Filial>) :
    RecyclerView.Adapter<FilialAdapter.FilialViewHolder>() {

    // Atualizar a lista de filiais no adapter
    fun updateList(novasFiliais: List<Filial>) {
        filiais = novasFiliais
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_filial, parent, false)
        return FilialViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilialViewHolder, position: Int) {
        val filial = filiais[position]
        holder.nomeTextView.text = filial.nome

        Glide.with(holder.imagemImageView.context)
            .load(filial.imagemUrl)
            .into(holder.imagemImageView)
    }

    override fun getItemCount(): Int = filiais.size

    inner class FilialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeTextView: TextView = view.findViewById(R.id.txtNomeFilial)
        val imagemImageView: ImageView = view.findViewById(R.id.imgFilial)
    }
}
