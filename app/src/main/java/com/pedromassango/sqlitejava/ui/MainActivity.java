package com.pedromassango.sqlitejava.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pedromassango.sqlitejava.R;
import com.pedromassango.sqlitejava.data.ProductsRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    // The presenter of this View (Activity)
    private MainPresenter presenter;
    // The repository of data, this should be created in View because it need a context
    private ProductsRepository repository;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // VIEW
        ListView lv = findViewById(R.id.lv_produtos);
        // create the adapter to our ListView
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, new ArrayList<String>());
        // Connect the adapter to the listView
        lv.setAdapter(adapter);
        // VIEW


        // Setup the data repository
        repository = new ProductsRepository(this);

        // Setup the presenter of this class
        presenter = new MainPresenter(this, repository);
        // request data in presenter
        presenter.onLoadProducts();
    }

    @Override
    public void showProducts(List<String> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void showNoProductsMessage() {
        new android.support.v7.app.AlertDialog.Builder(this)
                .setMessage("There is no data in database, click on the button below to add products!")
                .setPositiveButton("OK", null)
                .show();
    }

    public void AddProduct(View view) {
        showDialogInput();
    }

    private void showDialogInput() {

        final EditText edt = new EditText(this);
        edt.setPadding(10, 32, 10, 32);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Nome do produto")
                .setView(edt)
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String input = edt.getText().toString().trim();

                        // if no text, just do nothing
                        if (input.isEmpty()) {
                            return;
                        }

                        // Save in database
                        presenter.onSaveProduct(input);

                        // set the item in adapter
                        adapter.add(input);

                        // Show a success message
                        Toast.makeText(MainActivity.this, "Adicionado", Toast.LENGTH_SHORT).show();
                    }
                });

        builder.create()
                .show();
    }
}
