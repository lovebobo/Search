package com.example.test;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listviewAdapter<T> extends ArrayAdapter<T> 
{
	private Context mContext;
	private int mViewResourceID = 0;
	private List<T> mViewList;
	private int mposition=-1;

	public listviewAdapter(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
		mContext = context;
		mViewResourceID = textViewResourceId;
		mViewList = objects;
		
	}
	public void reloadList(List<T> objects) {
		if(objects!=null){
			mViewList = objects;
			}
	}

	@Override
	public int getCount() {
		return mViewList.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public T getItem(int position) {
		return mViewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {			
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					mViewResourceID, null);
			}
		switch (mViewResourceID) {
		
		case R.layout.grid_view_item:
			bindgridView((homepage)mViewList.get(position), convertView);
			break;
		case R.layout.ans_item:
			bindansView((danci)mViewList.get(position), convertView);
			break;
		default:
			break;
			
		}
		
		return convertView;
	}
	private void bindansView(danci danci, View convertView) {
		// TODO Auto-generated method stub
		((TextView)convertView.findViewById(R.id.ans_textview1)).setText(danci.getEnglish());
		((TextView)convertView.findViewById(R.id.ans_detail_textview)).setText(danci.getChinese());
	}
	private void bindgridView(homepage homepage, View convertView) {
		// TODO Auto-generated method stub
		((TextView)convertView.findViewById(R.id.text)).setText(homepage.getName());
		((ImageView)convertView.findViewById(R.id.img_li)).setBackgroundResource(homepage.getIcon());
		
	}
}
