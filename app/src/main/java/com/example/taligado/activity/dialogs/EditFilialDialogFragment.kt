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
import com.example.taligado.model.Filial
import com.example.taligado.viewModel.FilialViewModel

class EditFilialDialogFragment(
    private val filial: Filial,
    private val onFilialUpdated: () -> Unit
) : DialogFragment() {

    private var imageUri: Uri? = null

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                val imageView: ImageView = dialog?.findViewById(R.id.imageViewFilial)!!
                imageView.setImageURI(it)
            }
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())

        builder.setView(R.layout.dialog_edit_filial)
            .setTitle("Editar Filial")
            .setCancelable(false)

        val dialog = builder.create()

        dialog.setOnShowListener {

            val nomeEditText: EditText = dialog.findViewById(R.id.edtNomeFilial)!!
            val enderecoEditText: EditText = dialog.findViewById(R.id.edtEnderecoFilial)!!
            val descricaoEditText: EditText = dialog.findViewById(R.id.edtDescricaoFilial)!!
            val btnEscolherImagem: Button = dialog.findViewById(R.id.btnEscolherImagem)!!
            val btnSalvar: Button = dialog.findViewById(R.id.btnSalvar)!!
            val imageView: ImageView = dialog.findViewById(R.id.imageViewFilial)!!


            nomeEditText.setText(filial.nome)
            enderecoEditText.setText(filial.endereco)
            descricaoEditText.setText(filial.descricao)


            Glide.with(requireContext()).load(filial.imagemUrl).into(imageView)


            btnEscolherImagem.setOnClickListener {
                selectImageLauncher.launch("image/*")
            }

            btnSalvar.setOnClickListener {
                val nome = nomeEditText.text.toString()
                val endereco = enderecoEditText.text.toString()
                val descricao = descricaoEditText.text.toString()

                if (nome.isNotBlank() && endereco.isNotBlank() && descricao.isNotBlank()) {
                    val filialViewModel = FilialViewModel()
                    val updatedFilial = filial.copy(
                        nome = nome,
                        endereco = endereco,
                        descricao = descricao,
                        imagemUrl = imageUri?.toString() ?: ""

                    )

                    filialViewModel.atualizarFilial(filial.id.toString(), updatedFilial) { sucesso ->
                        if (sucesso) {
                            Toast.makeText(requireContext(), "Filial atualizada com sucesso", Toast.LENGTH_SHORT).show()
                            onFilialUpdated()

                            requireActivity().supportFragmentManager.popBackStack()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Falha ao atualizar filial", Toast.LENGTH_SHORT).show()
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
