package com.example.taligado.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taligado.R
import com.example.taligado.activity.dialogs.AdicionarDispositivoDialog
import com.example.taligado.adapters.DispositivosAdapter
import com.example.taligado.model.Dispositivo
import com.example.taligado.viewModel.DispositivosViewModel


class DispositivosFragment : Fragment(R.layout.fragment_dispositivos) {

    private lateinit var recyclerDispositivo: RecyclerView
    private lateinit var btnAddDispositivo: Button
    private lateinit var dispositivoAdapter: DispositivosAdapter
    private lateinit var dispositivoViewModel: DispositivosViewModel
    private var dispositivos: List<Dispositivo> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dispositivos, container, false)

        dispositivoViewModel = ViewModelProvider(this).get(DispositivosViewModel::class.java)

        recyclerDispositivo = view.findViewById(R.id.recyclerDispositivos)
        recyclerDispositivo.layoutManager = LinearLayoutManager(requireContext())

        dispositivoViewModel.listarDispositivos { dispositivosList ->
            dispositivos = dispositivosList
            dispositivoAdapter = DispositivosAdapter(dispositivos)
            recyclerDispositivo.adapter = dispositivoAdapter
        }

        btnAddDispositivo = view.findViewById(R.id.btnAddDispositivo)
        btnAddDispositivo.setOnClickListener {

            AdicionarDispositivoDialog { novaDispositivo ->
                dispositivoViewModel.adicionarDispositivo(novaDispositivo) { sucesso ->
                    if (sucesso) {
                        dispositivoViewModel.listarDispositivos { dispositivosList ->
                            dispositivoAdapter.updateList(dispositivosList)
                        }
                    }
                }
            }.show(parentFragmentManager, "AddDispositivoDialog")
        }

        return view
    }
}


