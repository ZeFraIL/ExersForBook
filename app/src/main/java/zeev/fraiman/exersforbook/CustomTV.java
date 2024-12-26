package zeev.fraiman.exersforbook;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomTV extends AppCompatActivity {

    Context context;
    TextView tv1, tv2;
    String fullText="";
    SeekBar sbTextSize;
    Button bChangeColor, bShadow, bBlink, bPrev, bNext;
    private final long totalTime=10000;
    private final long interval=100;
    private final long radius=60;
    private int startIndex = 0; // Начало текущего фрагмента
    private int numCharsPerPage; // Количество символов, которое помещается на TextView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_tv);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initComponents();

        bChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor();
            }
        });

        bShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShadow();
            }
        });

        bBlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBlink();
            }
        });

        sbTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                numCharsPerPage = getNumCharsPerPage();
                Toast.makeText(context, ""+numCharsPerPage, Toast.LENGTH_SHORT).show();
                tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15+progress*3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tv2.post(new Runnable() {
            @Override
            public void run() {
                /*numCharsPerPage = getNumCharsPerPage();
                Toast.makeText(context, ""+numCharsPerPage, Toast.LENGTH_SHORT).show();
                showText();*/
                int width = tv2.getWidth();
                Paint paint = tv2.getPaint();

                int numCharsPerLine = paint.breakText(fullText, true, width, null);

                int lineHeight = tv2.getLineHeight();
                int tv2Height = tv2.getHeight();
                int numLines = tv2Height / lineHeight;

                int totalChars = numCharsPerLine * numLines;

                Toast.makeText(getApplicationContext(), "Number of characters: " + totalChars, Toast.LENGTH_SHORT).show();
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startIndex + numCharsPerPage < fullText.length()) {
                    startIndex += numCharsPerPage;
                    showText();
                }
            }
        });

        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startIndex - numCharsPerPage >= 0) {
                    startIndex -= numCharsPerPage;
                    showText();
                }
            }
        });
    }

    private void initComponents() {
        context=this;
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        fullText=getString(R.string.forTV2);
        tv2.setText(fullText);
        sbTextSize=findViewById(R.id.sbTextSize);
        sbTextSize.setMax(10);
        bChangeColor=findViewById(R.id.bChangeColor);
        bShadow=findViewById(R.id.bShadow);
        bBlink=findViewById(R.id.bBlink);
        bPrev=findViewById(R.id.bPrev);
        bNext=findViewById(R.id.bNext);
    }

    private void showText() {
        int endIndex = Math.min(startIndex + numCharsPerPage, fullText.length());
        tv2.setText(fullText.substring(startIndex, endIndex));
    }

    private int getNumCharsPerPage() {
        int width = tv2.getWidth();
        Paint paint = tv2.getPaint();
        String sampleText = fullText.substring(0, Math.min(1000, fullText.length())); // Пример небольшой части текста
        return paint.breakText(sampleText, true, width, null);
    }

    private void changeColor()  {
        float startHue=0f;
        float endHue=270f;
        float saturation=1f;
        float value=1f;

        long duration=10000;
        long interval=100;

        new CountDownTimer(duration, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                float t=(float) (duration-millisUntilFinished)/duration;
                float currentHue=startHue+(endHue-startHue)*t;
                float[] color_now=new float[]{currentHue,saturation,value};
                tv1.setTextColor(Color.HSVToColor(color_now));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    private void goShadow()  {
        new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                float elapsedTime=(totalTime-millisUntilFinished)/(float)totalTime;
                float angle=(float) (2*Math.PI*elapsedTime);
                float dx=(float) (radius*Math.cos(angle));
                float dy=(float) (radius*Math.sin(angle));
                tv1.setShadowLayer(1,dx,dy,0xFFF44336);
            }

            @Override
            public void onFinish() {
                tv1.setShadowLayer(0,0,0,0xFFF44336);
            }
        }.start();
    }

    private void goBlink()  {
        new CountDownTimer(12000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv1.setVisibility(tv1.getVisibility()== View.VISIBLE ? View.INVISIBLE:View.VISIBLE);
            }

            @Override
            public void onFinish() {
                tv1.setVisibility(View.VISIBLE);
            }
        }.start();
    }
}