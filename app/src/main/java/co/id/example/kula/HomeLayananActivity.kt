package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.example.kula.layanan.EventFtActivity
import co.id.example.kula.layanan.ProdukFtActivity
import co.id.example.kula.layanan.SesiFtActivity
import co.id.example.kula.layanan.WeddingFtActivity
import co.id.example.kula.onboarding.OnboardingThreeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_data.*
import kotlinx.android.synthetic.main.activity_home_data.btn_back
import kotlinx.android.synthetic.main.activity_home_layanan.*
import kotlinx.android.synthetic.main.activity_onboarding_two.*

class HomeLayananActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_layanan)



        btn_wedding.setOnClickListener {
            val intent = Intent(this@HomeLayananActivity,
                WeddingFtActivity::class.java)
            startActivity(intent)
        }

        btn_event.setOnClickListener {
            val intent = Intent(this@HomeLayananActivity,
                EventFtActivity::class.java)
            startActivity(intent)
        }

        btn_sesi.setOnClickListener {
            val intent = Intent(this@HomeLayananActivity,
                SesiFtActivity::class.java)
            startActivity(intent)
        }

        btn_produk.setOnClickListener {
            val intent = Intent(this@HomeLayananActivity,
                ProdukFtActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@HomeLayananActivity,
                HomeActivity::class.java
            )
            startActivity(intent)
        }

    }
}