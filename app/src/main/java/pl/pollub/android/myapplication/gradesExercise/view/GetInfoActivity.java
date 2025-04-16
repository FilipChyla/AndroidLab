package pl.pollub.android.myapplication.gradesExercise.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import pl.pollub.android.myapplication.R;

public class GetInfoActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private float meanGradeToSave = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setTitle("Podaj informacje");

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        Bundle extras = intent.getExtras();
                        if (extras != null) {
                            if (extras.containsKey("gradeMean")) {
                                float gradeMean = intent.getExtras().getFloat("gradeMean");
                                Toast.makeText(this, "Srednia wynosi: " + gradeMean, Toast.LENGTH_SHORT).show();

                                meanGradeToSave = gradeMean;
                                buildExitButton(gradeMean);
                            }
                        }
                    }
                });

        Button button = findViewById(R.id.buttonGrades);
        button.setOnClickListener(view -> startSecondActivity());
        button.setVisibility(View.GONE);

        View.OnFocusChangeListener focusChangeListenerIfEmpty = (v, hasFocus) -> {
            if (!hasFocus) {
                isFieldEmpty(v, true);
                showButtonIfCorrectInputs();
            }
        };

        View.OnFocusChangeListener focusChangeListenerIfNumber = (v, hasFocus) -> {
            if (!hasFocus) {
                isNumberCorrect(v, true);
                showButtonIfCorrectInputs();
            }
        };
        findViewById(R.id.editTextName).setOnFocusChangeListener(focusChangeListenerIfEmpty);
        findViewById(R.id.editTextSurname).setOnFocusChangeListener(focusChangeListenerIfEmpty);
        findViewById(R.id.editTextGradeCount).setOnFocusChangeListener(focusChangeListenerIfNumber);
    }

    private void buildExitButton(float gradeMean) {
        Button endButton = findViewById(R.id.buttonFinal);
        if (gradeMean >= 3) {
            endButton.setText(R.string.superSmile);
            endButton.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("Gratulacje!")
                    .setMessage("Otrzymujesz zaliczenie!")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> finishAffinity())
                    .show());
        } else {
            endButton.setText(R.string.tymRazemNiePoszlo);
            endButton.setOnClickListener(v -> new AlertDialog.Builder(this)
                    .setTitle("Niestety")
                    .setMessage("Wysyłam podanie o zaliczenie warunkowe")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> finishAffinity())
                    .show());
        }
        endButton.setVisibility(View.VISIBLE);
    }

    private void startSecondActivity() {
        Intent intent = new Intent(GetInfoActivity.this, GetGradesActivity.class);
        int gradeCount = Integer.parseInt(((EditText) findViewById(R.id.editTextGradeCount)).getText().toString());
        intent.putExtra("gradeCount", gradeCount);
        activityResultLauncher.launch(intent);
    }

    private boolean isNumberCorrect(View view, boolean showToast) {
        EditText editText = (EditText) view;
        String stringGradeCount = editText.getText().toString();
        if (stringGradeCount.isEmpty()) {
            if (showToast) {
                Toast.makeText(getApplicationContext(), "Pole nie może być puste!", Toast.LENGTH_SHORT).show();
                editText.setError("Pole nie może być puste");
            }
            return false;
        } else {
            if ((Integer.parseInt(stringGradeCount) > 15 || Integer.parseInt(stringGradeCount) < 5)) {
                if (showToast) {
                    Toast.makeText(getApplicationContext(), "Niepoprwna ilość ocen!", Toast.LENGTH_SHORT).show();
                    editText.setError("Niepoprwna ilość ocen");
                }
                return false;
            }
        }
        return true;
    }

    private boolean isFieldEmpty(View view, boolean showToast) {
        EditText editText = (EditText) view;
        if (editText.getText().toString().isEmpty()) {
            if (showToast) {
                Toast.makeText(getApplicationContext(), "Pole nie może być puste!", Toast.LENGTH_SHORT).show();
                editText.setError("Pole nie może być puste");
            }
            return true;
        }
        return false;
    }

    private void showButtonIfCorrectInputs() {
        EditText etName = findViewById(R.id.editTextName);
        EditText etSurname = findViewById(R.id.editTextSurname);
        EditText etGradeCount = findViewById(R.id.editTextGradeCount);
        Button button = findViewById(R.id.buttonGrades);

        if (!isFieldEmpty(etName, false) && !isFieldEmpty(etSurname, false) && isNumberCorrect(etGradeCount, false)) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Button buttonGrades = findViewById(R.id.buttonGrades);
        outState.putInt("ButtonGradeState", buttonGrades.getVisibility());

        float gradeSave = meanGradeToSave;
        outState.putFloat("grade", gradeSave);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Button buttonGrades = findViewById(R.id.buttonGrades);
        buttonGrades.setVisibility(savedInstanceState.getInt("ButtonGradeState"));

        float gradeSaved = savedInstanceState.getFloat("grade");

        if(gradeSaved > 0){
            buildExitButton(gradeSaved);
        }
    }
}