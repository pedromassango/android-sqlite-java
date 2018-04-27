package com.pedromassango.sqlitejava.ui;

import java.util.List;

/**
 * Created by Pedro Massango on 4/27/18.
 *
 * The contract for MVP pattern.
 */

public interface MainContract {

    public interface Presenter{
        void onLoadProducts();

        void onSaveProduct(String product);
    }

    public interface View{
        void showProducts(List<String> data);

        void showNoProductsMessage();
    }
}
