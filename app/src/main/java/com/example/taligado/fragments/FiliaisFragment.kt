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
import com.example.taligado.activity.dialogs.AddFilialDialogFragment
import com.example.taligado.adapters.FilialAdapter
import com.example.taligado.model.Filial
import com.example.taligado.viewModel.FilialViewModel

class FiliaisFragment : Fragment(R.layout.fragment_filiais) {

    private lateinit var recyclerFiliais: RecyclerView
    private lateinit var btnAddFilial: Button
    private lateinit var filialAdapter: FilialAdapter
    private lateinit var filialViewModel: FilialViewModel
    private var filiais: List<Filial> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filiais, container, false)

        filialViewModel = ViewModelProvider(this).get(FilialViewModel::class.java)

        recyclerFiliais = view.findViewById(R.id.recyclerFiliais)
        recyclerFiliais.layoutManager = LinearLayoutManager(requireContext())

        filialViewModel.listarFiliais { filiaisList ->
            filiais = filiaisList
            filialAdapter = FilialAdapter(filiais)
            recyclerFiliais.adapter = filialAdapter
        }

        btnAddFilial = view.findViewById(R.id.btnAddFilial)
        btnAddFilial.setOnClickListener {

            AddFilialDialogFragment { novaFilial ->
                filialViewModel.adicionarFilial(novaFilial) { sucesso ->
                    if (sucesso) {
                        filialViewModel.listarFiliais { filiaisList ->
                            filialAdapter.updateList(filiaisList)
                        }
                    }
                }
            }.show(parentFragmentManager, "AddFilialDialog")
        }

        return view
    }
}
