package com.example.gyx.jixiezulin.widget.WheelView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 滚动选择器 更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38513301
 * 
 * @author chenjing
 * 
 */
public class PickerView extends View
{

	public static final String TAG = "PickerView";
	private Context context;
	/**
	 * text之间间距和minTextSize之比
	 */
	public static final float MARGIN_ALPHA = 1f;
	/**
	 * 自动回滚到中间的速度
	 */
	public static final float SPEED = 10;

	private List<String> mDataList;
	/**
	 * 选中的位置，这个位置是mDataList的中心位置，一直不变
	 */
	private int mCurrentSelected;
	private Paint mPaint;

	private float mMaxTextSize;
	private float mMinTextSize;

	private float mMaxTextAlpha;
	private float mMinTextAlpha;

	private int mColorText = 0x333333;
	private String mCurrentColorText = "#fab514";
	private String mOtherColorText = "#999999";

	private int mViewHeight;
	private int mViewWidth;

	private float mLastDownY;
	/**
	 * 滑动的距离
	 */
	private float mMoveLen = 0;
	private boolean isInit = false;
	private onSelectListener mSelectListener;
	private Timer timer;
	private MyTimerTask mTask;

	Handler updateHandler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			if (Math.abs(mMoveLen) < SPEED)
			{
				mMoveLen = 0;
				if (mTask != null)
				{
					mTask.cancel();
					mTask = null;
					performSelect();
				}
			} else
				// 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
				mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
			invalidate();
		}

	};

	public PickerView(Context context)
	{
		super(context);
		this.context = context;
		init(null);
	}

	public PickerView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		init(attrs);
	}

	public void setOnSelectListener(onSelectListener listener)
	{
		mSelectListener = listener;
	}

	private void performSelect()
	{
		if (mSelectListener != null)
			mSelectListener.onSelect(mDataList.get(mCurrentSelected));
	}

	public void setData(List<String> datas)
	{

		mDataList = datas;

		mCurrentSelected = datas.size() / 2;
		//用于判断是否到底，在尾部加3个空值，具体加几个，看个人需求，或者控件本身的高度。
		//多加几个是为了首尾永不相见。
		for(int i=0;i<3;i++){
			mDataList.add(mDataList.size(),"");
		}
		performSelect();//为了让不滑动也能触发一次onSelectListener。
		invalidate();
	}

	/**
	 * 选择选中的item的index
	 * 
	 * @param selected
	 */
	public void setSelected(int selected)
	{
		mCurrentSelected = selected;
		if(mDataList==null) return;
		int distance = mDataList.size() / 2 - mCurrentSelected;
		if (distance < 0)
			for (int i = 0; i < -distance; i++)
			{
				moveHeadToTail();
				mCurrentSelected--;
			}
		else if (distance > 0)
			for (int i = 0; i < distance; i++)
			{
				moveTailToHead();
				mCurrentSelected++;
			}
		performSelect();
		invalidate();
	}

	/**
	 * 选择选中的内容
	 * 
	 * @param mSelectItem
	 */
	public void setSelected(String mSelectItem)
	{
		for (int i = 0; i < mDataList.size(); i++)
			if (mDataList.get(i).equals(mSelectItem))
			{
				setSelected(i);
				break;
			}
	}

	private void moveHeadToTail()
	{
		String head = mDataList.get(0);
		mDataList.remove(0);
		mDataList.add(head);
	}

	/**
	 * 往上滑，
	 * 如果已经到最后一个元素了，则返回false,
	 */
	private boolean moveUp(){
		if(mDataList.get(mCurrentSelected+1).equals("")){
			return false;
		}else {
			moveHeadToTail();
			return true;
		}
	}
	/**
	 * 往下滑，头部加空值，尾部去空值
	 * 如果已经到第一个元素了，则返回true,
	 */
	private boolean moveDown(){

		if(mDataList.get(mCurrentSelected-1).equals("")){
			return false;
		}else {
			moveTailToHead();
			return true;
		}
	}

	private void moveTailToHead()
	{
		String tail = mDataList.get(mDataList.size() - 1);
		mDataList.remove(mDataList.size() - 1);
		mDataList.add(0, tail);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mViewHeight = getMeasuredHeight();
		mViewWidth = getMeasuredWidth();
		// 按照View的高度计算字体大小
		mMaxTextSize = mViewHeight / 4.0f;
		mMinTextSize = mViewHeight / 7.0f;
		isInit = true;
		invalidate();
	}

	private void init(AttributeSet attrs)
	{
		timer = new Timer();
		mDataList = new ArrayList<String>();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setColor(mColorText);
		mMaxTextAlpha = 255;
		mMinTextAlpha = 0;
		mCurrentSelected = -1;//表明未初始化状态
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		// 根据index绘制view
		if (isInit)
			drawData(canvas);
	}

	private void drawData(Canvas canvas)
	{
		// 先绘制选中的text再往上往下绘制其余的text
		float scale = parabola(mViewHeight / 6.0f, mMoveLen);
		float size = (dip2px(16)-dip2px(13.3f))*scale+dip2px(13.2f);
		mPaint.setTextSize(size);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		int c = Color.parseColor(mCurrentColorText);
		int d = Color.parseColor(mOtherColorText);
		mPaint.setColor(Color.parseColor(mCurrentColorText));
		// text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
		float x = (float) (mViewWidth / 2.0);
		float y = (float) (mViewHeight / 2.0 + mMoveLen);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

		canvas.drawText(mDataList.get(mCurrentSelected), x, baseline, mPaint);
		// 绘制上方data
		for (int i = 1; (mCurrentSelected - i) >= 0; i++)
		{
			drawOtherText(canvas, i, -1);
		}
		// 绘制下方data
		for (int i = 1; (mCurrentSelected + i) < mDataList.size(); i++)
		{
			drawOtherText(canvas, i, 1);
		}

	}

	/**
	 * @param canvas
	 * @param position
	 *            距离mCurrentSelected的差值
	 * @param type
	 *            1表示向下绘制，-1表示向上绘制
	 */
	private void drawOtherText(Canvas canvas, int position, int type)
	{
		float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type
				* mMoveLen);

		float scale = parabola(mViewHeight / 6.0f, d);
		float size = (dip2px(16)-dip2px(13.3f))*scale+dip2px(13.2f);
		mPaint.setTextSize(size);
		mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
		mPaint.setColor(Color.parseColor(mOtherColorText));
		float y = (float) (mViewHeight / 2.0 + type * d);
		FontMetricsInt fmi = mPaint.getFontMetricsInt();
		float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
		canvas.drawText(mDataList.get(mCurrentSelected + type * position),
				(float) (mViewWidth / 2.0), baseline, mPaint);

	}

	/**
	 * 抛物线
	 * 
	 * @param zero
	 *            零点坐标
	 * @param x
	 *            偏移量
	 * @return scale
	 */
	private float parabola(float zero, float x)
	{
		float f = (float) (1 - Math.pow(x / zero, 2));
		return f < 0 ? 0 : f;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getActionMasked())
		{
		case MotionEvent.ACTION_DOWN:
			doDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			doMove(event);
			break;
		case MotionEvent.ACTION_UP:
			doUp(event);
			break;
		}
		return true;
	}

	private void doDown(MotionEvent event)
	{
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mLastDownY = event.getY();
	}

	private void doMove(MotionEvent event)
	{

		mMoveLen += (event.getY() - mLastDownY);

		if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2)
		{
			// 往下滑超过离开距离
			//循环滚动
			//moveTailToHead();
			//不循环滚动
			if(!moveDown()){
				mMoveLen -= (event.getY() - mLastDownY);
			}else {
				mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;
			}

		} else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2)
		{
			// 往上滑超过离开距离
			//moveHeadToTail();
			if(!moveUp()){
				mMoveLen -= (event.getY() - mLastDownY);

			}else {
				mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
			}
		}

		mLastDownY = event.getY();
		invalidate();
	}

	private void doUp(MotionEvent event)
	{
		// 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
		if (Math.abs(mMoveLen) < 0.0001)
		{
			mMoveLen = 0;
			return;
		}
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	class MyTimerTask extends TimerTask
	{
		Handler handler;

		public MyTimerTask(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run()
		{
			handler.sendMessage(handler.obtainMessage());
		}

	}

	public interface onSelectListener
	{
		void onSelect(String text);
	}

	private int dip2px(float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
