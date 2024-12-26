package zeev.fraiman.exersforbook;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireworkView extends View {

    private Paint paint;
    private List<Particle> particles;
    private Random random;
    private boolean isAnimating = false;
    private long startTime;
    private static final int FIREWORK_DURATION = 4000; // 4 секунды

    public FireworkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        particles = new ArrayList<>();
        random = new Random();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimating) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - startTime > FIREWORK_DURATION) {
                isAnimating = false;
                particles.clear();
            } else {
                updateParticles();
                for (Particle particle : particles) {
                    paint.setColor(particle.color);
                    canvas.drawCircle(particle.x, particle.y, particle.size, paint);
                }
                invalidate();
            }
        }
    }

    public void startFirework() {
        isAnimating = true;
        startTime = System.currentTimeMillis();
        createFirework();
        invalidate();
    }

    private void createFirework() {
        particles.clear();
        int[] rainbowColors = {
                Color.RED, Color.CYAN, Color.YELLOW, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA
        };

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (int i = 0; i < 100; i++) {
            int color = rainbowColors[random.nextInt(rainbowColors.length)];
            float speedX = random.nextFloat() * 10 - 5;
            float speedY = random.nextFloat() * 10 - 5;
            particles.add(new Particle(centerX, centerY, speedX, speedY, color,
                    random.nextInt(10) + 5));
        }
    }

    private void updateParticles() {
        for (Particle particle : particles) {
            particle.x += particle.speedX;
            particle.y += particle.speedY;
            particle.size -= 0.1f;
        }
    }

    private static class Particle {
        float x, y, speedX, speedY, size;
        int color;

        Particle(float x, float y, float speedX, float speedY, int color, float size) {
            this.x = x;
            this.y = y;
            this.speedX = speedX;
            this.speedY = speedY;
            this.color = color;
            this.size = size;
        }
    }
}
