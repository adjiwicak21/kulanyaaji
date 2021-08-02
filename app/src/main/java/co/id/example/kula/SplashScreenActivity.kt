package co.id.example.kula

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import co.id.example.kula.onboarding.OnboardingOneActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        auth = FirebaseAuth.getInstance()


//        val mediaPlayer = MediaPlayer().apply {  }

        var handler = Handler()

        handler.postDelayed({
            if(auth.currentUser == null){
                val intent = Intent(this@SplashScreenActivity,
                    OnboardingOneActivity::class.java)
                startActivity(intent)
                finish()
            }else if (auth.currentUser != null){
                val intent = Intent(this@SplashScreenActivity,
                    HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}