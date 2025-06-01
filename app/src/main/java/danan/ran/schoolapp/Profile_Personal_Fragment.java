package danan.ran.schoolapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class Profile_Personal_Fragment extends Fragment {

    private TextView tvFullName, tvEmail, tvPhone;
    private Button btnChangeName, btnChangeEmail, btnChangePhone, btnChangePassword, btnBackToHome;
    private ImageView imgAppLogo;
    private String userId;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_profile, container, false);

        tvFullName = view.findViewById(R.id.tvFullName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);

        btnChangeName = view.findViewById(R.id.btnChangeName);
        btnChangeEmail = view.findViewById(R.id.btnChangeEmail);
        btnChangePhone = view.findViewById(R.id.btnChangePhone);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnBackToHome = view.findViewById(R.id.btnBackToHome);

        imgAppLogo = view.findViewById(R.id.imgAppLogo);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Bundle args = getArguments();
        if (args != null) {
            userId = args.getString("userId");
        }

        if (userId != null) {
            loadUserInfo();
        }

        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });

        btnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("fullName", "Change Full Name");
            }
        });

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("email", "Change Email");
            }
        });

        btnChangePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog("phone", "Change Phone");
            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "A verification code will be sent to your phone.", Toast.LENGTH_LONG).show();
                sendPhoneVerification();
            }
        });

        return view;
    }

    private void loadUserInfo() {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            tvFullName.setText(documentSnapshot.getString("fullName"));
                            tvEmail.setText(documentSnapshot.getString("email"));
                            tvPhone.setText(documentSnapshot.getString("phone"));
                        }
                    }
                });
    }

    private void showEditDialog(final String field, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String newValue = input.getText().toString();
                db.collection("users").document(userId)
                        .update(field, newValue)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), field + " updated", Toast.LENGTH_SHORT).show();
                                loadUserInfo();
                            }
                        });
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void sendPhoneVerification() {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String phoneNumber = documentSnapshot.getString("phone");
                        if (phoneNumber != null && !phoneNumber.isEmpty()) {
                            PhoneAuthCallbackHelper callbacks = new PhoneAuthCallbackHelper(
                                    requireActivity(),
                                    phoneNumber,
                                    userId
                            );

                            PhoneAuthOptions options =
                                    PhoneAuthOptions.newBuilder(mAuth)
                                            .setPhoneNumber(phoneNumber)
                                            .setTimeout(60L, TimeUnit.SECONDS)
                                            .setActivity(requireActivity())
                                            .setCallbacks(callbacks)
                                            .build();

                            PhoneAuthProvider.verifyPhoneNumber(options);
                        } else {
                            Toast.makeText(getContext(), "No phone number found for verification", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
