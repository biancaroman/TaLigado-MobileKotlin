package com.example.taligado.activity.dialogs

import android.net.Uri
import android.os.Bundle
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
import com.example.taligado.viewModel.FilialViewModel

class AddFilialDialogFragment(private val onFilialAdicionada: (Filial) -> Unit) : DialogFragment() {

    private lateinit var edtNome: EditText
    private lateinit var edtEndereco: EditText
    private lateinit var edtDescricao: EditText
    private lateinit var btnEscolherImagem: Button
    private lateinit var btnSalvar: Button
    private var selectedImageUri: Uri? = null

    private lateinit var filialViewModel: FilialViewModel

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

        filialViewModel = ViewModelProvider(requireActivity()).get(FilialViewModel::class.java)

        edtNome = view.findViewById(R.id.edtNomeFilial)
        edtEndereco = view.findViewById(R.id.edtEnderecoFilial)
        edtDescricao = view.findViewById(R.id.edtDescricaoFilial)
        btnEscolherImagem = view.findViewById(R.id.btnEscolherImagem)
        btnSalvar = view.findViewById(R.id.btnSalvar)

        btnEscolherImagem.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnSalvar.setOnClickListener {
            val nomeFilial = edtNome.text.toString().trim()
            val enderecoFilial = edtEndereco.text.toString().trim()
            val descricaoFilial = edtDescricao.text.toString().trim()


            if (nomeFilial.isNotEmpty() && enderecoFilial.isNotEmpty() && descricaoFilial.isNotEmpty() && selectedImageUri != null) {

                val filial = Filial(
                    nome = nomeFilial,
                    imagemUrl = selectedImageUri.toString(),
                    endereco = enderecoFilial,
                    descricao = descricaoFilial
                )
                
                btnSalvar.isEnabled = false

                filialViewModel.adicionarFilial(filial) { sucesso ->
                    if (sucesso) {
                        onFilialAdicionada(filial)
                        dismiss()
                        Toast.makeText(context, "Filial adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Erro ao adicionar filial", Toast.LENGTH_SHORT).show()
                    }

                    btnSalvar.isEnabled = true
                }
            } else {
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
