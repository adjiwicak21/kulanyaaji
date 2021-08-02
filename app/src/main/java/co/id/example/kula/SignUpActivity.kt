package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    companion object {
        val TAG = SignUpActivity::class.simpleName
    }

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        auth = Firebase.auth



        btn_masuk.setOnClickListener {

            val namaLengkap = daftar_nama.text.toString()
            val email = daftar_email.text.toString()
            val password = daftar_password.text.toString()

            Log.d(TAG, "$email $password $namaLengkap")
            btn_masuk.isActivated = false
            btn_masuk.text = getString(R.string.harapTunggu)
            if (namaLengkap.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        auth.currentUser?.uid?.let {
                            addDataUser(email, password, namaLengkap, it)
                        }
                        // Sign in success, update UI with the signed-in user's information

                        Toast.makeText(applicationContext, "Daftar Berhasil", Toast.LENGTH_SHORT)
                            .show()
                        btn_masuk.isActivated = true
                        btn_masuk.text = getString(R.string.sign_up)
                    }
                    .addOnFailureListener {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", it)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
            }

        }
        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@SignUpActivity,
                SignInActivity::class.java
            )
            startActivity(intent)
        }
    }


    private fun addDataUser(email: String, password: String, namaLengkap: String, auth: String) {
        val user = hashMapOf(
            "email" to email,
            "namaLengkap" to namaLengkap,
            "password" to password
        )

        db.collection("user").document(auth)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Silahkan Kembali ke Login", Toast.LENGTH_SHORT)
                    .show()
                Log.d(TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
            }
    }

}