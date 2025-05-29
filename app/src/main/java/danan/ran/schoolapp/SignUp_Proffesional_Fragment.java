package danan.ran.schoolapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp_Proffesional_Fragment extends Fragment {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextPhone;
    private EditText editTextBusinessName, editTextYearsOfExperience;
    private AutoCompleteTextView autoCompleteBusinessType;
    private Button buttonSignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public SignUp_Proffesional_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_proffesional, container, false);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize Views
        editTextFullName = view.findViewById(R.id.editText_name);
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = view.findViewById(R.id.editTextTextPassword2);
        editTextPhone = view.findViewById(R.id.editText_Phone_Number);
        editTextBusinessName = view.findViewById(R.id.editText_businessName);
        editTextYearsOfExperience = view.findViewById(R.id.editText_experience);
        autoCompleteBusinessType = view.findViewById(R.id.autoComplete_businessType);
        buttonSignUp = view.findViewById(R.id.button4);

        // Set up AutoCompleteTextView
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.business_types,
                android.R.layout.simple_dropdown_item_1line
        );
        autoCompleteBusinessType.setAdapter(adapter);

        // Set up button click (no lambda)
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
        String fullName = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String businessName = editTextBusinessName.getText().toString().trim();
        String businessType = autoCompleteBusinessType.getText().toString().trim();
        String yearsStr = editTextYearsOfExperience.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(businessName)
                || TextUtils.isEmpty(businessType) || TextUtils.isEmpty(yearsStr)) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int yearsOfExperience;
        try {
            yearsOfExperience = Integer.parseInt(yearsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid number for years of experience", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register with Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Save to Firestore
                            String userId = mAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullName", fullName);
                            user.put("email", email);
                            user.put("phone", phone);
                            user.put("businessName", businessName);
                            user.put("businessType", businessType);
                            user.put("yearsOfExperience", yearsOfExperience);
                            user.put("accountType", "professional");

                            db.collection("users").document(userId)
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> firestoreTask) {
                                            if (firestoreTask.isSuccessful()) {
                                                Toast.makeText(getContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();
                                                requireActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.Login_SignUp_Fragment_Container, new SignIn_Fragment()) // Make sure this is the correct container ID
                                                        .addToBackStack(null)
                                                        .commit();
                                            } else {
                                                Toast.makeText(getContext(), "Failed to save data: " + firestoreTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
