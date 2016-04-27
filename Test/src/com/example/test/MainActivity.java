package com.example.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.anim;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends Activity {
	
	private GridView gridView;
	
	private List<homepage> IconDetailList =new ArrayList<homepage>();//首页图标列表
	
	private List<homepage> data_list=new ArrayList<homepage>();
	
	private listviewAdapter<homepage> listViewAdapte;
	
	private AutoCompleteTextView autoCompleteTextView;
	
	private Button button;
	
	private ArrayList<String> hisArrays=new ArrayList<String>();
	
	private String DB_NAME ="dictionary.db";
	
	private String etext ;
	
	private String ctext ;
	
	private ArrayList<danci> danciList=new ArrayList<danci>();
	
	ArrayAdapter<String> adapter;
	
	private static final String PACKAGE_NAME = "com.example.test";  
	private static final String DB_PATH = "/data"  
	            + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;  
	private SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		initData();
	}

	public void openDatabase() {  
        this.database = this.openDatabase(DB_PATH + "/" + DB_NAME);  
    }
	
	public void getData(){        
        //cion和iconName的长度是相同的，这里任选其一都可以
		IconDetailList.add(new homepage("单词本",R.drawable.jiaowu ));
		IconDetailList.add(new homepage("咨询",R.drawable.shiwu ));
		IconDetailList.add(new homepage("翻译",R.drawable.banshi ));
		IconDetailList.add(new homepage("资料中心",R.drawable.tushu ));
		IconDetailList.add(new homepage("设置",R.drawable.shuidian ));
		IconDetailList.add(new homepage("帮助",R.drawable.gengduo ));
		
    }
	
	private void initView() {
		// TODO Auto-generated method stub
		gridView=(GridView)findViewById(R.id.gridview);
		autoCompleteTextView=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		button=(Button)findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stubr
				openDatabase();
				danciList.clear();
				Cursor cursor = database.rawQuery("select * from "
						+ "t_words" +" where english LIKE  ?", new String[] {"%"+autoCompleteTextView.getText().toString()+"%"});			
		        while(cursor.moveToNext()){  
		        	etext=cursor.getString(cursor.getColumnIndexOrThrow("english"));    
		        	ctext=cursor.getString(cursor.getColumnIndexOrThrow("chinese"));    
		        	danciList.add(new danci(etext,ctext));
		        }  
		        
		        cursor.close();  
		        database.close();  
		        
		        Intent intent=new Intent(MainActivity.this,ShowAnswerList.class);
		        Bundle bundle=new Bundle();
		        bundle.putSerializable("AnsList", danciList);
		        intent.putExtras(bundle);
		        startActivity(intent);		        
			}
		});
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		getData();
		listViewAdapte=new listviewAdapter<homepage>(MainActivity.this, R.layout.grid_view_item, IconDetailList);
		gridView.setAdapter(listViewAdapte);
		initAutoTextView(); 				
	}

	private void initAutoTextView() {
		// TODO Auto-generated method stub
	   adapter = new ArrayAdapter<String>(this,  
                android.R.layout.simple_dropdown_item_1line, hisArrays);  
		autoCompleteTextView.setAdapter(adapter);  
		autoCompleteTextView.setDropDownHeight(1000);  
		autoCompleteTextView.setThreshold(1);  
		autoCompleteTextView.setCompletionHint("最近的10条记录");  
        autoCompleteTextView.setOnFocusChangeListener(new OnFocusChangeListener() {  
            @Override  
            public void onFocusChange(View v, boolean hasFocus) {  
                /*AutoCompleteTextView view = (AutoCompleteTextView) v;  
                if (hasFocus) {  
                        view.showDropDown();  
                }  */
            }  
        });  
        
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				openDatabase();
				hisArrays.clear();
				Cursor cursor = database.rawQuery("select * from "
						+ "t_words " +" where english LIKE  ?"+"limit 0,5", new String[] {"%"+autoCompleteTextView.getText().toString()+"%"} );			
		        while(cursor.moveToNext()){  
		        	etext=cursor.getString(cursor.getColumnIndexOrThrow("english"));    
		        	hisArrays.add(etext);
		        }  
		        adapter.notifyDataSetChanged();
		        autoCompleteTextView.showDropDown();
		        cursor.close();  
		        database.close();  
			}
		});
       
                     
	}

	private SQLiteDatabase openDatabase(String dbfile) {  
        try {  
          File  file = new File(dbfile);  
            if (!file.exists()) {  
                InputStream is = this.getResources().openRawResource(R.raw.dictionary);  
                FileOutputStream fos = new FileOutputStream(dbfile);  
                byte[] buffer = new byte[1024];  
                int count = 0;  
                while ((count = is.read(buffer)) > 0) {  
                    fos.write(buffer, 0, count);  
                    fos.flush();  
                }  
                fos.close();  
                is.close();  
            }  
            database = SQLiteDatabase.openOrCreateDatabase(dbfile, null);  
            return database;  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
        }  
        return null;  
    }
	
}
