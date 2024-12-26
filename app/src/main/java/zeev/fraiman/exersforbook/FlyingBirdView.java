package zeev.fraiman.exersforbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class FlyingBirdView extends View {
    private Bitmap[] birdFrames;
    private int currentFrame = 0;
    private int birdX;
    private int birdY;
    private int screenHeight;
    private Handler handler = new Handler();
    private Random random = new Random();

    public FlyingBirdView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        birdFrames = new Bitmap[]{
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_001),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_002),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_003),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_004),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_005),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_006),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_007),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_008),
                BitmapFactory.decodeResource(getResources(), R.drawable.image_part_009)
        };
        birdX = getWidth() / 2 - 50;
        birdY = getHeight() - 100;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (birdFrames != null) {
            canvas.drawBitmap(birdFrames[currentFrame], birdX, birdY, null);
            currentFrame = (currentFrame + 1) % birdFrames.length;
        }
        handler.postDelayed(this::invalidate, 500);
    }

    public void flyUp() {
        birdY = getHeight() - 100;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (birdY > 0) {
                    birdY -= 10;
                    birdX += random.nextInt(200) - 100;
                    if (birdX < 0) birdX = 0;
                    if (birdX > getWidth() - 100) birdX = getWidth() - 100;
                    invalidate();
                    handler.postDelayed(this, 500);
                }
            }
        });
    }
}
