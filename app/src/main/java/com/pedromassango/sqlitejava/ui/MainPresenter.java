package com.pedromassango.sqlitejava.ui;

import com.pedromassango.sqlitejava.data.ProductsRepository;

import java.util.List;

/**
 * Created by Pedro Massango on 4/27/18.
 */

public class MainPresenter implements MainContract.Presenter {

    // The view to connect with the  android libraries
    private MainContract.View view;
    // Repository of data, to connect with database
    private ProductsRepository repository;

    public MainPresenter(MainContract.View view, ProductsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void onLoadProducts() {
        // Get data from repository (our database)
        List<String> products = repository.get();

        // If no products, show a dialog alert
        if(products.isEmpty()){
            view.showNoProductsMessage();
            return;
        }

        // show data in View
        view.showProducts(products);
    }

    @Override
    public void onSaveProduct(String product) {
        // Save the product in database
        repository.insert(product);
    }
}
