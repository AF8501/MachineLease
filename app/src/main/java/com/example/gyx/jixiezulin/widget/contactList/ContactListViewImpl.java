package com.example.gyx.jixiezulin.widget.contactList;

import android.content.Context;
import android.util.AttributeSet;

import com.example.gyx.jixiezulin.R;

public class ContactListViewImpl extends ContactListView
{

	public ContactListViewImpl(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public void createScroller()
	{

		mScroller = new IndexScroller(getContext(), this);

		mScroller.setAutoHide(autoHide);

		// style 1
		 mScroller.setShowIndexContainer(false);
		 mScroller.setIndexPaintColor(R.color.equipmentlist_indexScroller_text_normal);
		 mScroller.setIndexCurrentPaintColor(R.color.equipmentlist_indexScroller_text_current);

		// style 2
//		mScroller.setShowIndexContainer(true);
//		mScroller.setIndexPaintColor(R.color.equipmentlist_item_sectiontext_normal);

		if (autoHide)
			mScroller.hide();
		else
			mScroller.show();

	}
}
