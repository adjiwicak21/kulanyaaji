package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reservasi_transaksi.*
import kotlinx.android.synthetic.main.activity_reservasi_transfer.*

class ReservasiTransferActivity : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservasi_transfer)
        val idPemesanan = intent.getStringExtra("idPemesanan")

        btn_selanjutnya.setOnClickListener {
            addPemesanan("Transfer", idPemesanan.toString())

            val intent = Intent(
                this@ReservasiTransferActivity,
                ReservasiStrukActivity::class.java
            )

            intent.putExtra("idPemesanan",idPemesanan)
            startActivity(intent)

        }
    }

    private fun addPemesanan(metodePemesanan: String, idPemesanan: String) {

        val addMetode = mapOf(
            "metodePembayaran" to metodePemesanan
        )

        db.collection("pemesanan").document(idPemesanan)
            .update(addMetode)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()
                Log.d("ReservasiTransaksi", "$addMetode")
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}