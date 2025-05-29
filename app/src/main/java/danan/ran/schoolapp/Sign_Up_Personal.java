package danan.ran.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Sign_Up_Personal extends AppCompatActivity {
TextView HaveAccount_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_personal);

        HaveAccount_tv = findViewById(R.id.textView_alreadyHaveAccount);

        HaveAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign_Up_Personal.this,   Login_Page.class);
                startActivity(intent);
            }
        });
    }
}