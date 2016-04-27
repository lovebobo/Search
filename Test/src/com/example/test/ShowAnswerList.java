package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ShowAnswerList extends Activity {

	
	private ListView listView;
	
	private listviewAdapter<danci>  listviewadapter;
	
	private ArrayList<danci> AnsList=new ArrayList<danci>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_answer_list);
		
		AnsList=(ArrayList<danci>) getIntent().getExtras().getSerializable("AnsList");
		
		initView();
		
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		listView=(ListView)findViewById(R.id.list_ans);
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		listviewadapter=new listviewAdapter<danci>(ShowAnswerList.this, R.layout.ans_item, AnsList);
		listView.setAdapter(listviewadapter);
		
	}

	
}
