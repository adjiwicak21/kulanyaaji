package co.id.example.kula

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home_about.*
import kotlinx.android.synthetic.main.activity_onboarding_three.*

class HomeAboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_about)

        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@HomeAboutActivity,
                HomeActivity::class.java
            )
            startActivity(intent)
        }
    }
}