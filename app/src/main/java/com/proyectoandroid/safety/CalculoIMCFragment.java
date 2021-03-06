package com.proyectoandroid.safety;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculoIMCFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculoIMCFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText etPeso, etAltura;
    TextView resultado;
    Button btnCalcular;

    public CalculoIMCFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculoIMCFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculoIMCFragment newInstance(String param1, String param2) {
        CalculoIMCFragment fragment = new CalculoIMCFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculo_i_m_c, container, false);
        etPeso=(EditText) view.findViewById(R.id.etPeso);
        etAltura=(EditText) view.findViewById(R.id.etAltura);
        resultado=(TextView) view.findViewById(R.id.Resultado);
        btnCalcular=(Button) view.findViewById(R.id.calcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double peso, altura, IMC;
                peso = Double.parseDouble(etPeso.getText().toString());
                altura = Double.parseDouble(etAltura.getText().toString());
                IMC = peso/Math.pow(altura, 2);
                resultado.setText("Tu IMC es de: "+IMC);

            }
        });

        return view;
    }
}
