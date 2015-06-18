package poc.cip.cgs.com.cip_poc.newactivities;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import poc.cip.cgs.com.cip_poc.R;

public class CCustDetailsFragmentOld extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String KEY_CUST_ID = "custid";
    //private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mCustID;
    //private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCustID = getArguments().getString(KEY_CUST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout rootview = (LinearLayout)inflater.inflate(R.layout.fragment_cust_detail, container, false);

        return  rootview;
    }



}
