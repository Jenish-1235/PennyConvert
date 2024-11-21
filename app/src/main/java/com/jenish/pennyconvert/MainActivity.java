package com.jenish.pennyconvert;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.jenish.pennyconvert.Fragments.CryptoConvertFragment;
import com.jenish.pennyconvert.Fragments.CurrencyConvertFragment;
import com.jenish.pennyconvert.Fragments.HistoryFragment;
import com.jenish.pennyconvert.Fragments.NewsFragment;
import com.jenish.pennyconvert.Fragments.VoiceConvertFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TypedValue colorPrimary = new TypedValue();
        getTheme().resolveAttribute(com.google.android.material.R.attr.colorPrimary, colorPrimary, true);


        // clear FLAG_TRANSLUCENT_STATUS flag:
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(colorPrimary.data);

        ImageView helpButton = findViewById(R.id.helpButton);
        helpButton.setOnClickListener(v -> {
            Intent helpActivity = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(helpActivity);
        });


        bottomTabLayoutFormation(2);
    }


    private void bottomTabLayoutFormation(int i){
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setTabRippleColor(null);

        TabLayout.Tab voiceTab = tabLayout.newTab();
        View voiceTabView = getLayoutInflater().inflate(R.layout.bottom_tab_layout_item, null);
        TextView voiceTabText = voiceTabView.findViewById(R.id.tab_text);
        voiceTabText.setText("Voice");
        ImageView voiceTabIcon = voiceTabView.findViewById(R.id.tab_icon);
        voiceTabIcon.setImageResource(R.drawable.ic_mic_off);
        voiceTab.setCustomView(voiceTabView);
        tabLayout.addTab(voiceTab);

        TabLayout.Tab cryptoTab = tabLayout.newTab();
        View cryptoTabView = getLayoutInflater().inflate(R.layout.bottom_tab_layout_item, null);
        TextView cryptoTabText = cryptoTabView.findViewById(R.id.tab_text);
        cryptoTabText.setText("Crypto");
        ImageView cryptoTabIcon = cryptoTabView.findViewById(R.id.tab_icon);
        cryptoTabIcon.setImageResource(R.drawable.ic_crypto);
        cryptoTab.setCustomView(cryptoTabView);
        tabLayout.addTab(cryptoTab);


        TabLayout.Tab currencyTab = tabLayout.newTab();
        View currencyTabView = getLayoutInflater().inflate(R.layout.bottom_tab_layout_item, null);
        TextView currencyTabText = currencyTabView.findViewById(R.id.tab_text);
        currencyTabText.setText("Currency");
        ImageView currencyTabIcon = currencyTabView.findViewById(R.id.tab_icon);
        currencyTabIcon.setImageResource(R.drawable.ic_currency);
        currencyTab.setCustomView(currencyTabView);
        tabLayout.addTab(currencyTab);

        TabLayout.Tab newsTab = tabLayout.newTab();
        View newsTabView = getLayoutInflater().inflate(R.layout.bottom_tab_layout_item, null);
        TextView newsTabText = newsTabView.findViewById(R.id.tab_text);
        newsTabText.setText("News");
        ImageView newsTabIcon = newsTabView.findViewById(R.id.tab_icon);
        newsTabIcon.setImageResource(R.drawable.ic_news);
        newsTab.setCustomView(newsTabView);
        tabLayout.addTab(newsTab);

        TabLayout.Tab historyTab = tabLayout.newTab();
        View historyTabView = getLayoutInflater().inflate(R.layout.bottom_tab_layout_item, null);
        TextView historyTabText = historyTabView.findViewById(R.id.tab_text);
        historyTabText.setText("History");
        ImageView historyTabIcon = historyTabView.findViewById(R.id.tab_icon);
        historyTabIcon.setImageResource(R.drawable.ic_history);
        historyTab.setCustomView(historyTabView);
        tabLayout.addTab(historyTab);

        TypedValue colorOnPrimary = new TypedValue();
        TypedValue colorAccent = new TypedValue();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                getTheme().resolveAttribute(com.google.android.material.R.attr.colorOnPrimary, colorOnPrimary, true);
                getTheme().resolveAttribute(com.google.android.material.R.attr.colorAccent, colorAccent, true);
                switch (position){
                    case 0:

                        voiceTabIcon.setImageResource(R.drawable.ic_mic);
                        voiceTabIcon.setImageTintList(ColorStateList.valueOf(colorAccent.data));
                        voiceTabText.setTextColor(colorAccent.data);

                        cryptoTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        cryptoTabText.setTextColor(colorOnPrimary.data);

                        currencyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        currencyTabText.setTextColor(colorOnPrimary.data);

                        newsTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        newsTabText.setTextColor(colorOnPrimary.data);

                        historyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        historyTabText.setTextColor(colorOnPrimary.data);

                        Fragment voice_converter_fragment = new VoiceConvertFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, voice_converter_fragment).commit();
                        break;

                    case 1:
                        cryptoTabIcon.setImageTintList(ColorStateList.valueOf(colorAccent.data));
                        cryptoTabText.setTextColor(colorAccent.data);

                        voiceTabIcon.setImageResource(R.drawable.ic_mic_off);
                        voiceTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        voiceTabText.setTextColor(colorOnPrimary.data);

                        currencyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        currencyTabText.setTextColor(colorOnPrimary.data);

                        newsTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        newsTabText.setTextColor(colorOnPrimary.data);

                        historyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        historyTabText.setTextColor(colorOnPrimary.data);

                        Fragment crypto_convert_fragment = new CryptoConvertFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, crypto_convert_fragment).commit();
                        break;

                    case 2:
                        currencyTabIcon.setImageTintList(ColorStateList.valueOf(colorAccent.data));
                        currencyTabText.setTextColor(colorAccent.data);

                        voiceTabIcon.setImageResource(R.drawable.ic_mic_off);
                        voiceTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        voiceTabText.setTextColor(colorOnPrimary.data);

                        cryptoTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        cryptoTabText.setTextColor(colorOnPrimary.data);

                        newsTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        newsTabText.setTextColor(colorOnPrimary.data);

                        historyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        historyTabText.setTextColor(colorOnPrimary.data);

                        Fragment currency_convert_fragment = new CurrencyConvertFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, currency_convert_fragment).commit();
                        break;

                    case 3:
                        newsTabIcon.setImageTintList(ColorStateList.valueOf(colorAccent.data));
                        newsTabText.setTextColor(colorAccent.data);

                        voiceTabIcon.setImageResource(R.drawable.ic_mic_off);
                        voiceTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        voiceTabText.setTextColor(colorOnPrimary.data);

                        cryptoTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        cryptoTabText.setTextColor(colorOnPrimary.data);

                        currencyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        currencyTabText.setTextColor(colorOnPrimary.data);

                        historyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        historyTabText.setTextColor(colorOnPrimary.data);

                        Fragment news_fragment = new NewsFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, news_fragment).commit();
                        break;

                    case 4:
                        historyTabIcon.setImageTintList(ColorStateList.valueOf(colorAccent.data));
                        historyTabText.setTextColor(colorAccent.data);

                        voiceTabIcon.setImageResource(R.drawable.ic_mic_off);
                        voiceTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        voiceTabText.setTextColor(colorOnPrimary.data);

                        cryptoTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        cryptoTabText.setTextColor(colorOnPrimary.data);

                        currencyTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        currencyTabText.setTextColor(colorOnPrimary.data);

                        newsTabIcon.setImageTintList(ColorStateList.valueOf(colorOnPrimary.data));
                        newsTabText.setTextColor(colorOnPrimary.data);

                        Fragment history_fragment = new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, history_fragment).commit();
                        break;

                    default:
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab){
            }
        });

        Objects.requireNonNull(tabLayout.getTabAt(i)).select();

    }


}