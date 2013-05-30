package com.insidion.prspct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

public class MenuActivity extends Activity implements View.OnClickListener {

	private Button news, artists, events, pictures;
	public static int[] background = { R.drawable.bg_1, R.drawable.bg_2,
			R.drawable.bg_3 };
	private ImageChange imagechanger;
	
	private boolean isRunning;
	

	Random damn = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		getButtons();

		
		startBackground();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isRunning = true;
	}
	
	private void startBackground() {
		imagechanger = new ImageChange(MenuActivity.this);
		imagechanger.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	public boolean isRunning() {
		return isRunning;
	}

	private void getButtons() {
		news = (Button) findViewById(R.id.bNews);
		news.setOnClickListener(this);
		events = (Button) findViewById(R.id.bEvents);
		events.setOnClickListener(this);
		artists = (Button) findViewById(R.id.bArtists);
		artists.setOnClickListener(this);
		pictures = (Button) findViewById(R.id.bPictures);
		pictures.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bNews:
			Intent newsIntent = new Intent();
			newsIntent.setAction("android.intent.action.NEWS");
			startActivity(newsIntent);
			break;
		case R.id.bArtists:
			Intent artistsIntent = new Intent();
			artistsIntent.setAction("android.intent.action.ARTISTS");
			startActivity(artistsIntent);
			break;
		case R.id.bEvents:
			Intent eventIntent = new Intent();
			eventIntent.setAction("android.intent.action.EVENTS");
			startActivity(eventIntent);
			break;
		case R.id.bPictures:
			Intent picIntent = new Intent();
			picIntent.setAction("android.intent.action.PICTURES");
			startActivity(picIntent);
			break;
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isRunning = false;
	}

}

class ImageChange extends AsyncTask<Void, Void, Void> {
	Random damn = new Random();
	MenuActivity act;
	List<Integer> already = new ArrayList<Integer>();
	LinearLayout bg;

	Runnable changeImage = new Runnable() {
		public void run() {
			if (already.size() == MenuActivity.background.length) {
				already.clear();
			}
			int newCandidate = -1;
			while (newCandidate == -1 || already.contains(newCandidate)) {
				newCandidate = damn.nextInt(MenuActivity.background.length);
			}
			already.add(newCandidate);
			bg.setBackgroundResource(MenuActivity.background[newCandidate]);
		}
	};

	public ImageChange(MenuActivity act) {
		this.act = act;
		bg = (LinearLayout) this.act.findViewById(R.id.rlBg);
		this.act.runOnUiThread(changeImage);
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		while (true) {
			try {
				Thread.sleep(5000);
				if(act.isRunning()){
					act.runOnUiThread(changeImage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}
	}

}
