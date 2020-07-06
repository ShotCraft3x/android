package com.proyectoandroid.safety


import android.Manifest
import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.proyectoandroid.adapters.loginAdapter
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var btnCreateAccount: Button
    private lateinit var btnRegistro: Button
    private lateinit var btnInvitado:Button
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0
    private val MY_PERMISSIONS_REQUEST_SERVICE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        btnCreateAccount = btn_iniciar
        btnRegistro= btn_registro
        btnInvitado=btn_invitado
        pedirPermisoUbicacion()
        pedirPermisoMensaje()
        pedirPermisoServicio()


        btnCreateAccount.setOnClickListener {
            val intent = Intent(this.applicationContext, IniciarSesion::class.java)
            startActivity(intent)
        }
        btnRegistro.setOnClickListener {
            val intent = Intent(this.applicationContext, Registro::class.java)
            startActivity(intent)
        }
        btnInvitado.setOnClickListener {
            val intent = Intent(this.applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }

        mViewPager = findViewById(R.id.viewPager)
        mViewPager.adapter = loginAdapter(supportFragmentManager, this)
        mViewPager.offscreenPageLimit = 1
    }

    fun pedirPermisoUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@loginActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            return
        }
    }
    fun pedirPermisoServicio() {
        if (ActivityCompat.checkSelfPermission(this, permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@loginActivity, arrayOf(permission.FOREGROUND_SERVICE),
                    MY_PERMISSIONS_REQUEST_SERVICE)
            return
        }
    }

    fun pedirPermisoMensaje() {
        val permisocheck = ContextCompat.checkSelfPermission(this, permission.SEND_SMS)
        if (permisocheck == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission.SEND_SMS), 0)
        }
    }
}
