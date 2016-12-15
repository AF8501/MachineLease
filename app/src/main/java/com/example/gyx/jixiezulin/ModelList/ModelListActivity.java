package com.example.gyx.jixiezulin.ModelList;

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

import com.example.gyx.jixiezulin.Common.CommonUtil;
import com.example.gyx.jixiezulin.EquipmentList.EquipmentListActivity;
import com.example.gyx.jixiezulin.EquipmentList.data.EquipmentData;
import com.example.gyx.jixiezulin.ModelList.adapter.ModelAdapter;
import com.example.gyx.jixiezulin.ModelList.data.ModelData;
import com.example.gyx.jixiezulin.ModelList.model.ModelItem;
import com.example.gyx.jixiezulin.R;
import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;
import com.example.gyx.jixiezulin.widget.contactList.ContactListViewImpl;

import java.util.ArrayList;
import java.util.List;


public class ModelListActivity extends AppCompatActivity{
	private ContactListViewImpl listview;

	private EditText searchBox;
	private String searchString;

	private TextView txTitle;
	private ImageButton imgBack;


	private Object searchLock = new Object();
	boolean inSearchMode = false;

	private final static String TAG = "ModelListActivity";
	public final static String MODEL_ADD = "selected_model";

	List<ContactItemInterface> contactList;
	List<ContactItemInterface> filterList;
	private SearchListTask curSearchTask = null;
	private ModelData data;

	private Intent intent;

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
		data = new ModelData(ModelData.modelJson);
		contactList = data.getItemList() ;

		final ModelAdapter adapter = new ModelAdapter(this,
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
				bundle.putString(MODEL_ADD,mEquipment);
				intent.putExtra(MODEL_ADD,bundle);
				setResult(RESULT_OK,intent);
				CommonUtil.hideSoftKeyboard(ModelListActivity.this,searchBox);
				finish();
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
					ModelItem contact = (ModelItem) item;

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

					ModelAdapter adapter = new ModelAdapter(ModelListActivity.this,
							R.layout.equipment_item, filterList);
					adapter.setInSearchMode(true);
					listview.setInSearchMode(true);
					listview.setAdapter(adapter);
				} else
				{
					ModelAdapter adapter = new ModelAdapter(ModelListActivity.this,
							R.layout.equipment_item, contactList);
					adapter.setInSearchMode(false);
					listview.setInSearchMode(false);
					listview.setAdapter(adapter);
				}
			}

		}
	}

}
