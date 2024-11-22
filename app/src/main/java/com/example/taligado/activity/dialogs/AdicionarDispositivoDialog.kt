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
import com.example.taligado.model.Dispositivo
import com.example.taligado.viewModel.DispositivosViewModel

class AdicionarDispositivoDialog(private val onDispositivoAdicionada: (Dispositivo) -> Unit) : DialogFragment() {

    private lateinit var edtNome: EditText
    private lateinit var edtStatus: EditText
    private lateinit var edtConsumoEnergia: EditText
    private lateinit var btnEscolherImagem: Button
    private lateinit var btnSalvar: Button
    private var selectedImageUri: Uri? = null

    private lateinit var dispositivosViewModel: DispositivosViewModel

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
        val view = inflater.inflate(R.layout.dialog_add_dispositivo, container, false)

        dispositivosViewModel = ViewModelProvider(requireActivity()).get(DispositivosViewModel::class.java)

        edtNome = view.findViewById(R.id.edtNomeDispositivo)
        edtStatus = view.findViewById(R.id.edtStatusDispositivo)
        edtConsumoEnergia= view.findViewById(R.id.edtConsumoEnergiaDispositivo)
        btnEscolherImagem = view.findViewById(R.id.btnEscolherImagem)
        btnSalvar = view.findViewById(R.id.btnSalvar)

        btnEscolherImagem.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        btnSalvar.setOnClickListener {
            val nomeDispositivo = edtNome.text.toString().trim()
            val statusDispositivo = edtStatus.text.toString().trim()
            val consumoEnergiaDispositivo = edtConsumoEnergia.text.toString().trim()


            if (nomeDispositivo.isNotEmpty() && statusDispositivo.isNotEmpty() && consumoEnergiaDispositivo.isNotEmpty() && selectedImageUri != null) {

                val dispositivo = Dispositivo(
                    nome = nomeDispositivo,
                    imagemUrl = selectedImageUri.toString(),
                    status = statusDispositivo,
                    consumoEnergia = consumoEnergiaDispositivo
                )

                btnSalvar.isEnabled = false

                dispositivosViewModel.adicionarDispositivo(dispositivo) { sucesso ->
                    if (sucesso) {
                        onDispositivoAdicionada(dispositivo)
                        dismiss()
                        Toast.makeText(context, "Dispositivo adicionada com sucesso!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Erro ao adicionar dispositivo", Toast.LENGTH_SHORT).show()
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
