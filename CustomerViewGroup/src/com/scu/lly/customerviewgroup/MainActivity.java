package com.scu.lly.customerviewgroup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.scu.lly.customerviewgroup.view.DynamicTagFlowLayout;
import com.scu.lly.customerviewgroup.view.DynamicTagFlowLayout.OnTagItemClickListener;

public class MainActivity extends Activity {
	
	private DynamicTagFlowLayout dynamicTagFlowLayout;
	
	List<String> tags = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dynamic_tagflowlayout);
		
		dynamicTagFlowLayout = (DynamicTagFlowLayout) findViewById(R.id.dynamic_tag);
		dynamicTagFlowLayout.setOnTagItemClickListener(new OnTagItemClickListener() {
			@Override
			public void onClick(View v) {
				TextView tv = (TextView) v;
				Toast.makeText(MainActivity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
			}
		});
		
		initData();
		dynamicTagFlowLayout.setTags(tags);
	}

	private void initData() {
		tags.add("������ã�");
		tags.add("Android����");
		tags.add("�����ȵ�");
		tags.add("��ˮ����������");
		tags.add("I love you");
		tags.add("�ɶ�����");
		tags.add("��������");
		tags.add("��Ů��");
		tags.add("���¹���");
		tags.add("����԰");
		tags.add("����100����");
		tags.add("�ж�����");
		tags.add("������������");
		tags.add("Hello World");
		tags.add("�й������");
		tags.add("�й������");
		tags.add("�й������");
		tags.add("�й������");
		tags.add("�й������");
		tags.add("�й������");
	}

}
