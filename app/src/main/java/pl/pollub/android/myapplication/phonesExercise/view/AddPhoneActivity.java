package pl.pollub.android.myapplication.phonesExercise.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

import pl.pollub.android.myapplication.R;
import pl.pollub.android.myapplication.phonesExercise.data.PhoneEntity;

public class AddPhoneActivity extends AppCompatActivity {
    private EditText manufacturerET, modelET, versionET, websiteET;
    private Button saveButton, websiteButton, cancelButton;
    private PhoneEntity phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        assignViews();

        try {
            if (Objects.requireNonNull(getIntent().getExtras()).containsKey("phone")){
                phone = getIntent().getExtras().getParcelable("phone");
                setUpToUpdate();
            }else {
                setTitle("Add new phone");
            }
        } catch (Exception e) {
            setTitle("Add new phone");
        }

        assignEditTextListeners();
        assignButtonListeners();
    }

    private void setUpToUpdate() {
        manufacturerET.setText(phone.getManufacturer());
        modelET.setText(phone.getModel());
        versionET.setText(phone.getAndroidVersion());
        websiteET.setText(phone.getSite());
        setTitle("Edit phone");
    }

    private void assignViews(){
        manufacturerET = findViewById(R.id.editTextManufacturer);
        modelET = findViewById(R.id.editTextModel);
        versionET = findViewById(R.id.editTextVersion);
        websiteET = findViewById(R.id.editTextWebsite);
        saveButton = findViewById(R.id.saveButton);
        websiteButton = findViewById(R.id.websiteButton);
        cancelButton = findViewById(R.id.cancelButton);
    }
    private void assignEditTextListeners() {
        View.OnFocusChangeListener focusChangeListenerIfEmpty = (v, hasFocus) -> {
            if (!hasFocus) {
                isFieldEmpty(v, true);
            }
        };

        manufacturerET.setOnFocusChangeListener(focusChangeListenerIfEmpty);
        modelET.setOnFocusChangeListener(focusChangeListenerIfEmpty);
        versionET.setOnFocusChangeListener(focusChangeListenerIfEmpty);
        websiteET.setOnFocusChangeListener(focusChangeListenerIfEmpty);
    }
    private void assignButtonListeners() {
        saveButton.setOnClickListener((v) -> {
            if (isFieldEmpty(manufacturerET, false) ||
                    isFieldEmpty(modelET, false) ||
                    isFieldEmpty(versionET, false) ||
                    isFieldEmpty(websiteET, false)){
                Toast.makeText(getApplicationContext(), "Pole nie moga byc puste", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent();
                if (phone != null){
                    phone.setManufacturer(manufacturerET.getText().toString());
                    phone.setModel(modelET.getText().toString());
                    phone.setAndroidVersion(versionET.getText().toString());
                    phone.setSite(websiteET.getText().toString());
                }else {
                    phone = new PhoneEntity(manufacturerET.getText().toString(), modelET.getText().toString(), versionET.getText().toString(), websiteET.getText().toString());
                }
                intent.putExtra("phone", phone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        websiteButton.setOnClickListener((v) -> {
            if (websiteET.getText().toString().startsWith("http://") || websiteET.getText().toString().startsWith("https://")){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteET.getText().toString()));
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener((v) -> {
            setResult(RESULT_CANCELED);
            finish();
        });
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
}