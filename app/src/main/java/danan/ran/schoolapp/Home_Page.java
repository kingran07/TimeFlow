package danan.ran.schoolapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Home_Page extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    RecyclerView recyclerView;
    ProfessionalAdapter adapter;
    List<User_Proffesional> professionalList = new ArrayList<>();
    List<User_Proffesional> filteredList = new ArrayList<>();


    Button filterButton;
    EditText searchEditText;
    TextView textViewGreeting, textViewDateTime;
    TextView headerUserName, headerUserEmail;

    Handler timeHandler = new Handler();
    Runnable timeRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        androidx.activity.EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        String uid = getIntent().getStringExtra("userId");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        headerUserName = headerView.findViewById(R.id.headerUserName);
        headerUserEmail = headerView.findViewById(R.id.headerUserEmail);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_settings) {
                    Toast.makeText(Home_Page.this, "Settings clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_appointments) {
                    Toast.makeText(Home_Page.this, "My Appointments clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_profile) {
                    Toast.makeText(Home_Page.this, "Profile clicked", Toast.LENGTH_SHORT).show();
                    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Intent intent = new Intent(Home_Page.this, ProfileActivity.class);
                    intent.putExtra("userId", uid);
                    startActivity(intent);

                } else if (id == R.id.nav_notifications) {
                    Toast.makeText(Home_Page.this, "Notifications clicked", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(Home_Page.this, "Log Out clicked", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        recyclerView = findViewById(R.id.recyclerViewProfessionals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filterButton = findViewById(R.id.buttonFilter);
        searchEditText = findViewById(R.id.editTextSearch);

        textViewGreeting = findViewById(R.id.textViewGreeting);
        textViewDateTime = findViewById(R.id.textViewDateTime);

        adapter = new ProfessionalAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);

        fetchProfessionalsFromFirestore();
        loadUserDataFromFirestore();
        startClockUpdater();

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterByName(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    private void startClockUpdater() {
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                String currentTime = new SimpleDateFormat("EEEE, MMM d, yyyy - HH:mm", Locale.getDefault()).format(new Date());
                textViewDateTime.setText(currentTime);
                timeHandler.postDelayed(this, 1000);
            }
        };
        timeHandler.post(timeRunnable);
    }

    private void loadUserDataFromFirestore() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users").document(currentUserId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        String fullName = document.getString("fullName");
                        String email = document.getString("email");

                        if (fullName != null && !fullName.isEmpty()) {
                            String firstName = fullName.split(" ")[0];
                            textViewGreeting.setText("Hello, " + firstName + " 👋");
                            headerUserName.setText(fullName);
                        } else {
                            textViewGreeting.setText("Hello 👋");
                            headerUserName.setText("User");
                        }

                        if (email != null && !email.isEmpty()) {
                            headerUserEmail.setText(email);
                        }
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

        filterByName(searchEditText.getText().toString().trim());
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Filtered by: " + profession, Toast.LENGTH_SHORT).show();
    }

    private void filterByName(String query) {
        query = query.trim().toLowerCase();
        List<User_Proffesional> searchResults = new ArrayList<>();

        for (User_Proffesional p : filteredList) {
            if (p.getFullName() != null && p.getFullName().toLowerCase().contains(query)) {
                searchResults.add(p);
            }
        }

        adapter.updateList(searchResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeHandler != null && timeRunnable != null) {
            timeHandler.removeCallbacks(timeRunnable);
        }
    }
}