package zeev.fraiman.exersforbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class TTS_1 extends AppCompatActivity
        implements TextToSpeech.OnInitListener{

    Context context;
    private TextToSpeech mTTS;
    private String st="Welcome to my application!";
    Button bSpeak1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tts1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();

        bSpeak1.setOnClickListener(v -> {
            mTTS.speak(st, TextToSpeech.QUEUE_FLUSH,
                    null, null);
        });
    }

    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTTS.setLanguage(Locale.US);

            if (result==TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(context, "Language not supported", Toast.LENGTH_SHORT).show();
            } else {
                //mTTS.speak(st, TextToSpeech.QUEUE_FLUSH,null, null);
            }
        } else {
            Toast.makeText(context, "Initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void initComponents() {
        context=this;
        mTTS=new TextToSpeech(context, this);
        bSpeak1=findViewById(R.id.bSpeak1);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}