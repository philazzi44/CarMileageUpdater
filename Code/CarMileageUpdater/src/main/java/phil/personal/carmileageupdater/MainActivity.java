package phil.personal.carmileageupdater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// All passwords for the keystore are "carmileage"
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void HandleSubmitClick(View arg0) {
        Button btnSubmit = (Button)arg0;
        EditText tboMileage = (EditText) findViewById(R.id.tboMileage);

        int newMileage = Integer.parseInt(tboMileage.getText().toString());
        btnSubmit.setVisibility(View.INVISIBLE);
        PostToPasteBin(newMileage);
    }

    class PostToPasteBinTask extends AsyncTask<String, Void, Boolean> {

        private Integer _newMileage;
        public PostToPasteBinTask(Integer newMileage)
        {
            _newMileage = newMileage;
        }
        private Exception exception;

        protected Boolean doInBackground(String... urls) {
            try {
                String apiDevKey = "1130938b77f534523710589cc6ae42c1";
                String apiUserKey = "ccd2b7b197c8cab9104fc6d4dc665939";
                String apiOption = "paste";
                String apiPasteName = "carmileage";
                String apiPasteExpireDate = "1W";
                String apiPastePrivate = "1"; // Unlisted
                String apiPasteCode = _newMileage.toString();

                URL url = new URL("http://pastebin.com/api/api_post.php");
                String urlParameters = "api_dev_key=" + apiDevKey +
                        "&api_user_key=" + apiUserKey +
                        "&api_option=" + apiOption +
                        "&api_paste_name=" + apiPasteName +
                        "&api_paste_expire_date=" + apiPasteExpireDate +
                        "&api_paste_private=" + apiPastePrivate +
                        "&api_paste_code=" + apiPasteCode;

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
                connection.setUseCaches (false);

                DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();

                // Get the response
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                String allLine = "";
                String line = "";
                while ((line = rd.readLine()) != null) {
                    allLine = allLine + line;
                }

                wr.close();
                rd.close();
                connection.disconnect();
                return true;
            } catch (Exception e) {
                this.exception = e;
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            if (result)
                ShowSuccess();
            else
                ShowFailure();
            ((Button)findViewById(R.id.btnSubmit)).setVisibility(View.VISIBLE);
        }
    }

    private void PostToPasteBin(Integer newMileage)
    {
        new PostToPasteBinTask(newMileage).execute();
    }

    private void ShowSuccess()
    {
        new AlertDialog.Builder(this)
                .setTitle("Mileage Updated")
                .setMessage("The mileage value has been updated successfully.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void ShowFailure()
    {
        new AlertDialog.Builder(this)
                .setTitle("Mileage Not Updated")
                .setMessage("The mileage value has not been updated successfully. Please try again later.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}
