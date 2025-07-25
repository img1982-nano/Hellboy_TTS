
package com.azilamo.hellboytts;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import android.content.Intent;
import android.net.Uri;

public class MainActivity extends Activity {
    private TextToSpeech tts;
    private ArrayList<String> quotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadQuotes();

        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.JAPANESE);
            }
        });

        Button speakBtn = findViewById(R.id.speakButton);
        speakBtn.setOnClickListener(v -> {
            if (!quotes.isEmpty()) {
                String quote = quotes.get(new Random().nextInt(quotes.size()));
                tts.speak(quote, TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        Button openUrlBtn = findViewById(R.id.openUrlButton);
        openUrlBtn.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://azilamo.com"));
            startActivity(browserIntent);
        });
    }

    private void loadQuotes() {
        try {
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(getAssets().open("quotes.txt"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                quotes.add(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
