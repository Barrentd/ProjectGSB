package com.example.bertrand.projetgsb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    private Button btnreturn;
    private Button btnenvoielist;
    private ArrayList<String> lstMalades;
    private ArrayAdapter<String> affichage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2_main);
        btnreturn = (Button) findViewById(R.id.btnret);
        btnenvoielist = (Button) findViewById(R.id.button9);
        btnenvoielist.setOnClickListener(clickListenerbtnenvoielist);
        btnreturn.setOnClickListener(clickListenerbtnreturn);
        listView = (ListView) findViewById(R.id.listView1);
        Bundle b1 = getIntent().getExtras();
        ArrayList<String> test  = b1.getStringArrayList("malades");
        getIntent().getStringArrayExtra("malades");
        lstMalades = (ArrayList<String>) getIntent().getSerializableExtra("malades");
        affichage = new ArrayAdapter<String>(Main2Activity.this,android.R.layout.simple_list_item_1,lstMalades);
        listView.setAdapter(affichage);

        // la gestion des clics sur les items de la liste

    }
    private View.OnClickListener clickListenerbtnreturn = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Main2Activity.this.finish();
        }
    };
    private View.OnClickListener clickListenerbtnenvoielist = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            ArrayList<String> infoMalades = MainActivity.maladesBDD.getToutesLesinfos();
            String corp = "";
            for(int i = 0; i<infoMalades.size(); i++){
                corp += infoMalades.get(i) + System.getProperty("line.separator");
            }
            try {
                Intent returnIt = new Intent(Intent.ACTION_SEND);
                String[] tos = {"bertrand.valat@gmail.com"};
                String[] ccs = {" "};
                returnIt.setType("message/rfc882");
                returnIt.putExtra(Intent.EXTRA_EMAIL, tos);
                returnIt.putExtra(Intent.EXTRA_CC, ccs);
                returnIt.putExtra(Intent.EXTRA_SUBJECT, "Malade souffrant d'hyperglycemie");
                returnIt.putExtra(Intent.EXTRA_TEXT,corp);
                //returnIt.putExtra(Intent.EXTRA_TEXT,infoMalades.toString());
                startActivity(Intent.createChooser(returnIt, "Choose Email Client"));
                MainActivity.maladesBDD.viderTable();
                Main2Activity.this.finish();
            }catch (Exception e){
                //Toast.makeText(Main2Activity.this,"Le mail n'a pas pu être envoyé", Toast.LENGTH_LONG).show();
                Toast.makeText(Main2Activity.this,e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };
}
