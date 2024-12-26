package zeev.fraiman.exersforbook;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VolumeKnob extends AppCompatActivity {

    private VolumeKnobView volumeKnob;
    private TextView volumeTextView;
    private TextView volumeIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_knob);

        // Инициализируем кастомную ручку и TextView
        volumeKnob = findViewById(R.id.volumeKnob);
        volumeTextView = findViewById(R.id.volumeTextView);
        volumeIndicator = findViewById(R.id.volumeIndicator);

        // Устанавливаем минимальные и максимальные значения громкости
        volumeKnob.setMinMaxValues(0, 100);

        // Устанавливаем слушатель для изменения громкости
        volumeKnob.setOnVolumeChangeListener(new VolumeKnobView.OnVolumeChangeListener() {
            @Override
            public void onVolumeChanged(int volume) {
                // Отображаем значение громкости в TextView
                volumeTextView.setText("Громкость=" + volume);
                ViewGroup.LayoutParams params = volumeIndicator.getLayoutParams();
                int maxWidth = ((ViewGroup) volumeIndicator.getParent()).getWidth(); // Максимальная ширина = ширина родительского элемента
                params.width = (int) (maxWidth * (volume / 100f)); // Ширина изменяется в зависимости от громкости
                volumeIndicator.setLayoutParams(params); // Применяем обновления
            }
        });
    }
}
