package com.proyectoandroid.safety


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.model.LatLng
import com.proyectoandroid.Adapter.DetalleRutaAdapter
import kotlinx.android.synthetic.main.activity_ruta.*
import java.util.*

class RutaActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var btngoogle: Button
    private lateinit var btnsafety: Button


    private val lat = 0.0
    private val lng = 0.0


    //Iniciar una nueva ruta
    private var latruta = 0.0
    private var lngruta = 0.0
    private var nombrepunto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ruta)

        btngoogle = btnGoogle
        btnsafety= btnSafety

        val intent = intent
        val b = intent.extras



        if (b != null) {
            latruta = java.lang.Double.valueOf(b.getString("lat")!!)
            lngruta = java.lang.Double.valueOf(b.getString("lng")!!)
            nombrepunto = b.getString("name")!!
        }

        btngoogle.setOnClickListener {
            googlemap()
        }

        btnsafety.setOnClickListener {
           
            MapsActivity.isOn = true
            val intent = Intent(applicationContext, MapsActivity::class.java)
            intent.putExtra("op", 1)
            intent.putExtra("lat", latruta)
            intent.putExtra("lng", lngruta)
            intent.putExtra("name", nombrepunto)
            startActivity(intent)
        }

        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = DetalleRutaAdapter(supportFragmentManager, this)
        mViewPager.offscreenPageLimit = 1
    }
    fun startCronometro() {
        miServicioNotificacion.isOn = true
        miServicioNotificacion.corriendo = true
        Toast.makeText(applicationContext, "Tiempo iniciado", Toast.LENGTH_SHORT).show()
        val intentService = Intent(this, miServicioNotificacion::class.java)
        startService(intentService)
    }

    fun googlemap() {

                startCronometro();
                var uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latruta,lngruta)
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
        }

    }


