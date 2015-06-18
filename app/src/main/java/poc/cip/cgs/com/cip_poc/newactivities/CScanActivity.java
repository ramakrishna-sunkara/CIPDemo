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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import poc.cip.cgs.com.cip_poc.R;

public class CScanActivity extends AppCompatActivity {

    private static final String TAG = CScanActivity.class.getSimpleName();
    private CompoundBarcodeView barcodeView;
    private String actionFrom = "";
    private String scanCustID = "";

    private TextView custidText;
    private FloatingActionButton floatingActionButton;

    private int counterInt = 0;
    private AlphaAnimation fadeIn,fadeOut;

    List<ResultPoint> resultList;

    private ArrayList<String> custRefIDList;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                //barcodeView.setStatusText(result.getText());
                if (result.getText() != null) {
                    String qrcode = result.getText();
                    barcodeView.setStatusText(qrcode);
                    scanCustID = qrcode;
                    switch (actionFrom){
                        case "FromCustList":
                            //custRefIDList.add(qrcode);
                            //custidText.setText(qrcode);
                            break;
                        case "FromCustDetails":
                            //scanCustID = qrcode;
                            break;
                    }
                    Log.d("OUTPUT", qrcode);
                   // result=qrcode;
                }
            }
            //Added preview of scanned barcode
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
            resultList = resultPoints;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        custRefIDList = new ArrayList<String>();

        try {
            Intent intent = getIntent();
            actionFrom = intent.getStringExtra(CIPConstants.INTENT_ACTOIN_FROM);
            custRefIDList = intent.getStringArrayListExtra(CIPConstants.KEY_SCAN_LIST);
            if (custRefIDList != null)
            Toast.makeText(getApplicationContext(),"from intetnt: "+custRefIDList.size(),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Scan your QR code");

        custidText = (TextView)findViewById(R.id.custidText);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.setStatusText("Waiting for QR code");
        barcodeView.decodeContinuous(callback);

        fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        fadeOut = new AlphaAnimation( 1.0f , 0.0f ) ;
        //custidText.startAnimation(fadeOut);
        fadeIn.setDuration(1200);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(1200);
        fadeOut.setFillAfter(true);
        fadeOut.setStartOffset(4200 + fadeIn.getStartOffset());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counterInt == 0){
                    floatingActionButton.setSoundEffectsEnabled(true);
                    floatingActionButton.setImageResource(android.R.drawable.ic_media_play);
                    barcodeView.pause();
                    custidText.setText(scanCustID);
                    custidText.startAnimation(fadeIn);

                    if (CCustListActivity.sAssignedCustIDsList.contains(scanCustID)) {
                        if (actionFrom.equals("FromCustList") && !scanCustID.equals("")) {
                            if (custRefIDList.contains(scanCustID)) {
                                //Toast.makeText(getApplicationContext(),"You already scanned this ID",Toast.LENGTH_SHORT).show();
                                //custRefIDList.add(scanCustID);
                                showDialog(scanCustID);
                            } else {
                                //Toast.makeText(getApplicationContext(),"New one",Toast.LENGTH_SHORT).show();
                                custRefIDList.add(scanCustID);
                            }

                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"This reference ID is not assigned to you",Toast.LENGTH_SHORT).show();
                    }
                    counterInt = 1;

                }else{
                    barcodeView.resume();
                    floatingActionButton.setImageResource(android.R.drawable.ic_media_pause);
                    scanCustID = "";
                    custidText.setText("Scanning...");
                    //custidText.startAnimation(fadeOut);
                    barcodeView.setStatusText("Waiting for QR code");
                    counterInt = 0;
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                passResultData();
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
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    public void showDialog(final String scanCustID){
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CScanActivity.this);
        alertDialog.setTitle("Already Selected");
        alertDialog.setMessage("You want to deselect?");
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                custRefIDList.remove(scanCustID);
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    public void passResultData(){
        Intent i = getIntent();
        switch (actionFrom){
            case "FromCustList":
                // add elements to al, including duplicates
                Set<String> hs = new HashSet<String>();
                hs.addAll(custRefIDList);
                custRefIDList.clear();
                custRefIDList.addAll(hs);
                i.putStringArrayListExtra(CIPConstants.KEY_SCAN_LIST, custRefIDList);
                break;
            case "FromCustDetails":
                i.putExtra(CIPConstants.KEY_SCAN_ID, scanCustID);
                break;
        }
        setResult(RESULT_OK, i);
        finish();
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        passResultData();
    }
}
