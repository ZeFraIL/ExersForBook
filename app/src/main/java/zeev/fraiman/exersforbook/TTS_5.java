package zeev.fraiman.exersforbook;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.Locale;

public class TTS_5 extends AppCompatActivity {

    Context context;
    TextView tv5;
    Button bSpeak5, bSave, bWave;
    private TextToSpeech tts, tts1, tts2;
    String fullText="";
    private String stText = "This is an example of how you can " +
            "show what word is being spoken while reading text";
    private String[] words;
    private SpannableString spannableString;
    private WaveView waveView;
    private boolean isSpeaking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts5);

        initComponents();

        bSpeak5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakText(stText);
            }
        });

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            File internalFile = new File(getFilesDir(),
                                    "speech_output.wav");
                            if (internalFile.exists()) {
                                internalFile.delete();
                            }
                            tts1.synthesizeToFile(fullText, null,
                                    internalFile, "SpeechID");
                        }
                    }
                });
            }
        });

        bWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts2.speak(fullText, TextToSpeech.QUEUE_FLUSH, null, "WithWaveID");
                startWaveAnimation();
            }
        });
    }

    private void initComponents() {
        context=this;
        bSave=findViewById(R.id.bSave);
        bWave=findViewById(R.id.bWave);
        waveView=findViewById(R.id.waveView);
        fullText=getString(R.string.forTV2);
        tv5=findViewById(R.id.tv5);
        bSpeak5=findViewById(R.id.bSpeak5);
        words = stText.split(" ");
        spannableString = new SpannableString(stText);
        tv5.setText(spannableString);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.getDefault());

                    tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {

                        }

                        @Override
                        public void onDone(String utteranceId) {

                        }

                        @Override
                        public void onError(String utteranceId) {

                        }

                        @Override
                        public void onRangeStart(String utteranceId, int start, int end, int frame) {
                            super.onRangeStart(utteranceId, start, end, frame);
                            highlightWord(start, end);
                        }
                    });
                }
            }
        });
        tts2 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    tts2.setLanguage(Locale.getDefault());
                    tts2.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String utteranceId) {
                            isSpeaking = true;
                            startWaveAnimation();
                        }
                        @Override
                        public void onDone(String utteranceId) {
                            isSpeaking = false;
                        }
                        @Override
                        public void onError(String utteranceId) {
                            isSpeaking = false;
                        }
                    });
                }
            }
        });
    }

    private void speakText(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TextID");
    }

    private void highlightWord(int start, int end) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.black)),
                        0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new BackgroundColorSpan(getResources().getColor(android.R.color.transparent)),
                        0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(1.0f),
                        0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(android.R.color.holo_blue_light)),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new BackgroundColorSpan(getResources().getColor(android.R.color.holo_orange_light)),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new RelativeSizeSpan(1.5f),
                        start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                tv5.setText(spannableString);
            }
        });
    }

    private void startWaveAnimation() {
        new Thread(() -> {
            float[] waveData = new float[100];
            while (isSpeaking) {
                for (int i = 0; i < waveData.length; i++) {
                    float amplitude = (float) Math.random();
                    waveData[i] = (float) Math.sin(i + System.currentTimeMillis() * 0.01)
                            * amplitude * 0.5f + 0.5f;
                }
                runOnUiThread(() -> waveView.updateWave(waveData));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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