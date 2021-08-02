package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import co.id.example.kula.data.Pemesanan
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_data.*
import kotlinx.android.synthetic.main.activity_reservasi_transaksi.*

class ReservasiTransaksiActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservasi_transaksi)

        val layanan = intent.getStringExtra("layanan")
        val idPemesanan = intent.getStringExtra("idPemesanan")

        nama_layanan.text = layanan
        harga_struk.text = layanan?.let { setHarga(it) }

        btn_tunai.setOnClickListener {
            addPemesanan("Tunai", idPemesanan.toString())
            val intent = Intent(
                this@ReservasiTransaksiActivity,
                ReservasiStrukActivity::class.java
            )
            intent.putExtra("idPemesanan", idPemesanan)
            startActivity(intent)
        }
        btn_transfer.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@ReservasiTransaksiActivity,
                ReservasiTransferActivity::class.java
            )
            intent.putExtra("idPemesanan", idPemesanan)
            startActivity(intent)

        }
    }

    private fun setHarga(layanan: String): String {
        var s = "900000"
        when (layanan) {
            "Pernikahan" -> {
                s = "4500000"
            }
            "Acara" -> {
                s = "5500000"
            }
            "Sesi Foto" -> {
                s = "2750000"
            }
            "Produk" -> {
                s = "1500000"
            }
        }
        return s
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
