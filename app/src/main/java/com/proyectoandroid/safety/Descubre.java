package com.proyectoandroid.safety;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.proyectoandroid.Adapter.RetosAdapter;
import com.proyectoandroid.Adapter.PlazaDetAdapter;
import com.proyectoandroid.Modelo.MaquinasEjercicios;
import com.proyectoandroid.Modelo.Plazas;
import com.proyectoandroid.Modelo.Retos;
import com.proyectoandroid.Modelo.Rutas;

import java.io.Serializable;
import java.util.ArrayList;

public class Descubre extends AppCompatActivity {

    private RecyclerView mList1,mList2;
    private Button button;
    private Button button2;
    private FirebaseFirestore mstore;


    private RetosAdapter adapterretos;
    private PlazaDetAdapter adapterplazas;

    //Para retos
    private ArrayList<Retos> appList ;
    private LinearLayoutManager linearLayoutManager;

    //Para plazas
    private ArrayList<Plazas> listaPlazas;
    private LinearLayoutManager linearLayoutManager2;

    private ProgressDialog cargando;

    //Valores a pasar a otra activity
    private String beneficio = "";
    private String seguridad = "";

    //Para los datos de las maquinas
    private ArrayList<MaquinasEjercicios> listamaquinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubre);
        mstore = FirebaseFirestore.getInstance();
        cargando = new ProgressDialog(this);

        //Referencias a variables
        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        mList1 = findViewById(R.id.list1);
        mList2 = findViewById(R.id.list2);

        //Insercion de retos
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList1.setHasFixedSize(true);
        mList1.setLayoutManager(linearLayoutManager);

        appList = new ArrayList<>();
        adapterretos = new RetosAdapter(this,appList);
        mList1.setAdapter(adapterretos);

        ObtenerDatosRetos();

        //Insercion de plazas
        linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setReverseLayout(true);
        linearLayoutManager2.setStackFromEnd(true);
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        mList2.setHasFixedSize(true);
        mList2.setLayoutManager(linearLayoutManager2);

        listaPlazas = new ArrayList<>();
        adapterplazas = new PlazaDetAdapter(this,listaPlazas);
        mList2.setAdapter(adapterplazas);

        ObtenerDatosPlazas();

        //Onclick del recycle view de retos
        adapterretos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargando.setTitle("Abriendo reto");
                cargando.setMessage("Espere por favor..");
                cargando.show();
                int idbeneficio = appList.get(mList1.getChildAdapterPosition(view)).getId_tipobeneficio();
                int idseguridad = appList.get(mList1.getChildAdapterPosition(view)).getId_tiposeguridad();
                ObtenerDatosBeneficio(String.valueOf(idbeneficio));
                ObtenerDatosSeguridad(String.valueOf(idseguridad));

                if(!seguridad.isEmpty() && !beneficio.isEmpty()) {
                    Intent visorDetalle = new Intent(view.getContext(), RutaActivity.class);
                    visorDetalle.putExtra("nombre", appList.get(mList1.getChildAdapterPosition(view)).getNombre());
                    visorDetalle.putExtra("descripcion", appList.get(mList1.getChildAdapterPosition(view)).getDescripcion());
                    visorDetalle.putExtra("beneficio", beneficio);
                    visorDetalle.putExtra("seguridad", seguridad);

                    Toast.makeText(getApplicationContext(), "Valoresss: " + beneficio, Toast.LENGTH_SHORT).show();
                    cargando.dismiss();
                    //startActivity(visorDetalle);
                }

            }
        });

        //Inicializacion del array de las maquinas
        listamaquinas = new ArrayList<>();
        //Onclick del recycle view de plazas
        adapterplazas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PlazaDetalle.class);

                String iddocumento = listaPlazas.get(mList2.getChildAdapterPosition(view)).getIddocumento();
                ObtenerDatosMaquinas(iddocumento);
                intent.putExtra("miLista", (Serializable) listamaquinas);



                startActivity(intent);
            }
        });

    }


    public void ObtenerDatosRetos(){

        CollectionReference collectionReference = mstore.collection("Retos");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                appList.removeAll(appList);
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    Retos retos = snapshot.toObject(Retos.class);

                    appList.add(retos);
                }

                adapterretos.notifyDataSetChanged();

            }
        });
    }

    public void ObtenerDatosPlazas(){

        CollectionReference collectionReference = mstore.collection("Rutas_recomendadas");
        collectionReference.whereEqualTo("ID_TipoLugar",3).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                listaPlazas.removeAll(listaPlazas);
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    Plazas fotos = snapshot.toObject(Plazas.class);
                    fotos.setIddocumento(snapshot.getId());
                    listaPlazas.add(fotos);

                    adapterplazas.notifyDataSetChanged();
                }
            }
        });
    }

    //Metodo para obtener el beneficio de firebase de acuerdo al id
    public void ObtenerDatosBeneficio(String id){
        mstore.collection("BeneficioReto").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    beneficio = documentSnapshot.getString("tipobeneficio");
                }

            }
        });
    }

    //Metodo para obtener la seguridad desde firebase de acuerdo al id
    public void ObtenerDatosSeguridad(String id){
        mstore.collection("SeguridadReto").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    seguridad = documentSnapshot.getString("tiposeguridad");
                    //Toast.makeText(getApplicationContext(), "BALOR" + seguridad[0], Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void ObtenerDatosMaquinas(String id){
        CollectionReference notebookRef = mstore.collection("Rutas_recomendadas");

        notebookRef.document(id).collection("MaquinasEjercicios")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                listamaquinas.removeAll(listamaquinas);
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    MaquinasEjercicios maquinas = snapshot.toObject(MaquinasEjercicios.class);
                    listamaquinas.add(maquinas);

                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //Para que empiece a mostrar cada articulo de la firebase

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void onClick(View v) {
        if(v.getId() == R.id.button){
            Intent intent = new Intent(this.getApplicationContext(), DetalleReto.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.button2){
            Intent intent = new Intent(this.getApplicationContext(), PlazaDetalle.class);
            startActivity(intent);
        }

        }


}