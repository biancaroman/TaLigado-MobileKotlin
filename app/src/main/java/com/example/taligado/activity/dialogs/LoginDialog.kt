package com.example.taligado.activity.dialogs

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.example.taligado.R
import com.example.taligado.activity.LoginActivity

class LoginDialog(private val context: Context) {

    fun show() {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_login)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (context.resources.displayMetrics.heightPixels * 0.7).toInt()
        )

        dialog.window?.setGravity(Gravity.BOTTOM)

        val slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom)
        dialog.window?.decorView?.startAnimation(slideIn)


        val btnNovo = dialog.findViewById<Button>(R.id.btnNovo)
        val txtLoginEmail = dialog.findViewById<TextView>(R.id.txtLoginEmail)
        // Evento do botão "Novo" (vai para a página de cadastro)
        btnNovo.setOnClickListener {
            CadastroDialog(context).show() // Abre o novo diálogo de cadastro
            dialog.dismiss()
        }

        // Evento do link "Entrar com e-mail" (vai para a página de login por e-mail)
        txtLoginEmail.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }
}

