package zeev.fraiman.exersforbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.Locale;

public class TTS_2 extends AppCompatActivity {

    Context context;
    Button bSpeak2;
    private TextToSpeech tts;
    String st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts2);

        initComponents();

        bSpeak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts.speak(st,
                        TextToSpeech.QUEUE_FLUSH,
                        null, null);
            }
        });
    }

    private void initComponents() {
        context=this;
        bSpeak2=findViewById(R.id.bSpeak2);
        st="Welcome to my application";
        tts = new TextToSpeech(context,
                new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
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