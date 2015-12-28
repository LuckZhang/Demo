package com.jdb.demo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jdb.demo.R;
import com.jdb.demo.model.DemoModel;

import java.util.List;

/**
 * Created by lenovo on 2015/12/24.
 */
public class DemoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
	private static final int TYPE_COMMON = 1;
	private static final int TYPE_HEADER = 2;
	private List<DemoModel.Demo> mDemoList;
	private Context context;
	private View mHeaderView;

	public DemoAdapter(Context context, List<DemoModel.Demo> demoList) {
		this.context = context;
		this.mDemoList = demoList;
	}

	public void setHeaderView(View headerView) {
		this.mHeaderView = headerView;
		notifyItemInserted(0);

	}

	public View getHeaderView() {
		return mHeaderView;
	}

	@Override
	public int getItemCount() {
		if (mDemoList == null) {
			return mHeaderView == null ? 0 : 1;
		} else {
			return mHeaderView == null ? mDemoList.size() : mDemoList.size() + 1;
		}
	}

	@Override
	public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
		if (layoutManager != null && layoutManager instanceof GridLayoutManager) {
			final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
			gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					return getItemViewType(position) == TYPE_HEADER ? gridLayoutManager.getSpanCount() : 1;
				}
			});
		}
	}

	@Override
	public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
		super.onViewAttachedToWindow(holder);
		ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
		if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams
				&& holder.getLayoutPosition() == 0) {
			StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
			lp.setFullSpan(true);
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (mHeaderView == null) {
			return TYPE_COMMON;
		}
		if (position == 0) {
			return TYPE_HEADER;
		}
		return TYPE_COMMON;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (mHeaderView != null && viewType == TYPE_HEADER) {
			return new HeaderViewHolder(mHeaderView);
		}
		View commonView = LayoutInflater.from(context).inflate(R.layout.item_common_layout, parent, false);
		return new CommonViewHolder(commonView);
	}

	@Override
	public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
		int viewType = getItemViewType(position);
		if (viewType == TYPE_HEADER && mHeaderView != null) {
			return;
		}

		final int realPosition = getRealPosition(holder);
		final DemoModel.Demo demo = mDemoList.get(realPosition);
		CommonViewHolder commonViewHolder = (CommonViewHolder) holder;
		commonViewHolder.tvCommon.setText(demo.content);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(context, demo.content, Toast.LENGTH_SHORT).show();

				if (onItemClickListener != null) {
					onItemClickListener.onItemClick(realPosition);
				}

			}
		});

	}

	private int getRealPosition(RecyclerView.ViewHolder holder) {
		int position = holder.getLayoutPosition();
		return mHeaderView != null ? position - 1 : position;
	}

	public static class CommonViewHolder extends RecyclerView.ViewHolder {
		public TextView tvCommon;

		public CommonViewHolder(View itemView) {
			super(itemView);
			tvCommon = (TextView) itemView.findViewById(R.id.tv_common);
		}
	}

	public static class HeaderViewHolder extends RecyclerView.ViewHolder {

		public HeaderViewHolder(View itemView) {
			super(itemView);
		}

	}

	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface OnItemClickListener {
		void onItemClick(int position);
	}

}
