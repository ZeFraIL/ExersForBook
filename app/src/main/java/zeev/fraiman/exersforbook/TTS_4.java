package zeev.fraiman.exersforbook;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class TTS_4 extends AppCompatActivity {

    Context context;
    private TextToSpeech tts;
    private String[] guides = {"This is a text part 1",
            "This is a text part 2", "This is a text part 3"};
    private int currentIndex = 0;
    Button bSpeak4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts4);

        initComponents();

        bSpeak4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Начинаем озвучивание с первого элемента массива
                currentIndex = 0; // Сбрасываем индекс на начало
                speakGuide(currentIndex);
            }
        });
    }

    private void initComponents() {
        context=this;
        bSpeak4=findViewById(R.id.bSpeak4);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(new Locale("en-En"));

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {

                        }

                        @Override
                        public void onDone(String utteranceId) {
                            currentIndex++;
                            if (currentIndex < guides.length) {
                                speakGuide(currentIndex);
                            }
                        }

                        @Override
                        public void onError(String utteranceId) {

                        }
                    });
                }
            }
        });
    }

    private void speakGuide(int index) {
        if (index < guides.length) {
            String guide = guides[index];
            // Озвучивание текста с использованием Utterance ID для отслеживания завершения
            tts.speak(guide, TextToSpeech.QUEUE_FLUSH, null, Integer.toString(index));
        }
    }

    @Override
    protected void onDestroy() {
        // Освобождение ресурсов TextToSpeech
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}