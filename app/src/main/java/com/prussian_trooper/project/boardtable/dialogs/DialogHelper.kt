package com.prussian_trooper.project.boardtable.dialogs

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.prussian_trooper.project.boardtable.MainActivity
import com.prussian_trooper.project.boardtable.R
import com.prussian_trooper.project.boardtable.account.AccountHelper


class DialogHelper(act: MainActivity) {
    private val activity = act
    private val accHelper = AccountHelper(act)//объект, с помощью которго мы можем регистрироваться

    fun createSignDialog(index: Int) {
        val builder = AlertDialog.Builder(activity)
        val rootDialogElement = SignDialogBinding.inflate(activity.layoutInflater)
        val view = rootDialogElement.root //rootDialogElement превращается во view(которая стоит после val)
        builder.setView(view)
        setDialogState(index,rootDialogElement)

        val dialog = builder.create()
        /*прослушивание нажатия кнопки*/
        rootDialogElement.btnSignUpIn.setOnClickListener{
            setOnClickSignUpIn(index, rootDialogElement, dialog)
        }
        rootDialogElement.btnForgetP.setOnClickListener{
            setOnClickResetPassword(rootDialogElement, dialog)
        }
        dialog.show()
    }
    //rootDialogElement - прямой доступ к элементам в sign_dialog
    private fun setOnClickResetPassword(rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
        if (rootDialogElement.edSignEmail.text.isNotEmpty()) {
            activity.myAuth.sendPasswordResetEmail(rootDialogElement.edSignEmail.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, R.string.email_reset_password_was_send, Toast.LENGTH_LONG).show()
                }
            }
            dialog?.dismiss()
        } else {
            rootDialogElement.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(index: Int, rootDialogElement: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
        if (index == DialogConst.SIGN_UP_STATE){

            accHelper.signUpWithEmail(rootDialogElement.edSignEmail.text.toString(),//Регистрация аккаунта
                rootDialogElement.edSignPassword.text.toString())

        } else {
            accHelper.signInWithEmail(rootDialogElement.edSignEmail.text.toString(),//Вход по паролю
                rootDialogElement.edSignPassword.text.toString())
        }
    }

    private fun setDialogState(index: Int, rootDialogElement: SignDialogBinding) {
        /* Изменяем текст в sign_dialog */
        if (index == DialogConst.SIGN_UP_STATE) {
            rootDialogElement.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_up)
            rootDialogElement.btnSignUpIn.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            rootDialogElement.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btnSignUpIn.text = activity.resources.getString(R.string.sign_in_action)
            rootDialogElement.btnForgetP.visibility = View.VISIBLE
        }
    }
}