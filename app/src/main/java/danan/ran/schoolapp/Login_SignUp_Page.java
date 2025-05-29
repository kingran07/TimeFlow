package danan.ran.schoolapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login_SignUp_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_sign_up_page);

        if (savedInstanceState == null) { // When the screen loads for the first time, show the SignInFragment in the fragment container.
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Login_SignUp_Fragment_Container, new SignIn_Fragment())
                    .commit();
        }

    }
}