package pl.pollub.android.myapplication.downloadExercise;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.pollub.android.myapplication.R;

public class DownloadActivity extends AppCompatActivity {

    EditText addressET;
    TextView fileSizeInfoTV, fileTypeInfoTV;
    Button getInfoButton;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Download file");
        assignViews();
        assignListeners();
    }

    private void assignListeners() {
        getInfoButton.setOnClickListener(view -> {
            String urlString = addressET.getText().toString();
            fetchFileInfoFrom(urlString);
        });
    }

    private void assignViews(){
        addressET = findViewById(R.id.addressET);
        getInfoButton = findViewById(R.id.getInfoButton);
        fileSizeInfoTV = findViewById(R.id.fileSizeInfoTV);
        fileTypeInfoTV = findViewById(R.id.fileTypeInfoTV);
    }

    private void fetchFileInfoFrom(String urlString) {
        executor.execute(() -> {
            String stringTypeAndLength;
            try {
                stringTypeAndLength = getFileTypeAndLengthFrom(urlString);

                String finalResultInfo = stringTypeAndLength;
                handler.post(() -> {
                    String[] fileData = finalResultInfo.split(" ", 2);
                    fileTypeInfoTV.setText(fileData[0]);
                    fileSizeInfoTV.setText(fileData[1]);
                });


            } catch (Exception e) {
                handler.post(() -> {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    fileTypeInfoTV.setText("---");
                    fileSizeInfoTV.setText("---");
                });

            }
        });
    }

    private String getFileTypeAndLengthFrom(String urlString) throws Exception {
        String resultInfo;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("Błąd połączenia: " + responseCode);
            } else {
                resultInfo = connection.getContentType() + " " + connection.getContentLength();
            }

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return resultInfo;
    }
}