package com.example.taligado.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taligado.R

class DispositivosFragment : Fragment(R.layout.fragment_dispositivos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_devices)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Lista de dispositivos com imagens diferentes
        val devices = listOf(
            Device("Geladeira", "Ativo", "25 kWh", R.drawable.device_image1),
            Device("Televisão", "Desligado", "15 kWh", R.drawable.device_image2),
            Device("Ar Condicionado", "Ativo", "40 kWh", R.drawable.device_image3)
        )

        val adapter = DevicesAdapter(devices)
        recyclerView.adapter = adapter

        val addButton: Button = view.findViewById(R.id.add_device_button)
        addButton.setOnClickListener {
            // Lógica para adicionar novo dispositivo
        }
    }

    data class Device(val name: String, val status: String, val energyConsumption: String, val imageResourceId: Int)

    class DevicesAdapter(private val devices: List<Device>) : RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
            return DeviceViewHolder(view)
        }

        override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
            val device = devices[position]
            holder.bind(device)
        }

        override fun getItemCount(): Int = devices.size

        class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val deviceImage = itemView.findViewById<ImageView>(R.id.device_image)
            private val deviceName = itemView.findViewById<TextView>(R.id.device_name)
            private val deviceStatus = itemView.findViewById<TextView>(R.id.device_status)
            private val deviceEnergyConsumption = itemView.findViewById<TextView>(R.id.device_energy_consumption)

            fun bind(device: Device) {
                deviceImage.setImageResource(device.imageResourceId)
                deviceName.text = device.name
                deviceStatus.text = device.status
                deviceEnergyConsumption.text = device.energyConsumption
            }
        }
    }
}
