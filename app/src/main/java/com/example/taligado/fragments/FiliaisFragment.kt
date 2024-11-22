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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class FiliaisFragment : Fragment(R.layout.fragment_filiais), OnMapReadyCallback {

    private lateinit var recyclerFiliais: RecyclerView
    private lateinit var btnAddFilial: Button
    private lateinit var filialAdapter: FilialAdapter
    private lateinit var filialViewModel: FilialViewModel
    private lateinit var googleMap: GoogleMap
    private var filiais: List<Filial> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_filiais, container, false)

        filialViewModel = ViewModelProvider(this)[FilialViewModel::class.java]

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

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        addFixedMarkers()
    }

    private fun addFixedMarkers() {
        val marker1 = LatLng(-23.550520, -46.633308) // São Paulo
        val marker2 = LatLng(-22.906847, -43.172896) // Rio de Janeiro
        val marker3 = LatLng(-19.916681, -43.934493) // Belo Horizonte

        googleMap.addMarker(MarkerOptions().position(marker1).title("São Paulo"))
        googleMap.addMarker(MarkerOptions().position(marker2).title("Rio de Janeiro"))
        googleMap.addMarker(MarkerOptions().position(marker3).title("Belo Horizonte"))

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker1, 5f))
    }
}
