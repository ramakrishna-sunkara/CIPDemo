package poc.cip.cgs.com.cip_poc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import poc.cip.cgs.com.cip_poc.model.MyAppConstants;


public class CustomerActivity extends FragmentActivity {
    private String TAG = "MenuFragment";
    Fragment listFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        getReferenceNumbers();
//        CIPModelData.prepareItems();
        listFragment = new MenuFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    private void getReferenceNumbers() {
        try {
            String urlParameters =
                    "EmployeeId=11";
            AppAsyncTask aAsyncTask = new AppAsyncTask(CustomerActivity.this,
                    MyAppConstants.GET_REF_API,
                    urlParameters, "Please Wait..");
            aAsyncTask.setListener(new AppAsyncTask.AsyncTaskListener() {
                @Override
                public void onPreExecuteConcluded() {
                }

                @Override
                public void onPostExecuteConcluded(String theResult) {
                    try {
                        Log.d("Response", theResult);
                        processResult(theResult);
                    } catch (Exception theJSONException) {
                        theJSONException.printStackTrace();

                    }
                }
            });
            aAsyncTask.execute(urlParameters);
        } catch (Exception theJSONException) {
            theJSONException.printStackTrace();
            Log.e(this.getClass().getName(),
                    "JSON Exception while constructing request for login webservice");
            Toast.makeText(getApplicationContext(),
                    "MyAppConstants.JSON_ERROR_REQ", Toast.LENGTH_LONG).show();
        }
    }

    private void processResult(String theResult) {
        try {
            JSONObject jsonResponse = new JSONObject(theResult);
            String IsSuccess = jsonResponse.getString("IsSuccess");
            String SuccessMessage = jsonResponse.getString("SuccessMessage");
            String FailureMessage = jsonResponse.getString("FailureMessage");
            if (IsSuccess.equals("true")) {
                CIPModelData.clearListItems();
                JSONArray jsonArray = jsonResponse.getJSONArray("ReferenceNumberList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object jsonObject = jsonArray.get(i);
                    String ref = String.valueOf(jsonObject);
//                    Log.d("Test", );
                    CIPModelData.persistRefNum(ref, 0);
                }

                showMenuFragment();
            } else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerActivity.this);
                alertDialog.setTitle("Retrieving Reference Numbers...");
                alertDialog.setMessage("Call to Server Failed..");
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test","calling fragemnet..");
        listFragment = new MenuFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.list_container, listFragment);

    }

    private void showMenuFragment() {
        listFragment = new MenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.list_container, listFragment);
        fragmentTransaction.commit();
    }
}
