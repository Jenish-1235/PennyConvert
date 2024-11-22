package com.jenish.pennyconvert.Fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Currency;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.jenish.pennyconvert.R;
import com.jenish.pennyconvert.models.CurrencyModel;
import com.jenish.pennyconvert.services.ExchangeRateApiService;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.Manifest;
import android.widget.Toast;

public class VoiceConvertFragment extends Fragment {


    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1;

    // Check or request permission
    private void requestAudioPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_voice_convert, container, false);

        FloatingActionButton voiceInputButton = view.findViewById(R.id.voiceInputButton);
        TextView outputView  = view.findViewById(R.id.outputView);

        requestAudioPermission();

        SpeechRecognizer sr = SpeechRecognizer.createSpeechRecognizer(requireContext());
        sr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                String str = new String();
                ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                for(int i = 0; i < data.size(); i++){
                    str += data.get(i) + "";
                }
                if(str.length() > 0){
                    outputView.setText("User Command: " +  str);
                }
                resolveCommand(str, outputView);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        final Intent speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        voiceInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputView.setText("Listening...");
                sr.startListening(speechIntent);
                Log.i("1111", "1111");

            }
        });

        return view;
    }

    private void resolveCommand(String command, TextView outputView){
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash-8b", "AIzaSyBt5pEBnSGL2k4A-GTO-6M3zY6ta6O6--A");
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content = new Content.Builder().addText("Get me amount and currency codes in 3 digit for both the country's mentioned in the following sentence: " + command + "in this form: example :- USDINR<Amount> where USD is base currency and INR is target currency and amount is amount to be converted. You don't have to convert the amount. You just need to give me same amount given by user. I will use external api for conversion").build();
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        ExchangeRateApiService exchangeRateApiService = new ExchangeRateApiService();
                        CurrencyModel currencyModel = exchangeRateApiService.fetchData();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String currentText = outputView.getText().toString();
                                outputView.setText(currentText + "\n \n" + "Resolved Command: " + resultText);


                                try {
                                    String baseCurrency = resultText.substring(0,3);
                                    String targetCurrency = resultText.substring(3,6);
                                    Double amount = Double.parseDouble(resultText.substring(6));

                                    double convertedAmount = ((1/currencyModel.conversion_rates.get(baseCurrency)) * (currencyModel.conversion_rates.get(targetCurrency))) * amount ;

                                    outputView.setText( outputView.getText().toString() + " \n \nAmount: " + amount);
                                    outputView.setText( outputView.getText().toString() + " \n \n Converted Amount: " + convertedAmount);
                                }catch (Exception e){
                                    Toast.makeText(getActivity(), "Invalid Command:" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
}