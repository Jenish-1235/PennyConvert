package com.jenish.pennyconvert.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jenish.pennyconvert.R;
import com.jenish.pennyconvert.models.CurrencyModel;
import com.jenish.pennyconvert.services.ExchangeRateApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
                ExchangeRateApiService exchangeRateApiService = new ExchangeRateApiService();
                CurrencyModel currencyData = exchangeRateApiService.fetchData();
                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> currencies = new ArrayList<>();
                            currencies.add("Select");

                            currencies.addAll(currencyData.conversion_rates.keySet());
                            Collections.sort(currencies, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    if (o1.equals("Select") || o2.equals("Select")) {
                                        return 0;
                                    }
                                    return o1.compareTo(o2);
                                }
                            });
                            baseCurrencyInput.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currencies));
                            targetCurrencyInput.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currencies));

                            convertButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String amount = amountInput.getText().toString();
                                    String baseCurrency = baseCurrencyInput.getSelectedItem().toString();
                                    String targetCurrency = targetCurrencyInput.getSelectedItem().toString();

                                    if (amount.isEmpty()) {
                                        Toast.makeText(getActivity(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (baseCurrency.equals("Select") || targetCurrency.equals("Select")) {
                                        Toast.makeText(getActivity(), "Please select a currency", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (baseCurrency.equals(targetCurrency)) {
                                        Toast.makeText(getActivity(), "Please select different currencies", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    double baseCurrencyRate = 1 / currencyData.conversion_rates.get(baseCurrency);
                                    double targetCurrencyRate = currencyData.conversion_rates.get(targetCurrency);
                                    double convertedAmount = Double.parseDouble(amount) * (baseCurrencyRate * targetCurrencyRate);
                                    convertValueView.setText(String.format("%.2f %s", convertedAmount, targetCurrency));
                                    convertValueView.setVisibility(View.VISIBLE);
                                    factsViewLinearLayout.setVisibility(View.VISIBLE);


                                }
                            });
                        }
                    });
                }
            }
        });

        getCurrenciesThread.start();

        return view;
    }
}