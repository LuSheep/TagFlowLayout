package com.scu.lly.customerviewgroup.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class CustomerViewGroup extends ViewGroup {
	
	

	public CustomerViewGroup(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomerViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CustomerViewGroup(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}
	
	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams()
	{
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	@Override
	protected ViewGroup.LayoutParams generateLayoutParams(
			ViewGroup.LayoutParams p)
	{
		return new MarginLayoutParams(p);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		
		//计算所有子View的大小
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		
		int width = 0;
		int height = 0;
		
		int tWidth = 0;//上面两个View的总宽度
		int bWidth = 0;//下面两个View的总宽度
		
		int lHeight = 0;//左边两个View的总高度
		int rHeight = 0;//右边两个View的总高度
		
		int cWidth = 0;
		int cHeight = 0;
		
		int count = getChildCount();
		MarginLayoutParams layoutParams;
		for(int i = 0; i < count; i++){
			 View child = getChildAt(i);
			 cWidth = child.getMeasuredWidth();
			 cHeight = child.getMeasuredHeight();
			 layoutParams = (MarginLayoutParams) child.getLayoutParams();
			 
			 if(i == 0 || i == 1){
				 tWidth += cWidth + layoutParams.leftMargin + layoutParams.rightMargin;
			 }
			 if(i == 2 || i == 3){
				 bWidth += cWidth + layoutParams.leftMargin + layoutParams.rightMargin;
			 }
			 if(i == 0 || i == 2){
				 lHeight += cHeight + layoutParams.topMargin + layoutParams.bottomMargin;
			 }
			 if(i == 1 || i ==3){
				 rHeight += cHeight + layoutParams.topMargin + layoutParams.bottomMargin;
			 }
		}
		width = Math.max(tWidth, bWidth);
		height = Math.max(lHeight, rHeight);
		
		setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int count = getChildCount();
//		int cl = 0, ct = 0, cr = 0, cb = 0;
		int cWidth = 0;
		int cHeight = 0;
		MarginLayoutParams layoutParams;
		for(int i = 0; i < count; i++){
			View child = getChildAt(i);
			layoutParams = (MarginLayoutParams) child.getLayoutParams();
			cWidth = child.getMeasuredWidth();
			cHeight = child.getMeasuredHeight();
			int cl = 0, ct = 0, cr = 0, cb = 0;
			System.out.println("getWidth-->"+getWidth());
			System.out.println("i-->"+i+"L:"+layoutParams.leftMargin + " T:" + layoutParams.topMargin + " R:" + layoutParams.rightMargin + " B:" + layoutParams.bottomMargin);
			switch(i){
			case 0:
				cl = layoutParams.leftMargin;
				ct = layoutParams.topMargin;
				break;
			case 1:
				cl = getWidth() - cWidth - layoutParams.leftMargin - layoutParams.rightMargin;
				ct = layoutParams.topMargin;
				break;
			case 2:
				cl = layoutParams.leftMargin;
				ct = getHeight() - cHeight - layoutParams.bottomMargin;
				break;
			case 3:
				cl = getWidth() - cWidth - layoutParams.leftMargin - layoutParams.rightMargin;
				ct = getHeight() - cHeight - layoutParams.bottomMargin;
				break;
			}
			cr = cl + cWidth;
			cb = ct + cHeight;
			
			child.layout(cl, ct, cr, cb);
		}
	}

	

}
