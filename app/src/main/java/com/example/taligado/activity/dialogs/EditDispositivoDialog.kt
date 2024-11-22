package com.example.taligado.activity.dialogs

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.taligado.R
import com.example.taligado.model.Dispositivo
import com.example.taligado.viewModel.DispositivosViewModel

class EditDispositivoDialog(
    private val dispositivo: Dispositivo,
    private val onDispositivoUpdated: () -> Unit
) : DialogFragment() {

    private var imageUri: Uri? = null

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                val imageView: ImageView = dialog?.findViewById(R.id.imageViewDispositivo)!!
                imageView.setImageURI(it)
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())

        builder.setView(R.layout.dialog_edit_dispositivo)
            .setTitle("Editar Dispositivo")
            .setCancelable(false)

        val dialog = builder.create()

        dialog.setOnShowListener {

            val nomeEditText: EditText = dialog.findViewById(R.id.edtNomeDispositivo)!!
            val statusEditText: EditText = dialog.findViewById(R.id.edtStatusDispositivo)!!
            val consumoEnergiaEditText: EditText = dialog.findViewById(R.id.edtConsumoEnergiaDispositivo)!!
            val btnEscolherImagem: Button = dialog.findViewById(R.id.btnEscolherImagem)!!
            val btnSalvar: Button = dialog.findViewById(R.id.btnSalvar)!!
            val imageView: ImageView = dialog.findViewById(R.id.imageViewDispositivo)!!


            nomeEditText.setText(dispositivo.nome)
            statusEditText.setText(dispositivo.status)
            consumoEnergiaEditText.setText(dispositivo.consumoEnergia)


            Glide.with(requireContext()).load(dispositivo.imagemUrl).into(imageView)


            btnEscolherImagem.setOnClickListener {
                selectImageLauncher.launch("image/*")
            }

            btnSalvar.setOnClickListener {
                val nome = nomeEditText.text.toString()
                val status = statusEditText.text.toString()
                val consumoEnergia = consumoEnergiaEditText.text.toString()

                if (nome.isNotBlank() && status.isNotBlank() && consumoEnergia.isNotBlank()) {
                    val dispositivoViewModel = DispositivosViewModel()
                    val updatedDispositivo = dispositivo.copy(
                        nome = nome,
                        status = status,
                        consumoEnergia = consumoEnergia,
                        imagemUrl = imageUri?.toString() ?: ""

                    )

                    dispositivoViewModel.atualizarDispositivos(dispositivo.id.toString(), updatedDispositivo) { sucesso ->
                        if (sucesso) {
                            Toast.makeText(requireContext(), "Dispositivo atualizada com sucesso", Toast.LENGTH_SHORT).show()
                            onDispositivoUpdated()

                            requireActivity().supportFragmentManager.popBackStack()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Falha ao atualizar dispositivo", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
