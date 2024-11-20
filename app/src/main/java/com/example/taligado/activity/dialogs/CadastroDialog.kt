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
import com.example.taligado.activity.CadastroActivity

class CadastroDialog (private val context: Context) {

    fun show() {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_cadastro)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            (context.resources.displayMetrics.heightPixels * 0.7).toInt()
        )

        dialog.window?.setGravity(Gravity.BOTTOM)

        val slideIn = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom)
        dialog.window?.decorView?.startAnimation(slideIn)


        val btnRetornando = dialog.findViewById<Button>(R.id.btnRetornando)
        val txtCadastroEmail = dialog.findViewById<TextView>(R.id.txtCadastroEmail)

        btnRetornando.setOnClickListener {
            LoginDialog(context).show()
            dialog.dismiss()
        }

        txtCadastroEmail.setOnClickListener {
            val intent = Intent(context, CadastroActivity::class.java)
            context.startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }
}

