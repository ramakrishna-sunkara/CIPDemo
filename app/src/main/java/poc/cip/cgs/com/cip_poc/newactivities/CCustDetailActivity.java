/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package poc.cip.cgs.com.cip_poc.newactivities;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import poc.cip.cgs.com.cip_poc.R;

public class CCustDetailActivity extends AppCompatActivity {

    private EditText searchCustIDEdit;
    private String searchCustID = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_detail);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra("EXTRA_NAME");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Customer Details");

        searchCustIDEdit = (EditText)findViewById(R.id.search_cust_id);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CScanActivity.class);
                intent.putExtra(CIPConstants.INTENT_ACTOIN_FROM, "FromCustDetails");
                startActivityForResult(intent, 1);
            }
        });

        searchCustIDEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH)   {
                    getCustomerDetails(searchCustIDEdit.getText().toString());
                }
                return false;
            }
        });

    }

    private void getCustomerDetails(String custid) {
        if (custid.equals("") || custid == null){
              Toast.makeText(getApplicationContext(),"Please enter customer ID ",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "coustomer id is " + custid, Toast.LENGTH_SHORT).show();
            Fragment newFragment = new CCustDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(CCustDetailsFragment.KEY_CUST_ID,custid);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.custdetails_container, newFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data.getExtras().containsKey(CIPConstants.KEY_SCAN_ID)){
            Toast.makeText(getApplicationContext(),data.getStringExtra(CIPConstants.KEY_SCAN_ID),Toast.LENGTH_SHORT).show();
            searchCustIDEdit.setText(data.getStringExtra(CIPConstants.KEY_SCAN_ID));
        }
    }
}
