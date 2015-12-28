package com.jdb.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jdb.demo.adapter.DemoAdapter;
import com.jdb.demo.model.DemoModel;

public class MainActivity extends AppCompatActivity implements DemoAdapter.OnItemClickListener {
	private RecyclerView recyclerView;
	private static final int MENU_SCROLL_POSITION = 1;
	private static final int MENU_TYPE_LINEAR = 2;
	private static final int MENU_TYPE_GRID = 3;
	private static final int MENU_TYPE_STAGGER_GRIDE = 4;
	private RecyclerView.LayoutManager layoutManager;
	private int scrollPosition = 0;
	private DemoAdapter adapter;
	private DemoModel mDemoModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("demo");
		setSupportActionBar(toolbar);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

		setLinearLayoutManager();
		mDemoModel = new DemoModel();
		adapter = new DemoAdapter(this, mDemoModel.demoList);
		adapter.setOnItemClickListener(this);
		recyclerView.setAdapter(adapter);

		initHeaderView();
	}

	private void initHeaderView() {
		View headerView = LayoutInflater.from(this).inflate(R.layout.item_header_layout, recyclerView, false);
		adapter.setHeaderView(headerView);
	}

	private void setLinearLayoutManager() {
		layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		recyclerView.setLayoutManager(layoutManager);
	}

	private void setGridLayoutManager() {
		layoutManager = new GridLayoutManager(this, 2);
		recyclerView.setLayoutManager(layoutManager);
		adapter.onAttachedToRecyclerView(recyclerView);
	}

	private void setStaggerLayoutManager() {
		layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_SCROLL_POSITION, MENU_SCROLL_POSITION, "滚动");
		menu.add(0, MENU_TYPE_LINEAR, MENU_TYPE_LINEAR, "列表");
		menu.add(0, MENU_TYPE_GRID, MENU_TYPE_GRID, "九宫格");
		menu.add(0, MENU_TYPE_STAGGER_GRIDE, MENU_TYPE_STAGGER_GRIDE, "瀑布流");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SCROLL_POSITION:
			recyclerView.smoothScrollToPosition(100);
			break;
		case MENU_TYPE_LINEAR:
			setLinearLayoutManager();
			break;
		case MENU_TYPE_GRID:
			setGridLayoutManager();
			break;
		case MENU_TYPE_STAGGER_GRIDE:
			setStaggerLayoutManager();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(int position) {
		ViewGroup viewGroup = (ViewGroup) layoutManager.findViewByPosition(position + 1);
		TextView textView = new TextView(this);
		textView.setText("我是新加的条目");
		textView.setTextColor(Color.BLUE);
		viewGroup.addView(textView);
	}
}
