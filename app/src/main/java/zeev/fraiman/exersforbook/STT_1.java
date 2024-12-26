package zeev.fraiman.exersforbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;
import java.util.Random;

public class STT_1 extends AppCompatActivity {

    Context context;
    TextView tvNumber, tvResult;
    Button bNumber, bCheck;
    private int generatedNumber;
    private ActivityResultLauncher<Intent> speechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt1);

        initComponents();

        bNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                generatedNumber = random.nextInt(900) + 100;
                tvNumber.setText(String.valueOf(generatedNumber));
                speakNumber();
            }
        });

        bCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void speakNumber() {
        Start.totalTTS.speak(String.valueOf(generatedNumber),
                TextToSpeech.QUEUE_FLUSH,
                null, null);
    }

    private void checkAnswer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        speechRecognizer.launch(intent);
    }

    private void onSpeechResult(String spokenText) {
        try {
            int spokenNumber = Integer.parseInt(spokenText);
            if (spokenNumber == generatedNumber) {
                Toast.makeText(context, "Correct", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Incorrect", Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Error parsing spoken text", Toast.LENGTH_LONG).show();
        }
    }

    private void initComponents() {
        context=this;
        tvNumber=findViewById(R.id.tvNumber);
        tvResult=findViewById(R.id.tvResult);
        bNumber=findViewById(R.id.bNumber);
        bCheck=findViewById(R.id.bCheck);
        speechRecognizer = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String[] results = result.getData().
                                getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).
                                        toArray(new String[0]);
                        String spokenText = results[0];
                        onSpeechResult(spokenText);
                    }
                });
    }
}