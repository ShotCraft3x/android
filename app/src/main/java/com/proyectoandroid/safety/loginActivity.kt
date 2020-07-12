package com.proyectoandroid.safety


import android.Manifest
import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.proyectoandroid.Adapter.loginAdapter
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var btnCreateAccount: Button
    private lateinit var btnRegistro: Button
    private lateinit var btnInvitado: Button
    private val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0
    private val MY_PERMISSIONS_REQUEST_SERVICE = 0
    private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    private val REQUEST_PERMISSION_CAMERA = 1

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Referencia para el inicio de sesion
        mAuth = FirebaseAuth.getInstance()


        btnCreateAccount = btn_iniciar
        btnRegistro = btn_registro
        btnInvitado = btn_invitado
        //pedirPermisoUbicacion()
        //pedirPermisoMensaje()
        //pedirPermisoServicio()
        //pedirPermisoFotos()


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

        verificarPermisos()
    }

    private fun verificarPermisos() {
        val permsRequestCode = 100
        val perms = arrayOf(permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION,
                permission.CAMERA,permission.SEND_SMS,permission.FOREGROUND_SERVICE,
                permission.WRITE_EXTERNAL_STORAGE,permission.READ_EXTERNAL_STORAGE)

        val accessFinePermission = checkSelfPermission(permission.ACCESS_FINE_LOCATION)
        val accessCoarsePermission = checkSelfPermission(permission.ACCESS_COARSE_LOCATION)
        val cameraPermission = checkSelfPermission(permission.CAMERA)

        val permisomensaje = checkSelfPermission(permission.SEND_SMS)

        val permisolectura = checkSelfPermission(permission.READ_EXTERNAL_STORAGE)
        val permisoescritura = checkSelfPermission(permission.WRITE_EXTERNAL_STORAGE)
        val permisoservicio = checkSelfPermission(permission.FOREGROUND_SERVICE)

        if (cameraPermission == PackageManager.PERMISSION_GRANTED && accessFinePermission == PackageManager.PERMISSION_GRANTED
                && accessCoarsePermission == PackageManager.PERMISSION_GRANTED && permisomensaje == PackageManager.PERMISSION_GRANTED
                && permisolectura == PackageManager.PERMISSION_GRANTED && permisoescritura == PackageManager.PERMISSION_GRANTED
                && permisoservicio == PackageManager.PERMISSION_GRANTED) {
            //se realiza metodo si es necesario...
        } else {
            requestPermissions(perms, permsRequestCode)

        }
    }


    fun pedirPermisoUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@loginActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS)
            return
        }
    }

    fun pedirPermisoFotos() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //to simplify, call requestPermissions again
                Toast.makeText(getApplicationContext(),
                        "shouldShowRequestPermissionRationale",
                        Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this@loginActivity,
                        arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this@loginActivity,
                        arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
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
