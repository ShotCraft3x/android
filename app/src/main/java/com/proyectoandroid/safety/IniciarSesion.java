package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.proyectoandroid.safety.R;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener{

    private EditText etcorreo;
    private  EditText etpass;
    private Button btninicio;

    private String correo = "";
    private  String pass = "";
    private ProgressBar progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Instancia a firebase auth
        mAuth = FirebaseAuth.getInstance();

        //Referencias a los componentes
        etcorreo = (EditText)findViewById(R.id.txtcorreo);
        etpass = (EditText)findViewById(R.id.txtcontraseña);
        btninicio= (Button)findViewById(R.id.btniniciar);
        progressbar = (ProgressBar)findViewById(R.id.progressBar2);
        btninicio.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        correo = etcorreo.getText().toString().trim();
        pass = etpass.getText().toString().trim();


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
        }


        progressbar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(correo,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Inicio correcto!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}
