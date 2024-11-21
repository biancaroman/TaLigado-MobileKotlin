package com.example.taligado.activity.dialogs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.taligado.R
import com.example.taligado.model.Filial
import com.example.taligado.viewmodel.FilialViewModel

class AddFilialDialogFragment(private val onFilialAdicionada: (Filial) -> Unit) : DialogFragment() {

    private lateinit var edtNome: EditText
    private lateinit var btnEscolherImagem: Button
    private lateinit var btnSalvar: Button
    private var selectedImageUri: Uri? = null

    private lateinit var filialViewModel: FilialViewModel

    // Novo ActivityResultLauncher para escolher imagem
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            selectedImageUri = uri
            Toast.makeText(context, "Imagem selecionada com sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_add_filial, container, false)

        // Obtendo o ViewModel com escopo de Activity
        filialViewModel = ViewModelProvider(requireActivity()).get(FilialViewModel::class.java)

        edtNome = view.findViewById(R.id.edtNomeFilial)
        btnEscolherImagem = view.findViewById(R.id.btnEscolherImagem)
        btnSalvar = view.findViewById(R.id.btnSalvar)

        // Abrir a galeria de imagens usando o launcher
        btnEscolherImagem.setOnClickListener {
            pickImageLauncher.launch("image/*") // Abre a galeria para selecionar imagens
        }

        // Ao clicar no botão de salvar, salva a filial usando o ViewModel
        btnSalvar.setOnClickListener {
            val nomeFilial = edtNome.text.toString()
            if (nomeFilial.isNotEmpty() && selectedImageUri != null) {
                // Criar uma nova filial com o nome e imagem
                val filial = Filial(nomeFilial, selectedImageUri.toString())
                filialViewModel.adicionarFilial(filial) { sucesso ->
                    if (sucesso) {
                        // Chama a função de callback para atualizar a lista
                        onFilialAdicionada(filial)
                        dismiss() // Fecha o Dialog
                        Toast.makeText(context, "Filial adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Erro ao adicionar filial", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
