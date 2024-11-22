package com.jenish.pennyconvert.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.jenish.pennyconvert.BuildConfig;
import com.jenish.pennyconvert.R;
import com.jenish.pennyconvert.models.CryptoModel;
import com.jenish.pennyconvert.services.CoinLayerApiService;
import com.jenish.pennyconvert.services.GeminiAiApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CryptoConvertFragment extends Fragment{

    String apiKey = BuildConfig.API_KEY;

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

//                                    Log.i("Daaatata", apiKey.toString());
//
                                    getFacts(baseCrypto + " Crypto Currency", 1, factView1, factView2);
                                    getFacts(targetCrypto + " Crypto Currency", 2, factView1, factView2);
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
    private void getFacts(String currency, int factView, TextView factView1, TextView factView2){
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash-8b", apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder().addText("Give me 1 random fact about " + currency).build();
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (factView == 1) {
                                    factView1.setText(resultText);
                                } else {
                                    factView2.setText(resultText);
                                }
                            }
                        });
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.i("Datatata", t.getMessage());
                    }

                }, executor);
    }

    public static String getApiKey(Context context){
        Properties properties = new Properties();
        try{
            InputStream inputStream = new FileInputStream(new File("local.properties"));
            properties.load(inputStream);
        } catch (Exception e) {
            Log.i("Datatata", e.getMessage());
        }
        return properties.getProperty("apiKey");
    }

}