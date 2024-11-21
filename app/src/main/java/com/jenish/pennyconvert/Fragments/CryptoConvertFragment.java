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

import com.jenish.pennyconvert.R;
import com.jenish.pennyconvert.models.CryptoModel;
import com.jenish.pennyconvert.services.CoinLayerApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executor;

public class CryptoConvertFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crypto_convert, container, false);

        EditText amountInput = view.findViewById(R.id.amountInput);
        Spinner baseCryptoInput = view.findViewById(R.id.baseCryptoInput);
        Spinner targetCryptoInput = view.findViewById(R.id.targetCryptoInput);
        TextView convertValueView = view.findViewById(R.id.convertValueView);
        Button convertButton = view.findViewById(R.id.convertButton);
        TextView factView1 = view.findViewById(R.id.factView1);
        TextView factView2 = view.findViewById(R.id.factView2);
        LinearLayout factsViewLinearLayout = view.findViewById(R.id.factsViewLinearLayout);

        Thread cryptoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CoinLayerApiService coinLayerApiService = new CoinLayerApiService();
                CryptoModel cryptoModel = coinLayerApiService.fetchData();

                if (isAdded()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> cryptoList = new ArrayList<>();
                            cryptoList.add("Select");
                            for (String crypto : cryptoModel.rates.keySet()) {
                                cryptoList.add(crypto);
                            }

                            Collections.sort(cryptoList, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    if (o1.equals("Select") || o2.equals("Select")) {
                                        return 0;
                                    }
                                    return o1.compareTo(o2);
                                }
                            });

                            baseCryptoInput.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cryptoList));
                            targetCryptoInput.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, cryptoList));

                            convertButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String amount = amountInput.getText().toString();
                                    String baseCrypto = baseCryptoInput.getSelectedItem().toString();
                                    String targetCrypto = targetCryptoInput.getSelectedItem().toString();

                                    if (amount.isEmpty() || baseCrypto.equals("Select") || targetCrypto.equals("Select")) {
                                        return;
                                    }
                                    double amountDouble = Double.parseDouble(amount);
                                    double baseCryptoRate = cryptoModel.rates.get(baseCrypto);
                                    double targetCryptoRate = cryptoModel.rates.get(targetCrypto);
                                    double convertedAmount = (amountDouble * baseCryptoRate) / targetCryptoRate;
                                    convertValueView.setText((Math.round(convertedAmount * 100) / 100.0) + " " + targetCrypto);
                                    factsViewLinearLayout.setVisibility(View.VISIBLE);
                                    convertValueView.setVisibility(View.VISIBLE);


                                }
                            });

                        }
                    });
                }
            }
        });
        cryptoThread.start();


        return view;
    }
}