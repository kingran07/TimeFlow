package danan.ran.schoolapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Home_Page extends AppCompatActivity {

    RecyclerView recyclerView;
    ProfessionalAdapter adapter;
    List<User_Proffesional> professionalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);


        String uid = getIntent().getStringExtra("userId");


        recyclerView = findViewById(R.id.recyclerViewProfessionals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new ProfessionalAdapter(this, professionalList);
        recyclerView.setAdapter(adapter);


        fetchProfessionalsFromFirestore();
    }

    private void fetchProfessionalsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("professional", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        professionalList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            User_Proffesional user = document.toObject(User_Proffesional.class);
                            professionalList.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
