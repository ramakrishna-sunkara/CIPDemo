package poc.cip.cgs.com.cip_poc.newactivities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import poc.cip.cgs.com.cip_poc.AppAsyncTask;
import poc.cip.cgs.com.cip_poc.CIPModelData;
import poc.cip.cgs.com.cip_poc.CustomerActivity;
import poc.cip.cgs.com.cip_poc.R;
import poc.cip.cgs.com.cip_poc.UserDto;
import poc.cip.cgs.com.cip_poc.model.MyAppConstants;


public class CLoginActivity extends AppCompatActivity {
    private String Content;

    Context context;
    public static final UserDto dto = new UserDto();
    private static final String TAG_APPLICATIONUSER = "ApplicationUser";
    private static final String TAG_BRANCH = "Branch";
    private static final String TAG_LOBNAME = "LOBName";
    private static final String TAG_ROLENAME = "RoleName";
    private static final String TAG_EMPNAME = "EmployeeName";
    private static final String TAG_ACTIVE = "Active";
    private static final String TAG_PASSWORD = "Password";
    private static final String TAG_USERID = "UserId";
    JSONObject contacts = null;
    private EditText emailEdit;
    private EditText passwordEdit;
    String userName = "";
    String empid = "";
    String branch = "";
    String lob = "";
    String rolename = "";
    public static ArrayList<HashMap<String, String>> contactList;
    String targetURL = "http://appserver.constient.com/KYCDiss/api/Login/Authentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ram);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_action_user);
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Login");

        contactList = new ArrayList<HashMap<String, String>>();
        emailEdit = (EditText) findViewById(R.id.email_edit);
        passwordEdit = (EditText) findViewById(R.id.password_edit);
        emailEdit.setText("13108");
        passwordEdit.setText("n");

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        try {
            String urlParameters =
                    "UserName=" + emailEdit.getText().toString() +
                            "&Password=" + passwordEdit.getText().toString() + "&APKVersion=" + MyAppConstants.APK_VERSION;
            AppAsyncTask aAsyncTask = new AppAsyncTask(CLoginActivity.this,
                    MyAppConstants.LOGIN_API,
                    urlParameters, "Please Wait..");
            aAsyncTask.setListener(new AppAsyncTask.AsyncTaskListener() {
                @Override
                public void onPreExecuteConcluded() {
                }

                @Override
                public void onPostExecuteConcluded(String theResult) {
                    try {
                        Log.d("Response", theResult);
                        processLoginResult(theResult);
//                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
//                        startActivity(intent);
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

    private void processLoginResult(String theResult) {
        try {
            JSONObject jsonResponse = new JSONObject(theResult);
            final String username = jsonResponse.getString("UserName");
            final String empid = jsonResponse.getString("EmployeeId");
            String isauthenticated = jsonResponse.getString("IsAuthenticated");
            String authentication = jsonResponse.getString("AuthenticationSuccess");
            String otp = jsonResponse.getString("OTP");
            String agentmobile = jsonResponse.getString("AgentMobile");


            UserDto dto = new UserDto();
            dto.setUsername(username);
            dto.setEmpid(empid);
            dto.setIsauthenticated(isauthenticated);
            dto.setAuthentication(authentication);
            dto.setOtp(otp);
            dto.setAgentmobile(agentmobile);

            CIPModelData.persistLoginInfo(this, dto);

            if (isauthenticated.equals("true")) {
//                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
//                alertDialog.setTitle("Login...");
//                alertDialog.setMessage("" + authentication);
//                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
//
//                        startActivity(intent);
//                    }
//                });
//                alertDialog.show();
                Intent intent = new Intent(getApplicationContext(), CCustListActivity.class);
                startActivity(intent);
            } else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CLoginActivity.this);
                alertDialog.setTitle("Login...");
                alertDialog.setMessage("Login Failure" + authentication);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


}
