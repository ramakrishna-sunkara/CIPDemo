package poc.cip.cgs.com.cip_poc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import poc.cip.cgs.com.cip_poc.model.MyAppConstants;


public class CustomerDetailActivity extends Activity {
    private static final CusDto cdto = new CusDto();
    EditText refno;
    String finalstring;
    Button scan, submit, work,Sync;
    TextView clientid, emailtext, mobiletext, firstnametext;
    JSONObject contacts = null;
    ImageView imageView;



    private static final String TAG_ClientInfo = "ClientInfo";
    CustomerDetailActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        refno = (EditText) findViewById(R.id.ref_no);
        scan = (Button) findViewById(R.id.scan);
        submit = (Button) findViewById(R.id.submit);
        work = (Button) findViewById(R.id.work);
        Sync = (Button) findViewById(R.id.sync);
        final RelativeLayout mCusprofile = (RelativeLayout) findViewById(R.id.main1);
        final LinearLayout mBtnLayout = (LinearLayout) findViewById(R.id.btnlayout);
        activity = this;

        imageView = (ImageView) findViewById(R.id.imageView);
//        refno.setText("WB60002364");

        Picasso.with(this)
                .load("http://www.american.edu/uploads/profiles/large/James%20Thurber%20Profile%20Photo.jpg")
                .into(imageView);

        Bundle lBundle = getIntent().getExtras();
        if (lBundle != null) {
            String value = lBundle.getString("qrcode");
            System.out.println("ffffff" + value);
            refno.setText(value);
        }



     /*   Intent intent = getIntent();
        if (intent.getExtras().get("qrcode") != null) {
            String op = intent.getExtras().getString("qrcode");
            if (op != null) {
                System.out.println("fffffffffffff" + op);
                refno.setText(op);
            }
        }*/
        scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);


            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCusprofile.setVisibility(View.VISIBLE);
                mBtnLayout.setVisibility(View.VISIBLE);

                final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(activity);
                String value = (mSharedPreference.getString("EmployeeId", ""));
                // String  emp = LoginActivity.dto.getEmpid();
                System.out.println("eeeeee" + value);
                //int eid = Integer.parseInt(LoginActivity.dto.getEmpid());
                if (value.equals("11")) {
                    work.setText("SIGNED");
                } else if (value.equals("12")) {
                    work.setText("SUBMIT");
                }
                customerdetail();


            }
        });
        work.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                submitCustomer(0);

            }
        });

        Sync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                submitCustomer(1);


            }
        });
    }

    private void customerdetail() {
        try {
            String urlParameters =
                    "ReferenceNumber=" + refno.getText().toString();
            AppAsyncTask aAsyncTask = new AppAsyncTask(CustomerDetailActivity.this,
                    MyAppConstants.CUSTOMER_API,
                    urlParameters, "Please Wait..");
            aAsyncTask.setListener(new AppAsyncTask.AsyncTaskListener() {
                @Override
                public void onPreExecuteConcluded() {
                }

                @Override
                public void onPostExecuteConcluded(String theResult) {
                    try {
                        Log.d("Response", theResult);
                        processCustomerResult(theResult);

//                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
//                        startActivity(intent);
                    } catch (Exception theJSONException) {
                        theJSONException.printStackTrace();

                    }
                }

                protected void onProgressUpdate() {
                    // clientid.setText(cdto.getClientInfoId());
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


    private void processCustomerResult(String theResult) {
        try {
            JSONObject jsonResponse = new JSONObject(theResult);
            contacts = jsonResponse.getJSONObject(TAG_ClientInfo);
            String clientinfo = contacts.getString("ClientInfoId");
            String firstname = contacts.getString("FirstName");
            String lastname = contacts.getString("LastName");
            String email = contacts.getString("Email");
            String mobile = contacts.getString("Mobile");
            cdto.setClientInfoId(clientinfo);
            cdto.setFirstName(firstname);
            cdto.setLastName(lastname);
            cdto.setEmail(email);
            cdto.setMobile(mobile);

            TextView clientid = (TextView) findViewById(R.id.client_id_value);
            //String cs = CustomerDetailActivity.cdto.getClientInfoId();
            clientid.setText(clientinfo);
            TextView emailtext = (TextView) findViewById(R.id.email_value);
            emailtext.setText(email);
            TextView mobiletext = (TextView) findViewById(R.id.mobile_value);
            mobiletext.setText(mobile);
            TextView firstnametext = (TextView) findViewById(R.id.firstname_value);
            firstnametext.setText(firstname);
            System.out.println("hhh" + clientinfo);

            CIPModelData.persistCustomerInfo(this, cdto);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_customer_detail, menu);
//        return true;
//    }

    private void submitCustomer(int i) {

        try {
            String urlParameters;
            if(i==0){
                 urlParameters =
                        "ReferenceNumber=WB60002364&EmployeeId=11&Status=Y";
            }
            else {
                 urlParameters =
                        "ReferenceNumber=WB60002364&EmployeeId=11&Status=N";
            }

           // String urlParameters =
                   // "ReferenceNumber=WB60002364&EmployeeId=11&Status=Y";
            AppAsyncTask aAsyncTask = new AppAsyncTask(CustomerDetailActivity.this,
                    MyAppConstants.CUSTOMER_UPDATE,
                    urlParameters, "Please Wait..");
            aAsyncTask.setListener(new AppAsyncTask.AsyncTaskListener() {
                @Override
                public void onPreExecuteConcluded() {
                }

                @Override
                public void onPostExecuteConcluded(String theResult) {
                    try {
                        Log.d("Response", theResult);
                        processUpdateCustomerResult(theResult);

//                        Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
//                        startActivity(intent);
                    } catch (Exception theJSONException) {
                        theJSONException.printStackTrace();

                    }
                }

                protected void onProgressUpdate() {
                    // clientid.setText(cdto.getClientInfoId());
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

    private void processUpdateCustomerResult(String theResult) {
        try {
            JSONObject jsonResponse = new JSONObject(theResult);
            // contacts = jsonResponse.getJSONObject(TAG_ClientInfo);
            String isSuccess = jsonResponse.getString("IsSuccess");
            String SuccessMessage = jsonResponse.getString("SuccessMessage");
            System.out.println("SSSSS"+isSuccess);

            cdto.setIsSuccess(isSuccess);
            cdto.setSuccessMessage(SuccessMessage);

            CIPModelData.persistCustomerUpdate(this, cdto);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
