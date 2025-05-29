package danan.ran.schoolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class Account_Type_Selection_Fragment extends Fragment {

    public Account_Type_Selection_Fragment()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_type_selection, container, false);

        Button personalBtn = view.findViewById(R.id.personal_account_btn);
        Button professionalBtn = view.findViewById(R.id.professional_account_btn);

        personalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Login_SignUp_Fragment_Container, new SignUp_Personal_Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        professionalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.Login_SignUp_Fragment_Container, new SignUp_Proffesional_Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }
}




