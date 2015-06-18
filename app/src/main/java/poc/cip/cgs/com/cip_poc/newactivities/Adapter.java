package poc.cip.cgs.com.cip_poc.newactivities;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import poc.cip.cgs.com.cip_poc.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
	@SuppressWarnings("unused")
	private static final String TAG = Adapter.class.getSimpleName();

	private static final int TYPE_INACTIVE = 0;
	private static final int TYPE_ACTIVE = 1;

	private static final int ITEM_COUNT = 10;
	private List<Item> listCust;

	public Adapter(ArrayList<Item> listCust11){
//			Random random = new Random();
//			listCust = new ArrayList<>();
//			for (int i = 0; i < ITEM_COUNT; ++i) {
//				listCust.add(new Item("item" + i, random.nextBoolean()));
//		}
		listCust = listCust11;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final int layout = viewType == TYPE_INACTIVE ? R.layout.list_item_row : R.layout.list_item_row_active;

		View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
		return new ViewHolder(v);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Item item = listCust.get(position);

		holder.custRefText.setText(item.getTitle());
		// Highlight the item if it's selected
		//holder.selectedOverlay.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	public int getItemCount() {
		return listCust.size();
	}

	@Override
	public int getItemViewType(int position) {
		final Item item = listCust.get(position);

		return item.isActive() ? TYPE_ACTIVE : TYPE_INACTIVE;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		TextView custRefText;

		public ViewHolder(View itemView) {
			super(itemView);

			custRefText = (TextView) itemView.findViewById(R.id.custref_text);
		}
	}

}