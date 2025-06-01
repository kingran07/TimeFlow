package danan.ran.schoolapp;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class PhoneAuthCallbackHelper extends PhoneAuthProvider.OnVerificationStateChangedCallbacks {

    private final Activity activity;
    private final String phoneNumber;
    private final String userId;

    public PhoneAuthCallbackHelper(Activity activity, String phoneNumber, String userId) {
        this.activity = activity;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    @Override
    public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
        Toast.makeText(activity, "Verification completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationFailed(@NonNull FirebaseException e) {
        Toast.makeText(activity, "Verification failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        Log.e("PhoneAuth", "Verification failed", e);
    }

    @Override
    public void onCodeSent(@NonNull String verificationId,
                           @NonNull PhoneAuthProvider.ForceResendingToken token) {
        Toast.makeText(activity, "Verification code sent to " + phoneNumber, Toast.LENGTH_SHORT).show();
    }
}
