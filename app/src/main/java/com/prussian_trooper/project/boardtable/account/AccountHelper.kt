package com.prussian_trooper.project.boardtable.account

import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.prussian_trooper.project.boardtable.MainActivity
import com.prussian_trooper.project.boardtable.R


class AccountHelper(act: MainActivity) {
    private val act = act
//проверка
    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.myAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    //зачем task. По нему мы хотим узнать
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        act.uiUpdate(task.result?.user)
                    } else {
                        Toast.makeText(
                            act,
                            act.resources.getString(R.string.sign_up_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
            //  addOnCompleteListener() - слушатель, который объявляет об успешной регистрации либо о неудачной попытке*/
        }
    }

    fun signInWithEmail(email: String, password: String) {//функция входа в аккаунтт
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                //зачем task. По нему мы хотим узнать
                if (task.isSuccessful) {
                    act.uiUpdate(task.result?.user)
                } else {
                    Toast.makeText(
                        act,
                        act.resources.getString(R.string.sign_in_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
    }

    //отправка письма для финальной регистрации
    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    act,
                    act.resources.getString(R.string.send_verification_email_done),
                    Toast.LENGTH_SHORT
                ).show()
//Временный тост, потом поставить смс ""на вашу почту была отправленна ссылка с подтверждением
            } else {
                Toast.makeText(
                    act,
                    act.resources.getString(R.string.send_verification_email_error),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}
