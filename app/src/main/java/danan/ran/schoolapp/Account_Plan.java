package danan.ran.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Account_Plan extends AppCompatActivity {
Button CreateProffesional_btn , CreatePersonal_btn;
TextView HaveAccount_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_plan);

        CreateProffesional_btn = findViewById(R.id.Proffesional_btn);
        CreatePersonal_btn = findViewById(R.id.Personal_btn);
        HaveAccount_tv = findViewById(R.id.textView_alreadyHaveAccount);

        CreateProffesional_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Plan.this, Sign_Up_Proffesional.class);
                startActivity(intent);
            }
        }); 
        CreatePersonal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Plan.this, Sign_Up_Personal.class);
                startActivity(intent);
            }
        });
        HaveAccount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account_Plan.this,   Login_Page.class);
                startActivity(intent);
            }
        });



    }
}