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


import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import poc.cip.cgs.com.cip_poc.AppAsyncTask;
import poc.cip.cgs.com.cip_poc.R;
import poc.cip.cgs.com.cip_poc.model.MyAppConstants;

public class CCustListFragment extends Fragment{

    RecyclerView rv;
    ArrayList<Item> refNoList;
    private Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rv = (RecyclerView) inflater.inflate(
                R.layout.fragment_cust_list, container, false);

        getReferenceNumbers();
        //adapter = new Adapter();
        //rv.setAdapter(adapter);
        //rv.setItemAnimator(new DefaultItemAnimator());
        //rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        refNoList = new ArrayList<>();
//        for (int i = 0; i < 10; ++i) {
//            Item item = new Item();
//            item.setTitle("item"+i);
//            item.setActive(true);
//            refNoList.add(item);
//        }

        //adapter = new Adapter(refNoList);
        //rv.setAdapter(adapter);
        //rv.setItemAnimator(new DefaultItemAnimator());
        //rv.setLayoutManager(new LinearLayoutManager(getActivity()));



        return rv;
    }

    // to get reference number from the service through asynctask
    private void getReferenceNumbers() {
        try {
            String urlParameters =
                    "EmployeeId=11";
            AppAsyncTask aAsyncTask = new AppAsyncTask(getActivity(),
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
            Toast.makeText(getActivity(),
                    "MyAppConstants.JSON_ERROR_REQ", Toast.LENGTH_LONG).show();
        }
    }

    // to get the strings result from the json object
    private void processResult(String theResult) {
        try {
            JSONObject jsonResponse = new JSONObject(theResult);
            String IsSuccess = jsonResponse.getString("IsSuccess");
            String SuccessMessage = jsonResponse.getString("SuccessMessage");
            String FailureMessage = jsonResponse.getString("FailureMessage");
            refNoList = new ArrayList<Item>();
            if (IsSuccess.equals("true")) {
                //CIPModelData.clearListItems();

                JSONArray jsonArray = jsonResponse.getJSONArray("ReferenceNumberList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object jsonObject = jsonArray.get(i);
                    String ref = String.valueOf(jsonObject);
            Item item = new Item();
            item.setTitle(ref);
            item.setActive(false);
            refNoList.add(item);
                    CCustListActivity.sAssignedCustIDsList.add(ref);

                }
//                rv.setAdapter(new MyRecyclerViewAdapter(getActivity(),
//                        (refNoList)));
                adapter = new Adapter(refNoList);
                rv.setAdapter(adapter);
                rv.setItemAnimator(new DefaultItemAnimator());
                rv.setLayoutManager(new LinearLayoutManager(getActivity()));

            } else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public void acceptSelectedCustIDs(){
        Toast.makeText(getActivity(), "Accept All clicked", Toast.LENGTH_SHORT).show();
    }

    public void resetSelectedCustIDs(){
        Toast.makeText(getActivity(), "Reset clicked", Toast.LENGTH_SHORT).show();
    }

    public void updateSelectedCustIDs(ArrayList<String> scanedCustIDsList){
        Toast.makeText(getActivity(), "Update clicked: "+scanedCustIDsList.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Item> items = new ArrayList<Item>();
        for (int i=0;i<refNoList.size();i++) {
        if (scanedCustIDsList.contains(refNoList.get(i).getTitle())){
            refNoList.get(i).setActive(true);
        }else {
            refNoList.get(i).setActive(false);
        }
        }
        //refNoList = new ArrayList<>();
//        for (int i = 0; i < 5; ++i) {
//
//            refNoList.add(new Item("item" + i, false));
//        }

        adapter.notifyDataSetChanged();

    }

}



