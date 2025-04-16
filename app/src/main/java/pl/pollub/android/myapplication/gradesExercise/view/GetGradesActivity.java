package pl.pollub.android.myapplication.gradesExercise.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import pl.pollub.android.myapplication.gradesExercise.data.GradeModel;
import pl.pollub.android.myapplication.R;

public class GetGradesActivity extends AppCompatActivity {
    private RecyclerView gradeRecycleView;
    ArrayList<GradeModel> gradeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_grades);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Podaj oceny");
        gradeRecycleView = findViewById(R.id.gradeRecView);

        for (int i = 0; i < Objects.requireNonNull(getIntent().getExtras()).getInt("gradeCount"); i++) {
            gradeList.add(new GradeModel());
        }

        InteractiveTableAdapter adapter = new InteractiveTableAdapter();
        adapter.setGradeList(gradeList);
        adapter.setContext(this);

        gradeRecycleView.setAdapter(adapter);
        gradeRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Button gradesMeanButton = findViewById(R.id.gradesMeanButton);
        gradesMeanButton.setOnClickListener(view -> returnToMain());
    }

    private void returnToMain(){
        Intent intent = new Intent(GetGradesActivity.this, GetInfoActivity.class);
        float gradeSum = 0;
        for (GradeModel grade : gradeList){
            gradeSum += grade.getGrade();
        }
        float gradeMean = gradeSum / gradeList.size();
        intent.putExtra("gradeMean", gradeMean);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("gradeList", gradeList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gradeList = (ArrayList<GradeModel>) savedInstanceState.getSerializable("gradeList");
        InteractiveTableAdapter adapter = new InteractiveTableAdapter();
        adapter.setGradeList(gradeList);
        adapter.setContext(this);

        gradeRecycleView.setAdapter(adapter);
        gradeRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }
}