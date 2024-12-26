package zeev.fraiman.exersforbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Fireworks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fireworks);

        Button fireworkButton = findViewById(R.id.fireworkButton);
        FireworkView fireworkView = findViewById(R.id.fireworkView);

        fireworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireworkView.startFirework();
            }
        });

    }
}