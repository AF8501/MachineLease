package com.example.gyx.jixiezulin.EquipmentList.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gyx.jixiezulin.R;
import com.example.gyx.jixiezulin.widget.contactList.ContactItemInterface;
import com.example.gyx.jixiezulin.widget.contactList.ContactListAdapter;

public class EquipmentAdapter extends ContactListAdapter
{
	private int selectionPosition;
	private int resource;
	private Context context;
	public EquipmentAdapter(Context _context, int _resource,
							List<ContactItemInterface> _items)
	{
		super(_context, _resource, _items);
		selectionPosition = -1;
		resource = _resource;
		context = _context;
	}

	public void populateDataForRow(View parentView, ContactItemInterface item,
			int position)
	{
		View infoView = parentView.findViewById(R.id.infoRowContainer);
		TextView nicknameView = (TextView) infoView
				.findViewById(R.id.EquipmentName);

		nicknameView.setText(item.getDisplayInfo());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewGroup parentView;

		ContactItemInterface item = getItem(position);

		if (convertView == null)
		{
			parentView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					inflater);
			vi.inflate(resource, parentView, true);
		} else
		{
			parentView = (LinearLayout) convertView;
		}
		RelativeLayout infoRowContainer = (RelativeLayout)parentView.findViewById(R.id.infoRowContainer);
		if(selectionPosition==position){
			infoRowContainer.setBackgroundColor(context.getResources().getColor(R.color.equipmentlist_clicked));
		}else {
			infoRowContainer.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		showSectionViewIfFirstItem(parentView, item, position);

		populateDataForRow(parentView, item, position);

		return parentView;
	}

	public void setSelectionPosition(int selectionPosition) {
		this.selectionPosition = selectionPosition;
	}
}
