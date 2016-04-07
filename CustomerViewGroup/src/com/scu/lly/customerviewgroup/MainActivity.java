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
		tags.add("阳哥你好！");
		tags.add("Android开发");
		tags.add("新闻热点");
		tags.add("热水进宿舍啦！");
		tags.add("I love you");
		tags.add("成都妹子");
		tags.add("新余妹子");
		tags.add("仙女湖");
		tags.add("创新工厂");
		tags.add("孵化园");
		tags.add("神州100发射");
		tags.add("有毒疫苗");
		tags.add("顶你阳哥阳哥");
		tags.add("Hello World");
		tags.add("闲逛的蚂蚁");
		tags.add("闲逛的蚂蚁");
		tags.add("闲逛的蚂蚁");
		tags.add("闲逛的蚂蚁");
		tags.add("闲逛的蚂蚁");
		tags.add("闲逛的蚂蚁");
	}

}
