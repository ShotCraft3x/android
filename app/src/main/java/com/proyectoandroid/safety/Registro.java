package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyectoandroid.Modelo.Usuario;
import com.proyectoandroid.safety.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity implements View.OnClickListener,Dialogo.ExampleDialogListener{

    private String TAG;
    private EditText etnombre;
    private EditText etcorreo;
    private  EditText etpass;
    private  EditText etpass2;
    //private  EditText etapellidop;
    //private  EditText etapellidom;
    private Button btnregistro;
    private String userid;

    //Variables para ingresar los datos a firebase
    private String nombre = "";
    //private String apellidom = "";
    //private String apellidop = "";
    private String correo = "";
    private  String pass = "";
    private  String pass2 = "";
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Instancia a firebase auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        //Referencias a los componentes
        etnombre = (EditText)findViewById(R.id.txtnombre);
        //etapellidom = (EditText)findViewById(R.id.txtapellidom);
        //etapellidop = (EditText)findViewById(R.id.txtapellidop);
        progressbar = (ProgressBar)findViewById(R.id.progressBar);
        etcorreo = (EditText)findViewById(R.id.txtcorreo);
        etpass = (EditText)findViewById(R.id.txtpass);
        etpass2 = (EditText)findViewById(R.id.txtpass2);
        btnregistro = (Button)findViewById(R.id.btnregistrar);
        btnregistro.setOnClickListener(this);

        if(mAuth.getCurrentUser()!=null){
            Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public void onClick(View view) {

          nombre = etnombre.getText().toString().trim();
            correo = etcorreo.getText().toString().trim();
            pass = etpass.getText().toString().trim();
            pass2 = etpass2.getText().toString().trim();

            if(TextUtils.isEmpty(correo)){
                etcorreo.setError("El correo es requerido");
                return;
            }

            if(TextUtils.isEmpty(pass)){
                etpass.setError("La contraseña es requerida");
                return;
            }

            if(pass.length() <6){
                etpass.setError("La contraseña debe tener mas de 6 caracteres");
                return;
            }

            if(!pass.equals(pass2)){
                etpass.setError("La contraseña deben ser iguales");
                etpass2.setError("La contraseña deben ser iguales");
                return;
            }


        openDialog(getApplicationContext(),nombre,correo,pass);

    }
    public void openDialog(Context c, String nombre, String correo, String pass) {
        Dialogo exampleDialog = new Dialogo();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }


    public void RegistrarUsuario(final String pin){
            mAuth.createUserWithEmailAndPassword(correo,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getApplicationContext(),"Registro exitoso!",Toast.LENGTH_LONG).show();
                        //Obtenemos la id del usuario registrado
                        userid = mAuth.getCurrentUser().getUid();

                        DocumentReference documentReference = db.collection("users").document(userid);


                        Usuario user = new Usuario(nombre,correo,pass,pin);


                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG,"Usuario creado correctamente");

                            }
                        });

                        Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                        startActivity(intent);


                    }else{
                        Toast.makeText(getApplicationContext(),"Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
                    }
                }
            });
    }

    @Override
    public void applyTexts(String password) {
        Toast.makeText(getApplicationContext(),"Valor: " + password,Toast.LENGTH_SHORT).show();
        this.RegistrarUsuario(password);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
