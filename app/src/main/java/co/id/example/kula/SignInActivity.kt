package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_onboarding_three.*
import kotlinx.android.synthetic.main.activity_onboarding_two.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.btn_masuk
import kotlinx.android.synthetic.main.activity_sign_in.daftar_nama
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        auth = FirebaseAuth.getInstance()

            btn_masuk.setOnClickListener() {
                if (daftar_nama.text.toString().trim().isEmpty()
                    || masuk_password.text.toString().trim().isEmpty()){
                    Toast.makeText(
                        baseContext, "isi email dan password dengan benar",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    auth.signInWithEmailAndPassword(daftar_nama.text.toString().trim(), masuk_password.text.toString().trim())
                        .addOnSuccessListener {
                            // Sign in success, update UI with the signed-in user's information
                            val intent = Intent(
                                this,
                                HomeActivity::class.java
                            )
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            // If sign in fails, display a message to the user.
                            Log.w(SignUpActivity.TAG, "createUserWithEmail:failure", it)
                            Toast.makeText(
                                baseContext, "Authentication failed $it.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                }


            }
            link_daftar.setOnClickListener {
                finishAffinity()

                val intent = Intent(
                    this@SignInActivity,
                    SignUpActivity::class.java
                )
                startActivity(intent)
            }

            // intent to home

        }


    }
