package zeev.fraiman.exersforbook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WaveView extends View {
    private Paint paint;
    private float[] waveData;

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        waveData = new float[100];
    }

    public void updateWave(float[] newWaveData) {
        waveData = newWaveData;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
    }

    private void drawWave(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        float midY = height / 2;
        for (int i = 1; i < waveData.length; i++) {
            float startX = (width / waveData.length) * (i - 1);
            float startY = midY - waveData[i - 1] * midY;
            float stopX = (width / waveData.length) * i;
            float stopY = midY - waveData[i] * midY;

            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }
}
