package com.proyectoandroid.safety;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import android.os.Bundle;

import com.hanks.passcodeview.PasscodeView;

public class PassCode extends AppCompatActivity {

    PasscodeView passcodeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code);
        passcodeView=findViewById(R.id.passcodeView);
        passcodeView.setPasscodeLength(4).setLocalPasscode("1234").setListener(new PasscodeView.PasscodeViewListener(){
            @Override
            public void onFail() {
                Toast.makeText(PassCode.this, "La contraseña es incorrecta.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(String number) {
                Intent intent = new Intent(PassCode.this, IniciarRuta.class);
                startActivity(intent);
            }
        });
    }

}
