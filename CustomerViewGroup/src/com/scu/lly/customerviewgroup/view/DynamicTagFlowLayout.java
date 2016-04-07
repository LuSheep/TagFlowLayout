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
 * ��ʽ��ǩ(��̬�ģ����ݴ�������ݶ�̬��ӱ�ǩ)
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
		
		//��ǰViewGroup���ܸ߶�
		int totalHeight= 0;
		//�������е������
		int maxLineWidth = 0;
		
		//��ǰ�е����߶�
		int lineMaxHeight = 0;
		//��ǰ�е��ܿ��
		int currentLineWidth = 0;
		
		//ÿ��childView��ռ�õĿ��
		int childViewWidthSpace = 0;
		//ÿ��childView��ռ�õĸ߶�
		int childViewHeightSpace = 0;
		
		int count = getChildCount();
		MarginLayoutParams layoutParams;
		
		for(int i = 0; i < count; i++){
			View child = getChildAt(i);
			
			if(child.getVisibility() != View.GONE){//ֻ�е����View�ܹ���ʾ��ʱ���ȥ����
				//����ÿ����View���Ի�ȡ��View�Ŀ�͸�
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
				
				layoutParams = (MarginLayoutParams) child.getLayoutParams();
				
				childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
				childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
				
				if(currentLineWidth + childViewWidthSpace > widthSize){//��ʾ�����ǰ���ټ������������View���ͻᳬ���ܵĹ涨��ȣ���Ҫ����һ��
					totalHeight += lineMaxHeight;
					if(maxLineWidth < currentLineWidth){//����е����ȷ����˱仯�����±��������
						maxLineWidth = currentLineWidth;
					}
					currentLineWidth = childViewWidthSpace;//����һ�к���Ҫ���õ�ǰ�п�
					lineMaxHeight = childViewHeightSpace;
				}else{//��ʾ��ǰ�п��Լ��������Ԫ��
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
		//��ǰ�ǵڼ���
		int currentLine = 1;
		//���ÿһ�е����߶�
		List<Integer> lineMaxHeightList = new ArrayList<Integer>();
		
		//ÿ��childView��ռ�õĿ��
		int childViewWidthSpace = 0;
		//ÿ��childView��ռ�õĸ߶�
		int childViewHeightSpace = 0;
		
		//��ǰ�е����߶�
		int lineMaxHeight = 0;
		//��ǰ�е��ܿ��
		int currentLineWidth = 0;
		
		int count = getChildCount();
		MarginLayoutParams layoutParams;
		
		for(int i = 0; i < count; i++){
			int cl= 0, ct = 0, cr = 0, cb = 0;
			View child = getChildAt(i);
			if(child.getVisibility() != View.GONE){//ֻ�е����View�ܹ���ʾ��ʱ���ȥ����
			
				layoutParams = (MarginLayoutParams) child.getLayoutParams();
				childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
				childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
				
				System.out.println("getWidth()---->"+getWidth());
				
				if(currentLineWidth + childViewWidthSpace > getWidth()){//��ʾ�����ǰ���ټ������������View���ͻᳬ���ܵĹ涨��ȣ���Ҫ����һ��
					lineMaxHeightList.add(lineMaxHeight);//��ʱ�Ƚ���һ�е����߶ȼ��뵽������
					//�µ�һ�У�����һЩ����
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
				}else{//��ʾ��ǰ�п��Լ��������Ԫ��
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
				 * setPaddingһ��Ҫ��setBackgroundResource����ʹ�ò���Ч������
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
