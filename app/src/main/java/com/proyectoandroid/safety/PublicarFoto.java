package com.proyectoandroid.safety;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.proyectoandroid.Modelo.Fotos;
import com.proyectoandroid.Modelo.Usuario;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;


public class PublicarFoto extends AppCompatActivity  {


    private Uri imageUri;
    private String myUrl = "";

    private StorageReference storageReference;
    private Bitmap thumb_bitmap = null;
    private ImageView foto,close;
    private TextView post;
    private EditText descripcion;
    private ProgressDialog cargando;
    private Button btn;

    //Firestore
    private FirebaseAuth mAuth;

    String nombre = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_foto);
        close = findViewById(R.id.close);
        foto = findViewById(R.id.image_added);
        post = findViewById(R.id.post);
        descripcion = findViewById(R.id.description);
        storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
        cargando = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();


        CropImage.startPickImageActivity(PublicarFoto.this);



    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private String getNombre(){

        String userid = mAuth.getCurrentUser().getUid();
        nombre = "";
        FirebaseDatabase.getInstance().getReference().child("users").child(userid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        nombre = user.getUsername();
                        Toast.makeText(getApplicationContext(),"Nombreeee3: " + nombre,Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        Toast.makeText(getApplicationContext(),"Nombreeee: " + nombre,Toast.LENGTH_SHORT).show();
        return nombre;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(this, data);
            //Recortar imagen
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640, 480)
                    .setAspectRatio(2, 1).start(PublicarFoto.this);

        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            //Imagen recortada
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK) {
                Uri resultUri = result.getUri();
                File url = new File(resultUri.getPath());
                //Cargado de imagen en la activity
                Picasso.with(this).load(url).into(foto);


                //Comprimiendo imagen
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(90)
                            .compressToBitmap(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                final byte[] thumb_byte = byteArrayOutputStream.toByteArray();
                //Fin del compresor

                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cargando.setTitle("Subiendo foto");
                        cargando.setMessage("Espere por favor..");
                        cargando.show();

                        final StorageReference ref = storageReference.child(System.currentTimeMillis()
                                + "." + getFileExtension(imageUri));
                        UploadTask uploadTask = ref.putBytes(thumb_byte);

                        //Subir imagen en storage
                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw Objects.requireNonNull(task.getException());

                                }


                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri downloadUri = task.getResult();








                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Fotos_subidas");


                                String id = reference.push().getKey();
                                Fotos fotos = new Fotos(nombre,downloadUri.toString(),descripcion.getText().toString(),id);


                                String postid = reference.push().getKey();

                                reference.push().setValue(fotos);




                                cargando.dismiss();

                                Toast.makeText(PublicarFoto.this, "Imagen cargada con exito", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                                startActivity(intent);
                            }
                        });
                    }
                });


            }



        }


    }

}