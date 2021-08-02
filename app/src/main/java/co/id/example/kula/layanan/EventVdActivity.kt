package co.id.example.kula.layanan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.id.example.kula.HomeLayananActivity
import co.id.example.kula.R
import kotlinx.android.synthetic.main.activity_wedding_ft.*
import kotlinx.android.synthetic.main.activity_wedding_ft.btn_back
import kotlinx.android.synthetic.main.activity_wedding_ft.btn_foto
import kotlinx.android.synthetic.main.activity_wedding_ft.btn_hrg
import kotlinx.android.synthetic.main.activity_wedding_vd.*

class EventVdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_vd)

        btn_foto.setOnClickListener {
            val intent = Intent(this@EventVdActivity,
                EventFtActivity::class.java)
            startActivity(intent)
        }

        btn_hrg.setOnClickListener {
            val intent = Intent(this@EventVdActivity,
                EventHrgActivity::class.java)
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            finishAffinity()

            val intent = Intent(
                this@EventVdActivity,
                HomeLayananActivity::class.java
            )
            startActivity(intent)
        }
    }
}