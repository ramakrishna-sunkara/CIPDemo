package poc.cip.cgs.com.cip_poc;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import poc.cip.cgs.com.cip_poc.model.MyAppConstants;


public class MenuFragment extends Fragment {
    UserDto userDto;
    private Toolbar toolbar;
    private ImageView cusicon;
    private String toast;
    private List<String> mDataSourceList = new ArrayList<String>();
    private List<FragmentTransaction> mBackStackList = new ArrayList<FragmentTransaction>();
    private String TAG = "MenuFragment";
    RecyclerView recyclerView;
    MyAdapter mAdapter;
    Button mScanBtn, mResetBtn, mAcceptBtn;
    ArrayList<ItemData> listItems;
    int toolbarshowvalue = 45;
    Activity activity;
    String employeeId = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        activity = getActivity();
        return view;
    }

    @Override
    public void onResume() {
        Log.d(TAG, "MenuFragment...onResume..");
        if (mAdapter != null) {
            mAdapter.updateList(CIPModelData.getItems());
        }
//        recyclerView.notifyAll();
        super.onResume();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
        mScanBtn = (Button) getActivity().findViewById(R.id.scan);
        mResetBtn = (Button) getActivity().findViewById(R.id.reset);
        mAcceptBtn = (Button) getActivity().findViewById(R.id.acceptall);


        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter
        mAdapter = new MyAdapter(CIPModelData.getItems());
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mScanBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                Toast.makeText(getActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ContinuousCaptureActivity.class);
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
                startActivity(intent);
            }


        });

        mResetBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                CIPModelData.clearListItems();
                CIPModelData.getItems();
                Toast.makeText(getActivity(), "List reset done !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                startActivity(intent);
            }


        });

        mAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int size = CIPModelData.getItems().size();
                ArrayList<ItemData> trueDatas = new ArrayList<ItemData>();
                for (int i = 0; i < size; i++) {
                    ItemData itemData = CIPModelData.getItems().get(i);
                    if (itemData.isScanStatus()) {
                        trueDatas.add(itemData);
                    }
                }

                size = trueDatas.size();

                for (int i = 0; i < size; i++) {
                    ItemData itemData = trueDatas.get(i);
                    if (itemData.isScanStatus()) {
                        String title = itemData.getTitle();
//                    Log.d("test", data.getTitle() + ",status=" + data.isScanStatus());
                        if (i == (size - 1))
                            acceptRefNum(title, true);
                        else
                            acceptRefNum(title, false);
                    }
                }
            }
        });

        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            boolean hideToolBar = false;
            int toolbarMarginOffset = 0;

            private int dp(int inPixels) {
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, inPixels, getActivity().getResources().getDisplayMetrics());
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                toolbarMarginOffset += dy;
                if (toolbarMarginOffset > dp(toolbarshowvalue)) {
                    toolbarMarginOffset = dp(toolbarshowvalue);
                }
                if (toolbarMarginOffset < 0) {
                    toolbarMarginOffset = 0;
                }
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
                params.topMargin = -1 * toolbarMarginOffset;
                toolbar.setLayoutParams(params);
//                appCompatActivity.setSupportActionBar(toolbar);
            }
        };
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        cusicon = (ImageView) getActivity().findViewById(R.id.imageView2);
        final SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(activity
                .getApplicationContext());
        String value = (mSharedPreference.getString("EmployeeId", ""));
        employeeId = value;
        // String  emp = LoginActivity.dto.getEmpid();
        System.out.println("eeeeee" + value);
        //int eid = Integer.parseInt(LoginActivity.dto.getEmpid());
        if (value.equals("11")) {
            Drawable myDrawable = getResources().getDrawable(
                    R.drawable.person);
            cusicon.setImageDrawable(myDrawable);
        } else if (value.equals("12")) {
            Drawable myDrawable = getResources().getDrawable(
                    R.drawable.system);
            cusicon.setImageDrawable(myDrawable);
        }
        toolbar.inflateMenu(R.menu.main);

        // MenuItem item = menu.findItem(R.id.action_login);
        //  item.setActionView(R.layout.menu_bar);
        //  MenuInflater inflater=getActivity().getMenuInflater();
        // inflater.inflate(R.layout.menu_bar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                                               @Override
                                               public boolean onMenuItemClick(MenuItem menuItem) {
                                                   switch (menuItem.getItemId()) {
                                                       case R.id.contact_us:
                                                           // EITHER CALL THE METHOD HERE OR DO THE FUNCTION DIRECTLY
                                                           Intent i = new Intent(getActivity(), CustomerDetailActivity.class);
                                                           startActivity(i);

                                                           return true;

                                                       default:
                                                           // return super.onOptionsItemSelected(menuItem);
                                                   }
                                                   return true;
                                               }
                                           }
        );


        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();


        recyclerView.addOnScrollListener(onScrollListener);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

//        mAdapter.SetOnItemClickListener(new MyAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, ItemData itemData) {
//
//
//                Log.d("test", "" + itemData.getTitle() + "possssss" + position);
//                acceptRefNum(itemData.getTitle());
//            }
//        });
    }

    private void acceptRefNum(final String ref, final boolean b) {
//        String vref = "WB60002364";
        try {
            String urlParameters =
                    "EmployeeId=" + employeeId + "&ReferenceNumber=" + ref;
            AppAsyncTask aAsyncTask = new AppAsyncTask(getActivity(),
                    MyAppConstants.UPD_REF_API,
                    urlParameters, "Please Wait..");
            aAsyncTask.setListener(new AppAsyncTask.AsyncTaskListener() {
                @Override
                public void onPreExecuteConcluded() {
                }

                @Override
                public void onPostExecuteConcluded(String theResult) {
                    try {
                        Log.d("Response", theResult);
                        processResult(theResult, ref, b);
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
            Toast.makeText(activity,
                    "MyAppConstants.JSON_ERROR_REQ", Toast.LENGTH_LONG).show();
        }
    }


    private void processResult(String theResult, final String ref, boolean b) {
        try {
            JSONObject jsonResponse = new JSONObject(theResult);
            String IsSuccess = jsonResponse.getString("IsSuccess");
            String SuccessMessage = jsonResponse.getString("SuccessMessage");
            String FailureMessage = jsonResponse.getString("FailureMessage");
            Log.d("Response", "value=" + IsSuccess);
            if (IsSuccess.equals("true"))
                CIPModelData.removeItems(ref);
            if (b) {
                if (IsSuccess.equals("true")) {

                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle("CIP POC");
                    alertDialog.setMessage(SuccessMessage);
                    alertDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            mAdapter.updateList(CIPModelData.getItems());
                        }
                    });
                    alertDialog.show();
                } else {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle("CIP POC");
                    alertDialog.setMessage(FailureMessage);
                    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
