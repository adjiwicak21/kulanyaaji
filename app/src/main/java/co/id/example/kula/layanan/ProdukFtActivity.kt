package co.id.example.kula.layanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.example.kula.HomeActivity
import co.id.example.kula.HomeLayananActivity
import co.id.example.kula.R
import kotlinx.android.synthetic.main.activity_home_data.*
import kotlinx.android.synthetic.main.activity_home_layanan.*
import kotlinx.android.synthetic.main.activity_home_layanan.btn_back
import kotlinx.android.synthetic.main.activity_produk_ft.*
import kotlinx.android.synthetic.main.activity_wedding_ft.*
import kotlinx.android.synthetic.main.activity_wedding_ft.btn_hrg
import kotlinx.android.synthetic.main.activity_wedding_ft.btn_video

class ProdukFtActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produk_ft)

        btn_video.setOnClickListener {
            val intent = Intent(this@ProdukFtActivity,
                ProdukVdActivity::class.java)
            startActivity(intent)
        }

        btn_hrg.setOnClickListener {
            val intent = Intent(this@ProdukFtActivity,
                ProdukHrgActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@ProdukFtActivity,
                HomeLayananActivity::class.java
            )
            startActivity(intent)
        }
    }
}