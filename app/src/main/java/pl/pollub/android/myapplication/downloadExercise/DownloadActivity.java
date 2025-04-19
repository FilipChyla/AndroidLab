package pl.pollub.android.myapplication.downloadExercise;

import android.os.AsyncTask;
import android.os.Bundle;
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

import pl.pollub.android.myapplication.R;

public class DownloadActivity extends AppCompatActivity {

    EditText addressET;
    TextView fileSizeInfoTV, fileTypeInfoTV;
    Button getInfoButton;

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
            new FetchFileInfoTask().execute(urlString);
        });
    }

    private void assignViews(){
        addressET = findViewById(R.id.addressET);
        getInfoButton = findViewById(R.id.getInfoButton);
        fileSizeInfoTV = findViewById(R.id.fileSizeInfoTV);
        fileTypeInfoTV = findViewById(R.id.fileTypeInfoTV);
    }

    private class FetchFileInfoTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            HttpURLConnection connection = null;
            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return "Błąd połączenia: " + responseCode;
                }

                return  connection.getContentType() + " " + connection.getContentLength();

            } catch (Exception e) {
                return "Błąd: " + e.getMessage();
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("Błąd")){
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                fileTypeInfoTV.setText("---");
                fileSizeInfoTV.setText("---");
            }else {
                fileTypeInfoTV.setText(result.split(" ")[0]);
                fileSizeInfoTV.setText(result.split(" ")[1]);
            }
        }
    }
}