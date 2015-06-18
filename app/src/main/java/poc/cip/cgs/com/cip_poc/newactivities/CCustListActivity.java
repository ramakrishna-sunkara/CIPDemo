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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import poc.cip.cgs.com.cip_poc.R;

/**
 * TODO
 */
public class CCustListActivity extends AppCompatActivity {

    //private DrawerLayout mDrawerLayout;

    private ArrayList<String> selectedCustIDsList;

    protected static ArrayList<String> sAssignedCustIDsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_action_user);
        ab.setDisplayHomeAsUpEnabled(true);
        setTitle("Scan your QR code");

        selectedCustIDsList = new ArrayList<String>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CCustListActivity.this,CScanActivity.class);
                intent.putExtra(CIPConstants.INTENT_ACTOIN_FROM, "FromCustList");
                intent.putStringArrayListExtra(CIPConstants.KEY_SCAN_LIST, selectedCustIDsList);
                //intent.putStringArrayListExtra(CIPConstants.KEY_ASSN_CUST_ID_LIST, selectedCustIDsList);
                startActivityForResult(intent, 1);
                //startActivity(new Intent(getApplicationContext(),CCustListFragment.class));
            }
        });

        findViewById(R.id.acceptall_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCustListFragment custListFragment = (CCustListFragment) getSupportFragmentManager().findFragmentById(R.id.custlist_fragment);
                custListFragment.acceptSelectedCustIDs();
            }
        });

        findViewById(R.id.reset_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CCustListFragment custListFragment = (CCustListFragment) getSupportFragmentManager().findFragmentById(R.id.custlist_fragment);
                custListFragment.resetSelectedCustIDs();
            }
        });

        // TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_customers:
                startActivity(new Intent(getApplicationContext(), CCustDetailActivity.class));
                //mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data.getExtras().containsKey(CIPConstants.KEY_SCAN_LIST)) {
               // Toast.makeText(getApplicationContext(), data.getStringExtra(CIPConstants.KEY_SCAN_ID), Toast.LENGTH_SHORT).show();
                 selectedCustIDsList = data.getStringArrayListExtra(CIPConstants.KEY_SCAN_LIST);
                CCustListFragment custListFragment = (CCustListFragment) getSupportFragmentManager().findFragmentById(R.id.custlist_fragment);
                custListFragment.updateSelectedCustIDs(selectedCustIDsList);
            }
        }else{
            Toast.makeText(getApplicationContext(), "No data found...", Toast.LENGTH_SHORT).show();
        }
    }

}
