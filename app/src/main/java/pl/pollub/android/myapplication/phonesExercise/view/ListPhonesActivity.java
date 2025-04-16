package pl.pollub.android.myapplication.phonesExercise.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import pl.pollub.android.myapplication.R;
import pl.pollub.android.myapplication.phonesExercise.data.PhoneEntity;

public class ListPhonesActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PhoneViewModel phoneViewModel;
    private PhoneListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_phones);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle extras = intent.getExtras();
                        if (extras != null) {
                            if (extras.containsKey("phone")) {
                                PhoneEntity phone = extras.getParcelable("phone");
                                phoneViewModel.addPhone(phone);
                            }
                        }
                    }
                });

        setUpRecView();
        setUpPhoneVM();
        setUpFab();
    }
    private void setUpFab() {
        FloatingActionButton fabAddPhone = findViewById(R.id.fabAddPhone);
        fabAddPhone.setOnClickListener(view -> mainFabClicked());
    }

    private void setUpRecView(){
        RecyclerView recyclerView = findViewById(R.id.phones_rec_view);
        adapter = new PhoneListAdapter(this, phone -> {
            Intent intent = new Intent(ListPhonesActivity.this, AddPhoneActivity.class);
            intent.putExtra("phone", phone);
            activityResultLauncher.launch(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                phoneViewModel.deleteOne(adapter.getItemAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setUpPhoneVM(){
        phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        phoneViewModel.getAllPhones().observe(this, phones -> adapter.setPhoneList(phones));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.phones_menu, menu);
        setTitle("Phones DB");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.phones_clear_all_data) {
            Toast.makeText(this, "Clearing all data", Toast.LENGTH_SHORT).show();
            phoneViewModel.deleteAll();
            return true;
        } else if (id == R.id.phones_reset_db) {
            phoneViewModel.resetDatabase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void mainFabClicked() {
        Intent intent = new Intent(ListPhonesActivity.this, AddPhoneActivity.class);
        activityResultLauncher.launch(intent);
    }

}