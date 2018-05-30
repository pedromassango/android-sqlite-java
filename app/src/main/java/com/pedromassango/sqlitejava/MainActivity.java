package com.pedromassango.sqlitejava;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
        List<Product> productsData = sqlite.get();

        // proucts list as String
        List<String> data = new ArrayList<>();

        // convert all products to a string list
        for(Product p : productsData){
            String tmp = getProductAsTring(p);

            // add product as string in list
            data.add( tmp);
        }

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

        final EditText edtName = new EditText(this);
        edtName.setHint("Product name");
        edtName.setPadding(10,32,10,32);

        final EditText edtPrice = new EditText(this);
        edtPrice.setHint("Product price");
        edtPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtPrice.setPadding(10,32,10,32);

        LinearLayout view = new LinearLayout(this);
        view.setOrientation(LinearLayout.VERTICAL);
        view.addView(edtName);
        view.addView(edtPrice);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Nome do produto")
                .setView(view)
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // if no product name or product price, just do nothing
                        if(edtName.getText().toString().trim().isEmpty()
                                || edtPrice.getText().toString().trim().isEmpty()){
                            return;
                        }

                        // create product entity to save in database
                        Product p = new Product();
                        p.setName(edtName.getText().toString());
                        p.setPrice( Integer.parseInt(edtPrice.getText().toString()));


                        // save in SQLite
                        sqlite.insert(p);

                        // Format product as String
                        String productAsString = getProductAsTring(p);

                        // set the item in adapter
                        adapter.add(productAsString);

                        // Show a success message
                        Toast.makeText(MainActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create()
                .show();
    }

    private String getProductAsTring(Product p){
        return String.format("%s\nPrice: %s",
                p.getName(),
                p.getPrice());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
