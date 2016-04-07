package com.scu.lly.customerviewgroup.view;

import java.util.ArrayList;
import java.util.List;

import com.scu.lly.customerviewgroup.R;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 流式标签(动态的，根据传入的数据动态添加标签)
 */
public class DynamicTagFlowLayout extends ViewGroup {
	
	private List<String> mTags = new ArrayList<String>();
	
	public DynamicTagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public DynamicTagFlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DynamicTagFlowLayout(Context context) {
		super(context);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		//当前ViewGroup的总高度
		int totalHeight= 0;
		//所有行中的最大宽度
		int maxLineWidth = 0;
		
		//当前行的最大高度
		int lineMaxHeight = 0;
		//当前行的总宽度
		int currentLineWidth = 0;
		
		//每个childView所占用的宽度
		int childViewWidthSpace = 0;
		//每个childView所占用的高度
		int childViewHeightSpace = 0;
		
		int count = getChildCount();
		MarginLayoutParams layoutParams;
		
		for(int i = 0; i < count; i++){
			View child = getChildAt(i);
			
			if(child.getVisibility() != View.GONE){//只有当这个View能够显示的时候才去测量
				//测量每个子View，以获取子View的宽和高
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
				
				layoutParams = (MarginLayoutParams) child.getLayoutParams();
				
				childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
				childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
				
				if(currentLineWidth + childViewWidthSpace > widthSize){//表示如果当前行再加上现在这个子View，就会超出总的规定宽度，需要另起一行
					totalHeight += lineMaxHeight;
					if(maxLineWidth < currentLineWidth){//如果行的最长宽度发生了变化，更新保存的最长宽度
						maxLineWidth = currentLineWidth;
					}
					currentLineWidth = childViewWidthSpace;//另起一行后，需要重置当前行宽
					lineMaxHeight = childViewHeightSpace;
				}else{//表示当前行可以继续添加子元素
					currentLineWidth += childViewWidthSpace;
					if(lineMaxHeight < childViewHeightSpace){
						lineMaxHeight = childViewHeightSpace;
					}
				}
			}
		}
		
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxLineWidth, heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
		
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		//当前是第几行
		int currentLine = 1;
		//存放每一行的最大高度
		List<Integer> lineMaxHeightList = new ArrayList<Integer>();
		
		//每个childView所占用的宽度
		int childViewWidthSpace = 0;
		//每个childView所占用的高度
		int childViewHeightSpace = 0;
		
		//当前行的最大高度
		int lineMaxHeight = 0;
		//当前行的总宽度
		int currentLineWidth = 0;
		
		int count = getChildCount();
		MarginLayoutParams layoutParams;
		
		for(int i = 0; i < count; i++){
			int cl= 0, ct = 0, cr = 0, cb = 0;
			View child = getChildAt(i);
			if(child.getVisibility() != View.GONE){//只有当这个View能够显示的时候才去测量
			
				layoutParams = (MarginLayoutParams) child.getLayoutParams();
				childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
				childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
				
				System.out.println("getWidth()---->"+getWidth());
				
				if(currentLineWidth + childViewWidthSpace > getWidth()){//表示如果当前行再加上现在这个子View，就会超出总的规定宽度，需要另起一行
					lineMaxHeightList.add(lineMaxHeight);//此时先将这一行的最大高度加入到集合中
					//新的一行，重置一些参数
					currentLine++;
					currentLineWidth = childViewWidthSpace;
					lineMaxHeight = childViewHeightSpace;
					
					cl = layoutParams.leftMargin;
					if(currentLine > 1){
						for(int j = 0; j < currentLine - 1; j++){
							ct += lineMaxHeightList.get(j);
						}
						ct += layoutParams.topMargin ;
					}else{
						ct = layoutParams.topMargin;
					}
				}else{//表示当前行可以继续添加子元素
					cl = currentLineWidth + layoutParams.leftMargin;
					if(currentLine > 1){
						for(int j = 0; j < currentLine - 1; j++){
							ct += lineMaxHeightList.get(j);
						}
						ct += layoutParams.topMargin;
					}else{
						ct = layoutParams.topMargin;
					}
					currentLineWidth += childViewWidthSpace;
					if(lineMaxHeight < childViewHeightSpace){
						lineMaxHeight = childViewHeightSpace;
					}
				}
				
				cr = cl + child.getMeasuredWidth();
				cb = ct + child.getMeasuredHeight();
				
				child.layout(cl, ct, cr, cb);
			
			}
		}
	}
	
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
	
	public void setTags(List<String> tags){
		if(tags!= null){
			mTags.clear();
			mTags.addAll(tags);
			for(int i = 0; i < mTags.size(); i++){
				TextView tv = new TextView(getContext());
				MarginLayoutParams lp = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
				lp.setMargins(15, 15, 15, 15);
//				lp.width = MarginLayoutParams.WRAP_CONTENT;
//				lp.height = MarginLayoutParams.WRAP_CONTENT;
				tv.setLayoutParams(lp);
				tv.setBackgroundResource(R.drawable.tv_bg);
				/*
				 * setPadding一定要在setBackgroundResource后面使用才有效！！！
				 * http://stackoverflow.com/questions/18327498/setting-padding-for-textview-not-working
				 */
				tv.setPadding(15, 15, 15, 15);
				tv.setTextColor(Color.WHITE);
				
				tv.setText(mTags.get(i));
				
				tv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(listener != null){
							listener.onClick(v);
						}
					}
				});
				
				addView(tv);
			}
			requestLayout();
		}
	}
	
	private OnTagItemClickListener listener;
	public interface OnTagItemClickListener{
		public void onClick(View v);
	}
	public void setOnTagItemClickListener(OnTagItemClickListener l){
		listener = l;
	}

}
