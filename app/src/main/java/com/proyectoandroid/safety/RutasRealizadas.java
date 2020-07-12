package com.proyectoandroid.safety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.proyectoandroid.Adapter.AdaptadorRutas;

import com.proyectoandroid.Adapter.RutasAdapter;

import com.proyectoandroid.Modelo.Rutas;

import java.util.ArrayList;

public class RutasRealizadas extends AppCompatActivity {


    private double lat = 0.0;
    private double lng = 0.0;


    private RutasAdapter rutasAdapter;
    private ArrayList<Rutas> postLists;

    private FirebaseFirestore mstore;
    private LinearLayoutManager linearLayoutManager;


    //Matriz
    private RecyclerView recyclerView;
    //almacena los datos de los lugares
    String[][] datos={
            { "","Tienda de bicicleta " ,"Thoros Bike","-29.9058415","-71.2517834" },
            { "","Tienda de Deporte" ,"Ciclos Acquea","-29.9058037","-71.2517834" },
            { "","Tienda de bicicleta" ,"Cycles Serena","-29.9057659","-71.2517834" },
            { "","Tienda de deporte" ,"Original Bike","-29.9329333","-71.2603048" },
    };
    //Obtiene  las imagenes de la carpeta drawable
    int[] datosImg={R.drawable.thorobike,R.drawable.ciclesaquea,R.drawable.ciclesserena,R.drawable.originalbike};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lista_ruta);

        /*
        //instanciar la lista
        lista=(ListView) findViewById(R.id.lvLista);

        lista.setAdapter(new AdaptadorRutas(this,datos,datosImg));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalle=new Intent(view.getContext(), DetallesRutaRealizadas.class);
                visorDetalle.putExtra("FEC",datos[position][0]);
                visorDetalle.putExtra("HOR",datos[position][1]);

                visorDetalle.putExtra("RUT",datos[position][2]);

                //------------------------
                visorDetalle.putExtra("lat",Double.valueOf(datos[position][3]));
                visorDetalle.putExtra("lng",Double.valueOf(datos[position][4]));

                startActivity(visorDetalle);
            }
        });

         */
        mstore = FirebaseFirestore.getInstance();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        rutasAdapter = new RutasAdapter(this,postLists);
        recyclerView.setAdapter(rutasAdapter);
        obtenerRutas();
        rutasAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent visorDetalle=new Intent(view.getContext(),RutaActivity.class);


                visorDetalle.putExtra("lng",postLists.get(recyclerView.getChildAdapterPosition(view)).getLongitud());
                visorDetalle.putExtra("lat",postLists.get(recyclerView.getChildAdapterPosition(view)).getLatitud());
                visorDetalle.putExtra("name",postLists.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                startActivity(visorDetalle);



            }
        });




    }

    public void obtenerRutas(){
        CollectionReference collectionReference = mstore.collection("Rutas_recomendadas");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                postLists.removeAll(postLists);
                for(QueryDocumentSnapshot snapshot: queryDocumentSnapshots){
                    Rutas fotos = snapshot.toObject(Rutas.class);
                    postLists.add(fotos);
                }

                rutasAdapter.notifyDataSetChanged();

            }
        });
    }




     private void obtenerRutass(){
         /*
         linearLayoutManager = new LinearLayoutManager(this);
         linearLayoutManager.setReverseLayout(true);
         linearLayoutManager.setStackFromEnd(true);
         //recyclerlist = findViewById(R.id.recycle_view);
         //recyclerView.setHasFixedSize(true);
         //recyclerlist.setLayoutManager(linearLayoutManager);

         Query query = mstore.collection("Rutas_recomendadas");

         FirestoreRecyclerOptions<Rutas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.
                 Builder<Rutas>().setQuery(query,Rutas.class).build();

         //rutasAdapter = new RutasAdapter(firestoreRecyclerOptions);
         //rutasAdapter.notifyDataSetChanged();
         //recyclerlist.setAdapter(rutasAdapter);

         rutasAdapter.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent visorDetalle=new Intent(view.getContext(), DetallesRutaRealizadas.class);

                 visorDetalle.putExtra("FEC",postLists.get(recyclerlist.getChildAdapterPosition(view)).getLatitud());

                 startActivity(visorDetalle);



             }
         });


          */
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
}
