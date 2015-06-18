package poc.cip.cgs.com.cip_poc;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rohini on 5/27/2015.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    public ArrayList<ItemData> itemsData;
    OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, ItemData itemData);
    }


    public MyAdapter(ArrayList<ItemData> itemsData) {
        this.itemsData = itemsData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData


        viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
        viewHolder.txtViewStatus.setText(itemsData.get(position).getServerStatus() + "test");
        if (itemsData.get(position).isScanStatus()) {
            viewHolder.relativeLayout.setBackgroundColor(Color.LTGRAY);
        } else {
//            viewHolder.relativeLayout.setBackgroundColor(Color.CYAN);
        }
//        Log.d("niaz",itemsData.get(position).getsubtitle()+ itemsData.get(position).getTitle() + "=" + itemsData.get(position).isScanStatus());

    }

    // inner class to hold a reference to each item of RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtViewTitle, txtViewStatus;
        public RelativeLayout relativeLayout;


        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            relativeLayout = (RelativeLayout) itemLayoutView;
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewStatus = (TextView) itemLayoutView.findViewById(R.id.item_status);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getLayoutPosition(), itemsData.get(getLayoutPosition()));
            }
        }


    }
    public void updateList(ArrayList<ItemData> data) {
        itemsData = data;
        notifyDataSetChanged();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}
