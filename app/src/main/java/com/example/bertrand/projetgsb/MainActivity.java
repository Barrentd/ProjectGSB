package com.example.bertrand.projetgsb;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;
import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity implements Serializable {

    private TextView resultinsu;
    private TextView affnom;
    private TextView affprenom;
    private RadioButton rdprot1;
    private RadioButton rdprot2;
    private EditText editgly;
    private EditText editnom;
    private EditText editprenom;
    private Button btaffinsu;
    private Button btajout;
    private Button btmldasignal;
    private Button button2activity;
    private String malade;
    private ListView lstVue;
    public static MaladesBDD maladesBDD;
    private Malade unMalade;
    private static final int MYSECONDACTIVITY_REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rdprot1 = (RadioButton) findViewById(R.id.radioButton2);
        rdprot2 = (RadioButton) findViewById(R.id.radioButton);
        btaffinsu = (Button) findViewById(R.id.button);
        btajout = (Button) findViewById(R.id.button2);
        btmldasignal = (Button) findViewById(R.id.button3);
        button2activity = (Button) findViewById(R.id.button2activity);
        editgly = (EditText) findViewById(R.id.editText);
        editnom = (EditText) findViewById(R.id.editText2);
        editprenom = (EditText) findViewById(R.id.editText3);
        resultinsu = (TextView) findViewById(R.id.textView4);
        affnom = (TextView) findViewById(R.id.textView5);
        affprenom = (TextView) findViewById(R.id.textView6);
        editnom.setVisibility(View.INVISIBLE);
        editprenom.setVisibility(View.INVISIBLE);
        btajout.setVisibility(View.INVISIBLE);
        affnom.setVisibility(View.INVISIBLE);
        affprenom.setVisibility(View.INVISIBLE);
        btaffinsu.setOnClickListener(clickListenerbtaffinsu);
        btajout.setOnClickListener(clickListenerbtajout);
        btmldasignal.setOnClickListener(clickListenerbtmldasignal);
        button2activity.setOnClickListener(clickListenerbutton2activity);
        maladesBDD = new MaladesBDD(this);
        maladesBDD.open();
    }

    private View.OnClickListener clickListenerbtaffinsu = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            MapProtocoles m = new MapProtocoles();
            m.initialiser();
            if (editgly.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Saissisez votre glycémie !", Toast.LENGTH_LONG).show();
            } else {
                if (rdprot1.isChecked()) {
                    Protocole p = m.getProtocole(1);
                    double g = Double.parseDouble(editgly.getText().toString());
                    Integer insu = p.insuline(g);
                    resultinsu.setText(insu.toString());
                    if (g >= 2.0) {
                        Toast.makeText(MainActivity.this, "Le patient doit être enregistrer", Toast.LENGTH_LONG).show();
                        editnom.setVisibility(View.VISIBLE);
                        editprenom.setVisibility(View.VISIBLE);
                        btajout.setVisibility(View.VISIBLE);
                        btmldasignal.setVisibility(View.VISIBLE);
                        affnom.setVisibility(View.VISIBLE);
                        affprenom.setVisibility(View.VISIBLE);
                    }
                } else if (rdprot2.isChecked()) {
                    Protocole p = m.getProtocole(2);
                    double g = Double.parseDouble(editgly.getText().toString());
                    Integer insu = p.insuline(g);
                    resultinsu.setText(insu.toString());
                    if (g >= 2.0) {
                        Toast.makeText(MainActivity.this, "Le patient doit être enregistrer", Toast.LENGTH_LONG).show();
                        editnom.setVisibility(View.VISIBLE);
                        editprenom.setVisibility(View.VISIBLE);
                        btajout.setVisibility(View.VISIBLE);
                        affnom.setVisibility(View.VISIBLE);
                        affprenom.setVisibility(View.VISIBLE);
                    }
                } else
                    Toast.makeText(MainActivity.this, "Veuillez choisir le protocole !", Toast.LENGTH_LONG).show();

            }
        }
    };
            private View.OnClickListener clickListenerbtajout = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (editnom.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Veuillez saisir un nom !", Toast.LENGTH_LONG).show();
                    }
                    else if (editprenom.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Veuillez saisir un Prenom !", Toast.LENGTH_LONG).show();
                    }
                    else if (editgly.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Veuillez saisir une glycémie !", Toast.LENGTH_LONG).show();
                    }
                    else {
                        unMalade = new Malade(editnom.getText().toString(), editprenom.getText().toString(), Double.valueOf(editgly.getText().toString()));
                        maladesBDD.ajoutMalade(unMalade);
                        Toast.makeText(MainActivity.this, "Le malade a bien été ajouter !", Toast.LENGTH_LONG).show();
                    }
                }
            };
            private View.OnClickListener clickListenerbtmldasignal = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    malade=" ";
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    ArrayList<Malade> lstMalade = maladesBDD.getTousLesMalades();
                    for(Malade m : lstMalade){
                        malade += m.getNom()+" "+m.getPrenom()+" "+m.getGlycemie()+System.getProperty("line.separator");
                    }
                        // set title
                        alertDialogBuilder.setTitle("Les patients ajouter");

                        // set dialog message
                        alertDialogBuilder.setMessage(malade);

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();
                }
            };
            private View.OnClickListener clickListenerbutton2activity = new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    ArrayList<String> infoMalades = maladesBDD.getToutesLesinfos();
                    Intent it = new Intent(MainActivity.this, Main2Activity.class);
                    it.putExtra("malades",infoMalades);
                    startActivityForResult(it,MYSECONDACTIVITY_REQUESTCODE);
                }
            };
}

