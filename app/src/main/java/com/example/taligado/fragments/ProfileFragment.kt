package com.example.taligado.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taligado.R
import com.example.taligado.activity.HomeActivity
import com.example.taligado.viewModel.UsuarioViewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val usuarioViewModel = UsuarioViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logoutButton = view.findViewById<View>(R.id.ic_encerrarSessao)
        logoutButton.setOnClickListener {
            realizarLogout(requireContext())
        }
    }

    private fun realizarLogout(context: Context) {
        usuarioViewModel.logout(context)
        Toast.makeText(context, "Sessão encerrada com sucesso!", Toast.LENGTH_SHORT).show()

        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
