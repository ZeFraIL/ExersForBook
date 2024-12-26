package zeev.fraiman.exersforbook;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TTS_3 extends AppCompatActivity {

    String st="From here you can send SMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts3);

        Start.totalTTS.speak(st, TextToSpeech.QUEUE_FLUSH,
                null, null);
    }
}