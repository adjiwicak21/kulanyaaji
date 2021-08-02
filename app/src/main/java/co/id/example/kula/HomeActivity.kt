package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.example.kula.onboarding.OnboardingThreeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_onboarding_two.*
import kotlinx.android.synthetic.main.activity_onboarding_two.btn_next

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth= FirebaseAuth.getInstance()

        btn_profil.setOnClickListener {
            val intent = Intent(this@HomeActivity,
                HomeAboutActivity::class.java)
            startActivity(intent)
        }

        btn_reservasi.setOnClickListener {
            val intent = Intent(this@HomeActivity,
                HomeReservasiActivity::class.java)
            startActivity(intent)
        }

        btn_layanan.setOnClickListener {
            val intent = Intent(this@HomeActivity,
                HomeLayananActivity::class.java)
            startActivity(intent)
        }

        btn_data.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@HomeActivity,
                HomeDataActivity::class.java)
            startActivity(intent)
        }

        btn_logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this@HomeActivity,
                SignInActivity::class.java)
            startActivity(intent)
        }
    }
}