package com.insidion.prspct;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

	Context context;

	int layoutResourceId;

	List<NewsItem> items = null;

	public NewsItemAdapter(Context context, int textViewResourceId,
			List<NewsItem> objects) {
		super(context, textViewResourceId, objects);
		this.layoutResourceId = textViewResourceId;
		this.context = context;

		this.items = objects;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeatherHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(this.layoutResourceId, parent, false);

			holder = new WeatherHolder();
			holder.imgIcon = (ImageView) row.findViewById(R.id.imgIcon);
			holder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);

			row.setTag(holder);
		} else {
			holder = (WeatherHolder) row.getTag();
		}

		NewsItem weather = items.get(position);
		holder.txtTitle.setText(weather.getTitle());
		holder.imgIcon.setImageResource(R.drawable.prspct_logo);

		return row;

	}

	static class WeatherHolder {
		ImageView imgIcon;
		TextView txtTitle;
	}
}
