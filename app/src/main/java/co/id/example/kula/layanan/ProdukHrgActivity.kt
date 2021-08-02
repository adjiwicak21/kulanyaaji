package co.id.example.kula.layanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.example.kula.HomeLayananActivity
import co.id.example.kula.R
import kotlinx.android.synthetic.main.activity_home_layanan.*
import kotlinx.android.synthetic.main.activity_wedding_ft.*
import kotlinx.android.synthetic.main.activity_wedding_ft.btn_back

class ProdukHrgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_hrg)

        btn_foto.setOnClickListener {
            val intent = Intent(this@ProdukHrgActivity,
                ProdukFtActivity::class.java)
            startActivity(intent)
        }

        btn_video.setOnClickListener {
            val intent = Intent(this@ProdukHrgActivity,
                ProdukVdActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@ProdukHrgActivity,
                HomeLayananActivity::class.java
            )
            startActivity(intent)
        }

    }
}