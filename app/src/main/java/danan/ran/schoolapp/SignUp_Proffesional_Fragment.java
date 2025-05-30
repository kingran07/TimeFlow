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
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp_Proffesional_Fragment extends Fragment {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextPhone;
    private EditText editTextBusinessName, editTextYearsOfExperience;
    private AutoCompleteTextView autoCompleteProfession;
    private Button buttonSignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public SignUp_Proffesional_Fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up_proffesional, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextFullName = view.findViewById(R.id.editText_name);
        editTextEmail = view.findViewById(R.id.editTextTextEmailAddress2);
        editTextPassword = view.findViewById(R.id.editTextTextPassword2);
        editTextPhone = view.findViewById(R.id.editText_Phone_Number);
        editTextBusinessName = view.findViewById(R.id.editText_businessName);
        editTextYearsOfExperience = view.findViewById(R.id.editText_experience);
        autoCompleteProfession = view.findViewById(R.id.autoComplete_profession);
        buttonSignUp = view.findViewById(R.id.button4);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.business_types,
                android.R.layout.simple_dropdown_item_1line
        );
        autoCompleteProfession.setAdapter(adapter);

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
        String profession = autoCompleteProfession.getText().toString().trim();
        String yearsStr = editTextYearsOfExperience.getText().toString().trim();

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(phone) || TextUtils.isEmpty(businessName)
                || TextUtils.isEmpty(profession) || TextUtils.isEmpty(yearsStr)) {
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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();

                            User_Proffesional user = new User_Proffesional(fullName, email, phone, businessName, profession, yearsOfExperience, true);

                            db.collection("users").document(userId)
                                    .set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> firestoreTask) {
                                            if (firestoreTask.isSuccessful()) {
                                                Toast.makeText(getContext(), "Sign up successful!", Toast.LENGTH_SHORT).show();
                                                requireActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.Login_SignUp_Fragment_Container, new SignIn_Fragment())
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
