package danan.ran.schoolapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

public class SignUp_Personal_Fragment extends Fragment {

    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    private Button buttonSignUp;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private CollectionReference usersCollection;

    public SignUp_Personal_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_personal, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usersCollection = db.collection("users");

        editTextName = view.findViewById(R.id.editText_name);
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = view.findViewById(R.id.editTextTextPassword2);
        editTextPhone = view.findViewById(R.id.editText_Phone_Number);
        buttonSignUp = view.findViewById(R.id.button4);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        TextView alreadyHaveAccountButton = view.findViewById(R.id.textView_alreadyHaveAccount);

        alreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Login_SignUp_Fragment_Container, new SignIn_Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void registerUser() {
        String fullName = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(phone)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser == null) {
                                Toast.makeText(getContext(), "User is null after registration.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            String uid = firebaseUser.getUid();

                            User_Personal user = new User_Personal(fullName, email, phone, false);

                            usersCollection.document(uid).set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                                            requireActivity().getSupportFragmentManager()
                                                    .beginTransaction()
                                                    .replace(R.id.Login_SignUp_Fragment_Container, new SignIn_Fragment())
                                                    .addToBackStack(null)
                                                    .commit();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), "Failed to save user data: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
