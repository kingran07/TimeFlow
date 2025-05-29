package danan.ran.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.Manifest;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login_Page extends AppCompatActivity {
Button Login_btn , CreateAccount_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        CreateAccount_btn = findViewById(R.id.newAccount_btn);

        CreateAccount_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Page.this, Account_Plan.class);
                startActivity(intent);
            }
        });

    }
}