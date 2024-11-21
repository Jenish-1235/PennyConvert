package com.jenish.pennyconvert.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jenish.pennyconvert.R;

import java.util.concurrent.Exchanger;

public class CurrencyConvertFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency_convert, container, false);

        EditText amountInput = view.findViewById(R.id.amountInput);
        Spinner baseCurrencyInput = view.findViewById(R.id.baseCurrencyInput);
        Spinner targetCurrencyInput = view.findViewById(R.id.targetCurrencyInput);
        Button convertButton = view.findViewById(R.id.convertButton);
        TextView convertValueView = view.findViewById(R.id.convertValueView);
        LinearLayout factsViewLinearLayout = view.findViewById(R.id.factsViewLinearLayout);

        Thread getCurrenciesThread = new Thread(new Runnable(){
            @Override
            public void run() {

            }
        });

        return view;
    }
}