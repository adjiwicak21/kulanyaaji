package co.id.example.kula

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.id.example.kula.data.Pemesanan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_data.*
import kotlinx.android.synthetic.main.activity_home_data.btn_back
import kotlinx.android.synthetic.main.activity_home_reservasi.*
import kotlinx.android.synthetic.main.activity_sign_up.*

@Suppress("UNREACHABLE_CODE")
class HomeReservasiActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    companion object {
        val TAG = HomeReservasiActivity::class.simpleName
    }

    private lateinit var layanan:String

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore

    var lastIdPemesanan = ""
    var idPemesanan = "P001"
//    val courses = getResources().getStringArray(R.array.layanan);
    val layanans = arrayOf(
    "Pernikahan", "Acara", "Sesi Foto", "Produk"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_reservasi)

        auth = Firebase.auth



        db.collection("pemesanan").addSnapshotListener { value, error ->
           value?.documents?.map {
               lastIdPemesanan =   it.id
            }
            Log.d("LAST ID PEMESANAN" ,lastIdPemesanan)
            if (lastIdPemesanan.isEmpty()){
                idPemesanan
            }else{
                val idTerm =  lastIdPemesanan.substring(1).toInt() + 1
                idPemesanan = "P" + ( idTerm.toString().padStart(3, '0'))
                Log.d("ID PEMESANAN" ,idPemesanan)
            }
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.layanan,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_layanan.adapter = adapter
        }
        btn_back.setOnClickListener {
            val intent = Intent(
                this@HomeReservasiActivity,
                HomeActivity::class.java
            )
            startActivity(intent)
        }

        spinner_layanan.setOnItemSelectedListener(this);
        layanan = layanans[1]

        btn_nxt.setOnClickListener {
            val idUser = auth.currentUser?.uid.toString()
            val namaLengkap = nama_lengkap.text.toString()
            val emails = email.text.toString()
            val noTlp = no_tlp.text.toString()
            val alamats = alamat.text.toString()
            val tglPesan = tgl_pesan.text.toString()
            val harga = setHarga(layanan)
            val tglPelaksanaan = tgl_pelaksanaan.text.toString()

            Log.d(
                HomeReservasiActivity.TAG,
                "$namaLengkap $emails $layanan $noTlp $tglPesan $tglPelaksanaan"
            )
            btn_nxt.isActivated = false
            btn_nxt.text = getString(R.string.harapTunggu)
            if (namaLengkap.isEmpty() || emails.isEmpty() || noTlp.isEmpty()  || tglPesan.isEmpty() || tglPelaksanaan.isEmpty()) {
                Toast.makeText(applicationContext, "Isi data dengan benar", Toast.LENGTH_SHORT)
                    .show()
            } else {
            addPemesanan(idUser,emails,namaLengkap,alamats,noTlp,layanan,tglPesan,tglPelaksanaan,harga)
                Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(
                    this@HomeReservasiActivity,
                    ReservasiTransaksiActivity::class.java
                )
                intent.putExtra("layanan", layanan)
                intent.putExtra("idPemesanan",idPemesanan)
                startActivity(intent)
            }

        }
    }


    private fun setHarga(layanan: String): String {
        var s = "900000"
        when(layanan) {
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
          layanan = layanans[p2]
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


    private fun addPemesanan(idUser: String,
                            email: String,
                            namaLengkap: String,
                            alamat : String,
                            noTlp : String,
                            layanan : String,
                            tglPesan : String,
                            tglPelaksanaan : String,harga: String)
    {




        val pemesanan = Pemesanan(
            idUser,
            idPemesanan,
            namaLengkap,
            email,
            alamat,
            noTlp,
            layanan,
            tglPesan,
            tglPelaksanaan,
            harga
        )
        Log.d("ID PEMESANAN", idPemesanan)

        db.collection("pemesanan").document(idPemesanan)
            .set(pemesanan)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_SHORT)
                    .show()
                Log.d("ReservasiTransaksi", "$pemesanan")
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
            }
    }

}
