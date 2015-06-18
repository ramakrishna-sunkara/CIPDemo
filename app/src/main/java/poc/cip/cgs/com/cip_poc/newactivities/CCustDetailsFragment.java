package poc.cip.cgs.com.cip_poc.newactivities;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import poc.cip.cgs.com.cip_poc.AppAsyncTask;
import poc.cip.cgs.com.cip_poc.CIPModelData;
import poc.cip.cgs.com.cip_poc.CusDto;
import poc.cip.cgs.com.cip_poc.R;
import poc.cip.cgs.com.cip_poc.model.MyAppConstants;

public class CCustDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String KEY_CUST_ID = "custid";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mCustID = "";
    //private String mParam2;
   // private static final CusDto cdto = new CusDto();
    JSONObject contacts = null;
    private static final String TAG_ClientInfo = "ClientInfo";

    public static final CusDto cdto = new CusDto();
    private static TextView emailtext,mobiletext,firstnametext;
    private static String email,mobile,firstname;

    View mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mCustID = getArguments().getString(KEY_CUST_ID);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_cust_detail, container, false);
        ininRes();

        customerdetail();


        return mainView;
    }

    private void ininRes() {

        emailtext = (TextView) mainView.findViewById(R.id.emailTxt_value);
        mobiletext = (TextView) mainView.findViewById(R.id.mobileTxt_value);
        firstnametext = (TextView) mainView.findViewById(R.id.nameTxt_value);


    }

    private void customerdetail() {
        try {
            String urlParameters =
                    "ReferenceNumber=WB60002363";
            AppAsyncTask aAsyncTask = new AppAsyncTask(getActivity(),
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
            Toast.makeText(getActivity(),
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

            emailtext.setText(email);
            mobiletext.setText(mobile);
            firstnametext.setText(firstname);

//            TextView clientid = (TextView) findViewById(R.id.client_id_value);
//            //String cs = CustomerDetailActivity.cdto.getClientInfoId();
//            clientid.setText(clientinfo);
//            TextView emailtext = (TextView) findViewById(R.id.email_value);
//            emailtext.setText(email);
//            TextView mobiletext = (TextView) findViewById(R.id.mobile_value);
//            mobiletext.setText(mobile);
//            TextView firstnametext = (TextView) findViewById(R.id.firstname_value);
//            firstnametext.setText(firstname);
//            System.out.println("hhh" + clientinfo);

            CIPModelData.persistCustomerInfo(getActivity(), cdto);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




}
