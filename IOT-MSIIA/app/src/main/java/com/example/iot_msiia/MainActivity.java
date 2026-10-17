package com.example.iot_msiia;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SolutionAdapter adapter;
    private List<Solution> solutionList;
    private FirebaseFirestore db;
    private ListenerRegistration listenerRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // تهيئة RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // تهيئة القائمة
        solutionList = new ArrayList<>();
        adapter = new SolutionAdapter(solutionList, solution -> {
            Log.d("Checkbox Changed", "Solution: " + solution.getName() + " is checked: " + solution.isChecked());
        });

        recyclerView.setAdapter(adapter);

        // الاتصال بقاعدة البيانات
        db = FirebaseFirestore.getInstance();

        // جلب البيانات من Firestore
        getActiveSolutions();

        // ضبط القائمة السفلية
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_solutions) {
                return true;
            } else if (id == R.id.nav_notifications) {
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }

    // جلب البيانات من مستند IV1
    private void getActiveSolutions() {
        db.collection("ActiveSolutions")
                .document("IV1")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        Boolean checked = documentSnapshot.getBoolean("checked");

                        if (name != null && checked != null) {
                            solutionList.add(new Solution(name, 0, checked, false));
                            adapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(e ->
                        Log.e("FirestoreError", "Error fetching data", e)
                );
    }

    // تأكد من إلغاء الاشتراك في المستمع عند الخروج من الـ Activity
    @Override
    protected void onStop() {
        super.onStop();
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }
}
