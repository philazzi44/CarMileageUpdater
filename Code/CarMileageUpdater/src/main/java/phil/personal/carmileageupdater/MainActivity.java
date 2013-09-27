package phil.personal.carmileageupdater;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        Object objSpreadsheet = OpenSpreadsheet();

        boolean result = UpdateSpreadsheet(objSpreadsheet, newMileage);

        CloseSpreadsheet(objSpreadsheet);

        if (result)
        {
            ShowSuccess();
        }
        else
        {
            ShowFailure();
        }
    }

    private Object OpenSpreadsheet()
    {
        return null;
    }

    private void Authenticate()
    {
        AccountManager am = AccountManager.get(activity);
        am.getAuthToken(am.getAccounts())[0],
                "ouath2:" + DriveScopes.DRIVE,
                new Bundle(),
                true,
                new OnTokenAcquired(),
                null);
    }
    private boolean UpdateSpreadsheet(Object objSpreadsheet, int newMileage)
    {
        return true;
    }

    private void CloseSpreadsheet(Object objSpreadsheet)
    {

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
