package zeev.fraiman.exersforbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Start extends AppCompatActivity {

    Context context;
    Button bTV, bTTS1, bTTS2, bTTS3, bTTS4, bTTS5, bSTT, bVK, bFW, bFP, bAB;
    public static TextToSpeech totalTTS;
    Locale my_lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initCompomemts();

        bTV.setOnClickListener(v -> {
            Intent go=new Intent(context, CustomTV.class);
            startActivity(go);
        });

        bTTS1.setOnClickListener(v -> {
            Intent go=new Intent(context, TTS_1.class);
            startActivity(go);
        });

        bTTS2.setOnClickListener(v -> {
            Intent go=new Intent(context, TTS_2.class);
            startActivity(go);
        });

        bTTS3.setOnClickListener(v -> {
            Intent go=new Intent(context, TTS_3.class);
            startActivity(go);
        });

        bTTS4.setOnClickListener(v -> {
            Intent go=new Intent(context, TTS_4.class);
            startActivity(go);
        });

        bTTS5.setOnClickListener(v -> {
            Intent go=new Intent(context, TTS_5.class);
            startActivity(go);
        });

        bSTT.setOnClickListener(v -> {
            Intent go=new Intent(context, STT_1.class);
            startActivity(go);
        });

        bVK.setOnClickListener(v -> {
            Intent go=new Intent(context, VolumeKnob.class);
            startActivity(go);
        });

        bFW.setOnClickListener(v -> {
            Intent go=new Intent(context, Fireworks.class);
            startActivity(go);
        });

        bFP.setOnClickListener(v -> {
            Intent go=new Intent(context, FlyingPengion.class);
            startActivity(go);
        });

    }

    private void initCompomemts() {
        context=this;
        bTV=findViewById(R.id.bTV);
        bTTS1=findViewById(R.id.bTTS1);
        bTTS2=findViewById(R.id.bTTS2);
        bTTS3=findViewById(R.id.bTTS3);
        bTTS4=findViewById(R.id.bTTS4);
        bTTS5=findViewById(R.id.bTTS5);
        bSTT=findViewById(R.id.bSTT);
        bVK=findViewById(R.id.bVK);
        bFW=findViewById(R.id.bFW);
        bFP=findViewById(R.id.bFP);

        totalTTS = new TextToSpeech(context,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            //totalTTS.setLanguage(Locale.US);
                            my_lang=new Locale("he","IL");
                            totalTTS.setLanguage(my_lang);
                        }
                    }
                });
    }
}