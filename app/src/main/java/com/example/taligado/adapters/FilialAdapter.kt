package com.example.taligado.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taligado.R
import com.example.taligado.activity.dialogs.EditFilialDialogFragment
import com.example.taligado.model.Filial
import com.example.taligado.viewModel.FilialViewModel

class FilialAdapter(private var filiais: List<Filial>) :
    RecyclerView.Adapter<FilialAdapter.FilialViewHolder>() {

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
        Log.d("FilialAdapter", "Binding view para a filial: ${filial.nome}")

        holder.nomeTextView.text = filial.nome

        Glide.with(holder.imagemImageView.context)
            .load(filial.imagemUrl)
            .into(holder.imagemImageView)

        holder.itemView.findViewById<Button>(R.id.btnMaisInformacoes).setOnClickListener {
            Log.d("FilialAdapter", "Botão de mais informações clicado para a filial: ${filial.nome}")
            mostrarInformacoesFilial(filial, holder.itemView.context)
        }
    }

    override fun getItemCount(): Int = filiais.size

    private fun mostrarInformacoesFilial(filial: Filial, context: Context) {
        Log.d("FilialAdapter", "Exibindo informações da filial: ${filial.nome}")

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_filial, null)
        Log.d("FilialAdapter", "Dialog inflado com sucesso.")

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        val imgFilialDialog = dialogView.findViewById<ImageView>(R.id.dialogImgFilial)
        val nomeFilialDialog = dialogView.findViewById<TextView>(R.id.dialogNomeFilial)
        val enderecoFilialDialog = dialogView.findViewById<TextView>(R.id.dialogEnderecoFilial)
        val descricaoFilialDialog = dialogView.findViewById<TextView>(R.id.dialogDescricaoFilial)
        val btnRemoverFilial = dialogView.findViewById<Button>(R.id.btnRemoverFilial)
        val btnVerDetalhes = dialogView.findViewById<Button>(R.id.btnVerDetalhesFilial)
        val iconEditarFilial = dialogView.findViewById<ImageView>(R.id.iconEditarFilial)

        Log.d("FilialAdapter", "Carregando dados da filial na dialog.")
        Glide.with(context).load(filial.imagemUrl).into(imgFilialDialog)
        nomeFilialDialog.text = filial.nome
        enderecoFilialDialog.text = filial.endereco
        descricaoFilialDialog.text = filial.descricao

        // Ação para remover a filial
        btnRemoverFilial.setOnClickListener {
            Log.d("FilialAdapter", "Remover filial: ${filial.id}")
            val filialViewModel = FilialViewModel()
            filialViewModel.deletarFilial(filial.id.toString()) { sucesso ->
                if (sucesso) {
                    Log.d("FilialAdapter", "Filial removida com sucesso")
                    Toast.makeText(context, "Filial removida com sucesso", Toast.LENGTH_SHORT).show()
                    // Atualizar a lista no adapter
                    val index = filiais.indexOf(filial)
                    filiais = filiais.filterIndexed { idx, _ -> idx != index }
                    notifyItemRemoved(index)
                } else {
                    Log.e("FilialAdapter", "Falha ao remover filial")
                    Toast.makeText(context, "Falha ao remover filial", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
        }

        // Ação para ver detalhes
        btnVerDetalhes.setOnClickListener {
            Log.d("FilialAdapter", "Ver mais detalhes da filial: ${filial.id}")
            dialog.dismiss()
        }

        // Ação para editar a filial
        iconEditarFilial.setOnClickListener {
            Log.d("FilialAdapter", "Editar filial: ${filial.id}")
            val dialogFragment = EditFilialDialogFragment(filial) {
                updateList(filiais)
            }
            dialogFragment.show(
                (context as AppCompatActivity).supportFragmentManager,
                "editFilialDialog"
            )
        }

        dialog.show()
    }

    inner class FilialViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeTextView: TextView = view.findViewById(R.id.txtNomeFilial)
        val imagemImageView: ImageView = view.findViewById(R.id.imgFilial)
    }
}
