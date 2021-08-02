package co.id.example.kula.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.example.kula.R
import co.id.example.kula.SignInActivity
import kotlinx.android.synthetic.main.activity_onboarding_one.*
import kotlinx.android.synthetic.main.activity_onboarding_two.*
import kotlinx.android.synthetic.main.activity_onboarding_two.btn_home
import kotlinx.android.synthetic.main.activity_onboarding_two.btn_next

class OnboardingTwoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        btn_next.setOnClickListener {
            val intent = Intent(this@OnboardingTwoActivity,
                OnboardingThreeActivity::class.java)
            startActivity(intent)
        }

        btn_home.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@OnboardingTwoActivity,
                SignInActivity::class.java)
            startActivity(intent)
        }
    }
}