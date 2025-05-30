package danan.ran.schoolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.Toast;


public class ProfessionalAdapter extends RecyclerView.Adapter<ProfessionalAdapter.ViewHolder> {

    private Context context;
    private List<User_Proffesional> professionalList;

    public ProfessionalAdapter(Context context, List<User_Proffesional> professionalList) {
        this.context = context;
        this.professionalList = professionalList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewProfession, textViewLocation;
        Button buttonBook;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewProfession = itemView.findViewById(R.id.textViewProfession);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            buttonBook = itemView.findViewById(R.id.buttonBook);
        }
    }

    @NonNull
    @Override
    public ProfessionalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.professional_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessionalAdapter.ViewHolder holder, int position) {
        User_Proffesional user = professionalList.get(position);

        holder.textViewName.setText(user.getFullName());
        holder.textViewProfession.setText(user.getProfession());
        //holder.textViewLocation.setText("📍 " + user.getLocation());

        holder.buttonBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Booking " + user.getFullName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return professionalList.size();
    }
}

