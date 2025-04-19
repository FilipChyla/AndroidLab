package pl.pollub.android.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pl.pollub.android.myapplication.downloadExercise.DownloadActivity;
import pl.pollub.android.myapplication.gradesExercise.view.GetInfoActivity;
import pl.pollub.android.myapplication.phonesExercise.view.ListPhonesActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Android laboratoria");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_zad1){
            startActivity(new Intent(this, GetInfoActivity.class));
        } else if (item.getItemId() == R.id.menu_zad2) {
            startActivity(new Intent(this, ListPhonesActivity.class));
        } else if (item.getItemId() == R.id.menu_zad3) {
            startActivity(new Intent(this, DownloadActivity.class));
        }
        return true;
    }
}