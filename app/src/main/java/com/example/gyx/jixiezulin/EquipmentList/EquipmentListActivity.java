package com.example.gyx.jixiezulin.EquipmentList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gyx.jixiezulin.Common.CommonUtil;
import com.example.gyx.jixiezulin.EquipmentList.adapter.EquipmentAdapter;
import com.example.gyx.jixiezulin.EquipmentList.data.EquipmentData;
import com.example.gyx.jixiezulin.EquipmentList.model.EquipmentItem;
import com.example.gyx.jixiezulin.EquipmentList.adapter.HistorySearchGridAdapter;
import com.example.gyx.jixiezulin.EquipmentList.adapter.HotSearchGridAdapter;
import com.example.gyx.jixiezulin.EquipmentList.EquipmentListActivity;
import com.example.gyx.jixiezulin.widget.SoftKeyboardStateHelper;
import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;
import com.example.gyx.jixiezulin.widget.contactList.ContactListViewImpl;
import com.example.gyx.jixiezulin.R;

/**
 * 机械互联-03机械租赁填写交互-01设备类型列表
 * 机械互联-03机械租赁填写交互-02设备类型列表(字母表点击交互)
 * 机械互联-03机械租赁填写交互-03设备类型列表(列表点击交互)
 */
public class EquipmentListActivity extends AppCompatActivity{
	private ContactListViewImpl listview;

	private EditText searchBox;
	private String searchString;

	private TextView txTitle;
	private ImageButton imgBack;

	private GridView gvHotSearch;//历史搜索
	private GridView gvHistorySear;//热门搜索
	private boolean isShowSearch = true;

	private Object searchLock = new Object();
	boolean inSearchMode = false;

	private final static String TAG = "mEquipmentList";

	List<ContactItemInterface> contactList;
	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;

	private EquipmentData data;//设备类型数据类

	private Intent intent;
	public final static String Equipment_ADD = "selected_Equipment";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipmentlist);
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		txTitle = (TextView)findViewById(R.id.toolbar_title);
		txTitle.setText(R.string.EquipmentActivity_title);

		imgBack = (ImageButton)findViewById(R.id.toolbar_back);
		imgBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		filterList = new ArrayList<ContactItemInterface>();

		//Todo:请在此处传入设备列表数据，Json或者List<String>，下为示例代码

		//测试用:传入json数据，这里传入实验数据。
		data = new EquipmentData(EquipmentData.EquipmentJson);
		contactList = data.getItemList() ;

		final EquipmentAdapter adapter = new EquipmentAdapter(this,
				R.layout.equipment_item, contactList);

		listview = (ContactListViewImpl) findViewById(R.id.listview);
		listview.setFastScrollEnabled(true);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView parent, View v, int position,
					long id)
			{
				//列表点击交互：点击item变色
				adapter.setSelectionPosition(position);
				adapter.notifyDataSetChanged();
				//获取点击item数据
				List<ContactItemInterface> searchList = inSearchMode ? filterList
						: contactList;
				String mEquipment = searchList.get(position).getDisplayInfo();
				intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString(Equipment_ADD,mEquipment);
				intent.putExtra(Equipment_ADD,bundle);
				setResult(RESULT_OK,intent);
				CommonUtil.hideSoftKeyboard(EquipmentListActivity.this,searchBox);
				finish();
			}
		});

		gvHotSearch = (GridView) findViewById(R.id.gridview_hotSearch);
		final HotSearchGridAdapter hotAdapter = new HotSearchGridAdapter(this, R.layout.hotsearch_item, getHotSearchData());
		gvHotSearch.setAdapter(hotAdapter);
		gvHotSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				hotAdapter.setSelectedPostion(position);
				hotAdapter.notifyDataSetChanged();
				//开始搜索,这里搜索只有向searchBox中写入数据即可，下面有监听器，会自动搜索。
				TextView tx = (TextView) view.findViewById(R.id.tx_hotsearch_item);
				searchBox.setText(tx.getText());
				searchBox.setSelection(tx.getText().length());//移动光标到末尾。
			}
		});

		gvHistorySear = (GridView) findViewById(R.id.gridview_historySearch);
		final HistorySearchGridAdapter historyAdapter = new HistorySearchGridAdapter(this, R.layout.historysearch_item, getHistorySearchData());
		gvHistorySear.setAdapter(historyAdapter);
		gvHistorySear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				historyAdapter.setSelectedPostion(position);
				historyAdapter.notifyDataSetChanged();
				//开始搜索,这里搜索只有向searchBox中写入数据即可，下面有监听器，会自动搜索。
				TextView tx = (TextView) view.findViewById(R.id.tx_histsearch_item);
				searchBox.setText(tx.getText());
				searchBox.setSelection(tx.getText().length());//移动光标到末尾。
			}
		});

		searchBox = (EditText) findViewById(R.id.input_search_query);

		//监听软键盘状态（打开或收起）
		listenSoftKeyboardState(searchBox);

		//输入完毕开始搜索
		searchBox.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {


				searchString = searchBox.getText().toString().trim().toUpperCase();

				isShowSearch = false;
				showSearch(false);
				showShake(false);

				if (curSearchTask != null
						&& curSearchTask.getStatus() != AsyncTask.Status.FINISHED)
				{
					try
					{
						curSearchTask.cancel(true);
					} catch (Exception e)
					{
						Log.i(TAG, "Fail to cancel running search task");
					}

				}
				curSearchTask = new SearchListTask();
				curSearchTask.execute(searchString);
			}
		});
	}

	private List getHistorySearchData() {
		String[] s = {"挖掘机","挖土机"};
		return Arrays.asList(s);
	}

	private List getHotSearchData() {
		String[] s = {"挖土机","搅拌车","挖掘机"};
		return Arrays.asList(s);
	}

	private void listenSoftKeyboardState(EditText searchBox) {
		final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.listview));
		softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
			@Override
			public void onSoftKeyboardOpened(int keyboardHeightInPx) {
				showSearch(true);
				showShake(true);
			}

			@Override
			public void onSoftKeyboardClosed() {
				showSearch(true);
				showShake(false);
			}
		});
	}



	/**
	 * 显示或隐藏热门搜索和历史搜索
	 * @param show true:显示
     */
	private void showSearch(boolean show) {
		LinearLayout llHotsearch = (LinearLayout)findViewById(R.id.hotsearch_container);
		if(show){
			if(isShowSearch)
				llHotsearch.setVisibility(View.VISIBLE);
		}else {
			llHotsearch.setVisibility(View.INVISIBLE);
		}

	}

	/**
	 * 显示或隐藏热门搜索和历史搜索
	 * @param show true:显示
	 */

	private void showShake(boolean show){
		View shakeView = (View)findViewById(R.id.rl_equipment_search_shade);
		if(show){
			shakeView.setVisibility(View.VISIBLE);
		}else {
			shakeView.setVisibility(View.INVISIBLE);
		}

	}




	private class SearchListTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			filterList.clear();

			String keyword = params[0];

			inSearchMode = (keyword.length() > 0);

			if (inSearchMode)
			{
				// get all the items matching this
				for (ContactItemInterface item : contactList)
				{
					EquipmentItem contact = (EquipmentItem) item;

					boolean isPinyin = contact.getFullName().toUpperCase()
							.indexOf(keyword) > -1;//upper case 大写
					boolean isChinese = contact.getNickName().indexOf(keyword) > -1;

					if (isPinyin || isChinese)
					{
						filterList.add(item);
					}

				}

			}
			return null;
		}

		protected void onPostExecute(String result)
		{

			synchronized (searchLock)
			{

				if (inSearchMode)
				{

					EquipmentAdapter adapter = new EquipmentAdapter(EquipmentListActivity.this,
							R.layout.equipment_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else
				{
					EquipmentAdapter adapter = new EquipmentAdapter(EquipmentListActivity.this,
							R.layout.equipment_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

}
