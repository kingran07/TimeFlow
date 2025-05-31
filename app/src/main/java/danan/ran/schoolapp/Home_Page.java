package danan.ran.schoolapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    List<User_Proffesional> filteredList = new ArrayList<>();

    Button filterButton, searchButton;
    EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.activity.EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewProfessionals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filterButton = (Button) findViewById(R.id.buttonFilter);
        searchButton = (Button) findViewById(R.id.button);
        searchEditText = (EditText) findViewById(R.id.editTextText);

        adapter = new ProfessionalAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);

        fetchProfessionalsFromFirestore();

        filterButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                showFilterDialog();
            }
        });

        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                applySearchFilter();
            }
        });
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
                        filteredList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            User_Proffesional user = document.toObject(User_Proffesional.class);
                            professionalList.add(user);
                        }

                        filteredList.addAll(professionalList);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void showFilterDialog() {
        Resources res = getResources();
        final String[] professions = res.getStringArray(R.array.business_types);


        final String[] options = new String[professions.length + 1];
        options[0] = "All";
        System.arraycopy(professions, 0, options, 1, professions.length);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Profession to Filter");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String selectedProfession = options[which];
                applyProfessionFilter(selectedProfession);
            }
        });
        builder.show();
    }

    private void applyProfessionFilter(String profession) {
        filteredList.clear();

        if (profession.equals("All")) {
            filteredList.addAll(professionalList);
        } else {
            for (User_Proffesional p : professionalList) {
                if (p.getProfession() != null && p.getProfession().equalsIgnoreCase(profession)) {
                    filteredList.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Filtered by: " + profession, Toast.LENGTH_SHORT).show();
    }

    private void applySearchFilter() {
        String query = searchEditText.getText().toString().trim().toLowerCase();

        if (query.isEmpty()) {

            filteredList.clear();
            filteredList.addAll(professionalList);
        } else {
            List<User_Proffesional> searchFiltered = new ArrayList<>();
            for (User_Proffesional p : filteredList) {
                if (p.getFullName() != null && p.getFullName().toLowerCase().contains(query)) {
                    searchFiltered.add(p);
                }
            }
            filteredList.clear();
            filteredList.addAll(searchFiltered);
        }
        adapter.notifyDataSetChanged();
    }
}
