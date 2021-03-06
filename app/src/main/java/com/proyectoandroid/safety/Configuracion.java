package com.proyectoandroid.safety;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyectoandroid.Modelo.ContactAdapter;
import com.proyectoandroid.Modelo.ContactDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuracion extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private Button btnregistro;
    private ImageButton btnagregar;
    private ImageButton btneliminar;
    private String nombreContacto = "";
    private String numeroContacto = "";
    private EditText txtpin;
    private ListView listcontactos;
    static final int FLAG_CONTACT = 1; //Variable para comprobar los contactos, si es 1 fue correcto

    //Lista de contactos
    private ContactAdapter mAdapter;
    private List<Contactos> mlista = new ArrayList<>();
    private ContactDB cb;
    private int size = 0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        button = (Button)findViewById(R.id.btncerrarsesion);
        btnregistro = (Button)findViewById(R.id.btnregistrar);
        btnagregar = (ImageButton)findViewById(R.id.btnagregar);
        btneliminar = (ImageButton)findViewById(R.id.btneliminar);
        listcontactos = (ListView)findViewById(R.id.listacontactos);
        txtpin = (EditText) findViewById(R.id.txtpin);
        btnagregar.setOnClickListener(this);
        btneliminar.setOnClickListener(this);
        btnregistro.setOnClickListener(this);
        button.setOnClickListener(this);
        this.llenarLista();


    }

    public void llenarLista(){
        cb = new ContactDB(this);
        List<Contactos> miscontactos = new ArrayList<>();
        miscontactos = cb.consultarDatos();
        size = miscontactos.size();
        if(size == 0){
            mlista.add(new Contactos("",""));
        }else{
            mlista.clear();
            for(int i = 0; i<miscontactos.size();i++) {
                mlista.add(new Contactos(miscontactos.get(i).getNombre(),miscontactos.get(i).getNumero()));
            }
        }
        mAdapter = new ContactAdapter(Configuracion.this, R.layout.adapter_contactos, mlista);
        listcontactos.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btncerrarsesion){
            logout(view);
        }

        if(view.getId()==R.id.btnagregar){
            pedirPermisoMensaje();

            if(size<3){
                Toast.makeText(getApplicationContext(),"Agregaras un contacto",Toast.LENGTH_SHORT).show();
                seleccionarContacto();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Contacto favorito");
                builder.setMessage("Solo puedes añadir 3 contactos");
                builder.setPositiveButton("Aceptar", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        if(view.getId()==R.id.btneliminar){
            pedirPermisoMensaje();
            ContactDB contact = new ContactDB(this);
            contact.eliminarDatos();
            this.llenarLista();

        }

        if(view.getId() == R.id.btnregistrar){
            ContactDB contact = new ContactDB(this);
            //Aqui se hace el registro de los contactos en la SQLITE
            String pin = txtpin.getText().toString().trim();
            if(!pin.isEmpty()){
                setPin(pin);
                Toast.makeText(getApplicationContext(), "PIN Actualizado correctamente!", Toast.LENGTH_SHORT).show();
            }
            contact.registrarDatos(nombreContacto, numeroContacto);
            this.llenarLista();

        }



    }

    private void setPin(String pin){
        String userid = mAuth.getCurrentUser().getUid();
        Map<String,Object> map = new HashMap<>();
        map.put("pin",pin);
        mStore.collection("users").document(userid).update(map);

    }

    private void seleccionarContacto() {
        Intent seleccionaContacto = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));

        seleccionaContacto.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); //se hace la seleccion del tipo contacto
        startActivityForResult(seleccionaContacto,FLAG_CONTACT);

    }

    public void pedirPermisoMensaje() {
        int permisocheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permisocheck== PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_CONTACT) {
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                //Se consulta a los contactos
                //Base de datos de los contactos
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);

                if(cursor.moveToFirst()){ //Si al menos hubo 1 contacto
                    int columnaNombre = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int columnaTelefono = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    nombreContacto = cursor.getString(columnaNombre);
                    numeroContacto = cursor.getString(columnaTelefono);
                    //txtnombre.setText(nombreContacto);
                    //txtnumero.setText(numeroContacto);
                    Toast.makeText(getApplicationContext(),"Seleccionaste un contacto",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(),loginActivity.class);
        startActivity(intent);
        finish();
    }
}
