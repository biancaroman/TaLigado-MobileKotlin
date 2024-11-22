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
import com.example.taligado.activity.dialogs.EditDispositivoDialog
import com.example.taligado.model.Dispositivo
import com.example.taligado.viewModel.DispositivosViewModel


class DispositivosAdapter(private var dispositivos: List<Dispositivo>) :
    RecyclerView.Adapter<DispositivosAdapter.DispositivoViewHolder>() {

    fun updateList(novasDispositivos: List<Dispositivo>) {
        dispositivos = novasDispositivos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DispositivoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dispositivo, parent, false)
        return DispositivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DispositivoViewHolder, position: Int) {
        val dispositivo = dispositivos[position]
        Log.d("DispositivoAdapter", "Binding view para a dispositivo: ${dispositivo.nome}")

        holder.nomeTextView.text = dispositivo.nome

        Glide.with(holder.imagemImageView.context)
            .load(dispositivo.imagemUrl)
            .into(holder.imagemImageView)

        holder.itemView.findViewById<Button>(R.id.btnMaisInformacoes).setOnClickListener {
            Log.d("DispositivoAdapter", "Botão de mais informações clicado para a dispositivo: ${dispositivo.nome}")
            mostrarInformacoesDispositivo(dispositivo, holder.itemView.context)
        }
    }

    override fun getItemCount(): Int = dispositivos.size

    private fun mostrarInformacoesDispositivo(dispositivo: Dispositivo, context: Context) {
        Log.d("DispositivoAdapter", "Exibindo informações da dispositivo: ${dispositivo.nome}")

        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_dispositivo, null)
        Log.d("DispositivoAdapter", "Dialog inflado com sucesso.")

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()

        val imgDispositivoDialog = dialogView.findViewById<ImageView>(R.id.dialogImgDispositivo)
        val nomeDispositivoDialog = dialogView.findViewById<TextView>(R.id.dialogNomeDispositivo)
        val statusDispositivoDialog = dialogView.findViewById<TextView>(R.id.dialogStatusDispositivo)
        val consumoEnergiaDispositivoDialog = dialogView.findViewById<TextView>(R.id.dialogConsumoEnergiaDispositivo)
        val btnRemoverDispositivo = dialogView.findViewById<Button>(R.id.btnRemoverDispositivo)
        val btnVerDetalhes = dialogView.findViewById<Button>(R.id.btnVerDetalhesDispositivo)
        val iconEditarDispositivo = dialogView.findViewById<ImageView>(R.id.iconEditarDispositivo)

        Log.d("DispositivoAdapter", "Carregando dados da dispositivo na dialog.")
        Glide.with(context).load(dispositivo.imagemUrl).into(imgDispositivoDialog)
        nomeDispositivoDialog.text = dispositivo.nome
        statusDispositivoDialog.text = dispositivo.status
        consumoEnergiaDispositivoDialog.text = dispositivo.consumoEnergia


        btnRemoverDispositivo.setOnClickListener {
            Log.d("DispositivoAdapter", "Remover dispositivo: ${dispositivo.id}")
            val dispositivoViewModel = DispositivosViewModel()
            dispositivoViewModel.deletarDispositivo(dispositivo.id.toString()) { sucesso ->

                if (sucesso) {
                    Log.d("DispositivoAdapter", "Dispositivo removida com sucesso")
                    Toast.makeText(context, "Dispositivo removida com sucesso", Toast.LENGTH_SHORT).show()

                    val index = dispositivos.indexOf(dispositivo)
                    dispositivos = dispositivos.filterIndexed { idx, _ -> idx != index }
                    notifyItemRemoved(index)
                } else {
                    Log.e("DispositivoAdapter", "Falha ao remover dispositivo")
                    Toast.makeText(context, "Falha ao remover dispositivo", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
        }

        // Ação para ver detalhes
        btnVerDetalhes.setOnClickListener {
            Log.d("DispositivoAdapter", "Ver mais detalhes da dispositivo: ${dispositivo.id}")
            dialog.dismiss()
        }

        // Ação para editar a dispositivo
        iconEditarDispositivo.setOnClickListener {
            Log.d("DispositivoAdapter", "Editar dispositivo: ${dispositivo.id}")
            val dialogFragment = EditDispositivoDialog(dispositivo) {
                updateList(dispositivos)
            }
            dialogFragment.show(
                (context as AppCompatActivity).supportFragmentManager,
                "editDispositivoDialog"
            )
        }

        dialog.show()
    }

    inner class DispositivoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeTextView: TextView = view.findViewById(R.id.txtNomeDispositivo)
        val imagemImageView: ImageView = view.findViewById(R.id.imgDispositivo)
    }
}
