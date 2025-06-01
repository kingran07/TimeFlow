package danan.ran.schoolapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    FirebaseFirestore db;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Should contain FrameLayout with id activity_profile_fragment_container

        // Step 1: Get user ID from intent
        uid = getIntent().getStringExtra("userId");

        if (uid == null) {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db = FirebaseFirestore.getInstance();

        // Step 2: Fetch user type from Firestore
        db.collection("users").document(uid).get()
                .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Boolean isProfessional = documentSnapshot.getBoolean("professional");

                            if (isProfessional == null) {
                                isProfessional = false;
                            }

                            Fragment fragment;
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", uid);

                            if (isProfessional) {
                                fragment = new Profile_Professinal_Fragment();
                            } else {
                                fragment = new Profile_Personal_Fragment();
                            }

                             fragment.setArguments(bundle);

                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.activity_profile_fragment_container, fragment)
                                    .commit();
                        } else {
                            Toast.makeText(ProfileActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
