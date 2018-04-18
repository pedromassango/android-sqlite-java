package com.pedromassango.sqlitejava;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayAdapter<String> adapter;

    // My SQLite database
    private MyDB sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv_produtos);

        // setup the database
        sqlite = new MyDB(this);
        // Getting data from database
        List<String> data = sqlite.get();

        // If no data in database show a toast
        if(data.size() == 0){
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("There is no data in database, click on the button below to add products!")
                    .setPositiveButton("ENTENDI", null)
                    .show();
        }

        // set the data in adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, data);

        lv.setAdapter(adapter);
    }

    public void AddProduct(View view) {
        showDialogInput();
    }

    private void showDialogInput(){

        final EditText edt = new EditText(this);
        edt.setPadding(10,32,10,32);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Nome do produto")
                .setView(edt)
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // if no text, just do nothing
                        if(edt.getText().toString().trim().isEmpty()){
                            return;
                        }

                        // save in SQLite
                        sqlite.insert(edt.getText().toString());

                        // set the item in adapter
                        adapter.add(edt.getText().toString());

                        // Show a success message
                        Toast.makeText(MainActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
