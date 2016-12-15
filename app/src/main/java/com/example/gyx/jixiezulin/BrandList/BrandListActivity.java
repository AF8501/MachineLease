package com.example.gyx.jixiezulin.BrandList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.gyx.jixiezulin.BrandList.adapter.BrandAdapter;
import com.example.gyx.jixiezulin.BrandList.data.BrandData;
import com.example.gyx.jixiezulin.BrandList.model.BrandItem;
import com.example.gyx.jixiezulin.Common.CommonUtil;
import com.example.gyx.jixiezulin.EquipmentList.EquipmentListActivity;
import com.example.gyx.jixiezulin.EquipmentList.data.EquipmentData;
import com.example.gyx.jixiezulin.ModelList.ModelListActivity;
import com.example.gyx.jixiezulin.R;
import com.example.gyx.jixiezulin.widget.SoftKeyboardStateHelper;
import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;
import com.example.gyx.jixiezulin.widget.contactList.ContactListViewImpl;

import java.util.ArrayList;
import java.util.List;



public class BrandListActivity extends AppCompatActivity{
	private ContactListViewImpl listview;

	private EditText searchBox;
	private String searchString;

	private TextView txTitle;
	private ImageButton imgBack;


	private Object searchLock = new Object();
	boolean inSearchMode = false;

	private final static String TAG = "mEquipmentList";
	public final static String BRAND_ADD = "selected_brand";
	public final static int MODEL_ACTIVITY = 9502;

	List<ContactItemInterface> contactList;
	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;
	private BrandData data;//设备类型数据类

	private String selectedBrand;


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_equipmentlist);
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		txTitle = (TextView)findViewById(R.id.toolbar_title);
		txTitle.setText(R.string.BrandActivity_title);
		//设置粗体
		TextPaint tp = txTitle.getPaint();
		tp.setFakeBoldText(true);

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
		data = new BrandData(BrandData.BrandJson);
		contactList = data.getItemList() ;

		final BrandAdapter adapter = new BrandAdapter(this,
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
				selectedBrand = searchList.get(position).getDisplayInfo();

				//启动型号列表页面
				startModelListActivity();
			}
		});



		searchBox = (EditText) findViewById(R.id.input_search_query);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode){
			case MODEL_ACTIVITY:
				if(resultCode==RESULT_OK){
					Bundle bundle = data.getBundleExtra(ModelListActivity.MODEL_ADD);
					bundle.putString(BRAND_ADD,selectedBrand);
					Intent intent = new Intent();
					intent.putExtra(BRAND_ADD,bundle);
					setResult(RESULT_OK,intent);
					CommonUtil.hideSoftKeyboard(BrandListActivity.this,searchBox);
					finish();
				}
				break;
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
					BrandItem contact = (BrandItem) item;

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

					BrandAdapter adapter = new BrandAdapter(BrandListActivity.this,
							R.layout.equipment_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else
				{
					BrandAdapter adapter = new BrandAdapter(BrandListActivity.this,
							R.layout.equipment_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

	private void startModelListActivity() {
		Intent intent = new Intent(BrandListActivity.this,ModelListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString(BRAND_ADD,selectedBrand);
		intent.putExtra(BRAND_ADD,bundle);
		startActivityForResult(intent,MODEL_ACTIVITY);
	}

}
