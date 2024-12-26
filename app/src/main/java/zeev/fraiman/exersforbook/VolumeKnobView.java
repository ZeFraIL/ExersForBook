package zeev.fraiman.exersforbook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class VolumeKnobView extends View {

    private float angle = 0; // Угол поворота
    private int minValue = 0, maxValue = 100;
    private OnVolumeChangeListener listener;

    // Конструктор для создания через код
    public VolumeKnobView(Context context) {
        super(context);
        init();
    }

    // Конструктор для создания через XML (нужен для работы в layout-файлах)
    public VolumeKnobView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // Этот конструктор также может понадобиться, если ты используешь темы стилей
    public VolumeKnobView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        // Вычисляем угол поворота
                        float x = event.getX() - (getWidth() / 2f);
                        float y = event.getY() - (getHeight() / 2f);
                        angle = (float) Math.toDegrees(Math.atan2(y, x));

                        // Преобразуем угол в значение (громкость)
                        int volume = calculateVolumeFromAngle(angle);

                        // Оповещаем listener о изменении
                        if (listener != null) {
                            listener.onVolumeChanged(volume);
                        }
                        invalidate(); // Перерисовка элемента
                        return true;
                }
                return false;
            }
        });
    }

    private int calculateVolumeFromAngle(float angle) {
        // Переводим угол в диапазон значений от minValue до maxValue
        float normalizedAngle = (angle + 360) % 360; // Угол от 0 до 360
        return (int) ((normalizedAngle / 360) * (maxValue - minValue)) + minValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Рисуем ручку и отметку положения
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        // Рисуем круг
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 2f - 10, paint);

        // Рисуем отметку положения
        float markerX = (float) (getWidth() / 2 + (getWidth() / 2 - 20) * Math.cos(Math.toRadians(angle)));
        float markerY = (float) (getHeight() / 2 + (getHeight() / 2 - 20) * Math.sin(Math.toRadians(angle)));
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        canvas.drawLine(getWidth() / 2, getHeight() / 2, markerX, markerY, paint);
    }

    public void setOnVolumeChangeListener(OnVolumeChangeListener listener) {
        this.listener = listener;
    }

    public interface OnVolumeChangeListener {
        void onVolumeChanged(int volume);
    }

    // Метод для установки минимальных и максимальных значений
    public void setMinMaxValues(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
