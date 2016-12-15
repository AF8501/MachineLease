package com.example.gyx.jixiezulin.widget.contactList;

import android.widget.ListView;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;

public class ContactListView extends ListView
{

	protected boolean mIsFastScrollEnabled = false;
	protected IndexScroller mScroller = null;
	protected GestureDetector mGestureDetector = null;

	// additional customization
	protected boolean inSearchMode = false; // whether is in search mode
	protected boolean autoHide = false; // alway show the scroller

	private ContactItemInterface item;

	public ContactListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public ContactListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public IndexScroller getScroller()
	{
		return mScroller;
	}

	@Override
	public boolean isFastScrollEnabled()
	{
		return mIsFastScrollEnabled;
	}

	// override this if necessary for custom scroller
	public void createScroller()
	{
		mScroller = new IndexScroller(getContext(), this);
		mScroller.setAutoHide(autoHide);
		mScroller.setShowIndexContainer(true);

		if (autoHide)
			mScroller.hide();
		else
			mScroller.show();
	}

	@Override
	public void setFastScrollEnabled(boolean enabled)
	{
		mIsFastScrollEnabled = enabled;
		if (mIsFastScrollEnabled)
		{
			if (mScroller == null)
			{
				createScroller();
			}
		} else
		{
			if (mScroller != null)
			{
				mScroller.hide();
				mScroller = null;
			}
		}
	}

	@Override
	public void draw(Canvas canvas)
	{
		super.draw(canvas);

		// Overlay index bar
		if (!inSearchMode) // dun draw the scroller if not in search mode
		{
			if (mScroller != null){
				item = (ContactItemInterface)getItemAtPosition(getFirstVisiblePosition());
				String letter = item.getItemForIndex().substring(0,1);
				int letterNum = getLetterNum(letter)+1;//用于指示当前第一个列表项目的开关英文字母，以便IndexSrcoller加亮显示
				mScroller.draw(canvas,letterNum);
			}
		}

	}

	/**
	 *
	 * @param letter 英文字母
	 * @return 字母在字母表中对应的序号。
     */
	private int getLetterNum(String letter) {
		if(letter.equals("a")||letter.equals("A"))
			return 0;
		if(letter.equals("b")||letter.equals("B"))
			return 1;
		if(letter.equals("c")||letter.equals("C"))
			return 2;
		if(letter.equals("d")||letter.equals("D"))
			return 3;
		if(letter.equals("e")||letter.equals("E"))
			return 4;
		if(letter.equals("f")||letter.equals("F"))
			return 5;
		if(letter.equals("g")||letter.equals("G"))
			return 6;
		if(letter.equals("h")||letter.equals("H"))
			return 7;
		if(letter.equals("i")||letter.equals("I"))
			return 8;
		if(letter.equals("j")||letter.equals("J"))
			return 9;
		if(letter.equals("k")||letter.equals("K"))
			return 10;
		if(letter.equals("l")||letter.equals("L"))
			return 11;
		if(letter.equals("m")||letter.equals("M"))
			return 12;
		if(letter.equals("n")||letter.equals("N"))
			return 13;
		if(letter.equals("o")||letter.equals("O"))
			return 14;
		if(letter.equals("p")||letter.equals("P"))
			return 15;
		if(letter.equals("q")||letter.equals("Q"))
			return 16;
		if(letter.equals("r")||letter.equals("R"))
			return 17;
		if(letter.equals("s")||letter.equals("S"))
			return 18;
		if(letter.equals("t")||letter.equals("T"))
			return 19;
		if(letter.equals("u")||letter.equals("U"))
			return 20;
		if(letter.equals("v")||letter.equals("V"))
			return 21;
		if(letter.equals("w")||letter.equals("W"))
			return 22;
		if(letter.equals("x")||letter.equals("X"))
			return 23;
		if(letter.equals("y")||letter.equals("Y"))
			return 24;
		if(letter.equals("z")||letter.equals("Z"))
			return 25;
		return 0;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		// Intercept ListView's touch event
		if (mScroller != null && mScroller.onTouchEvent(ev))
			return true;

		if (mGestureDetector == null)
		{
			mGestureDetector = new GestureDetector(getContext(),
					new GestureDetector.SimpleOnGestureListener()
					{

						@Override
						public boolean onFling(MotionEvent e1, MotionEvent e2,
								float veloEquipmentX, float veloEquipmentY)
						{
							// If fling happens, index bar shows
							mScroller.show();
							return super.onFling(e1, e2, veloEquipmentX, veloEquipmentY);
						}

					});
		}
		mGestureDetector.onTouchEvent(ev);

		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		return true;
	}

	@Override
	public void setAdapter(ListAdapter adapter)
	{
		super.setAdapter(adapter);
		if (mScroller != null)
			mScroller.setAdapter(adapter);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		if (mScroller != null)
			mScroller.onSizeChanged(w, h, oldw, oldh);
	}

	public boolean isInSearchMode()
	{
		return inSearchMode;
	}

	public void setInSearchMode(boolean inSearchMode)
	{
		this.inSearchMode = inSearchMode;
	}

}
