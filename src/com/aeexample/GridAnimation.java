package com.aeexample;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.aeexample.gridanimationtest.R;

public class GridAnimation extends Activity {
    private static final String TAG = GridAnimation.GridAdapter.class
	    .getCanonicalName();
    private static final long DURATION = 150;
    private GridView mGrid;

    private OnGestureListener gestureListener = new OnGestureListener() {

	private boolean ignoreScrolls;

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
	    Log.d(TAG,
		    "GridAnimation.gestureListener.new OnGestureListener() {...}.onSingleTapUp():");
	    return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2,
		float distanceX, float distanceY) {
	    // positive moving towards bottom of list..
	    Log.d(TAG,
		    "GridAnimation.gestureListener.new OnGestureListener() {...}.onScroll():"
			    + distanceY);
	    if (!ignoreScrolls) {
		if (distanceY > 0) {
		    ignoreScrolls = true;
		    setHeaderVisibility(View.GONE);
		} else {
		    if (mGrid.getFirstVisiblePosition() < 1) {
			ignoreScrolls = true;
			setHeaderVisibility(View.VISIBLE);

		    }
		}
	    }

	    return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	private Runnable flingRunnable = new Runnable() {

	    @Override
	    public void run() {
		Log.d(TAG, "GridAnimation.runnable!!:");

		if (mGrid.getFirstVisiblePosition() == 0) {
		    Log.d(TAG,
			    " first visible position is 0 done scrolling, collapse");
		    ignoreScrolls = true;
		    setHeaderVisibility(View.VISIBLE);
		} else {
		    mGrid.postOnAnimation(this);
		}

	    }
	};

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {

	    if (velocityY > 0) {
		mGrid.removeCallbacks(flingRunnable);
		mGrid.postOnAnimation(flingRunnable);
		mGrid.postDelayed(new Runnable() {

		    @Override
		    public void run() {
			mGrid.removeCallbacks(flingRunnable);

		    }
		}, 1000);
		ignoreScrolls = true;
	    } else {
		ignoreScrolls = true;
	    }
	    return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
	    if (mGrid != null && mGrid.getAdapter() != null
		    && mGrid.getAdapter().getCount() > 2) {
		ignoreScrolls = false;
	    }
	    return false;
	}
    };
    private View mHeaderView;
    private GestureDetector gestureDetector;
    private GridAdapter mAdapter;
    private ViewGroup mContainer;

    private void setHeaderVisibility(int visibility) {

	if (mHeaderView.getVisibility() != visibility) {
	    mHeaderView.setVisibility(visibility);
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_grid_animation);
	mContainer = (ViewGroup) findViewById(R.id.main);

	mGrid = (GridView) findViewById(R.id.grid);
	mHeaderView = findViewById(R.id.header);
	mAdapter = new GridAdapter(this);
	mGrid.setAdapter(mAdapter);
	gestureDetector = new GestureDetector(this, gestureListener);
	mGrid.setOnTouchListener(new OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	    }
	});
    }

    @Override
    protected void onResume() {
	super.onResume();

	LayoutTransition trans = mContainer.getLayoutTransition();

	trans.setInterpolator(LayoutTransition.APPEARING,
		new AccelerateInterpolator(1.2f));
	trans.setInterpolator(LayoutTransition.CHANGE_APPEARING,
		new AccelerateInterpolator(1.2f));
	trans.setInterpolator(LayoutTransition.CHANGE_DISAPPEARING,
		new AccelerateInterpolator(1.2f));
	trans.setInterpolator(LayoutTransition.CHANGING,
		new AccelerateInterpolator(1.2f));
	trans.setInterpolator(LayoutTransition.DISAPPEARING,
		new AccelerateInterpolator(1.2f));

	trans.setDuration(LayoutTransition.APPEARING, DURATION);
	trans.setDuration(LayoutTransition.CHANGE_APPEARING, DURATION);
	trans.setDuration(LayoutTransition.CHANGE_DISAPPEARING, DURATION);
	trans.setDuration(LayoutTransition.CHANGING, DURATION);
	trans.setDuration(LayoutTransition.DISAPPEARING, DURATION);
	//
	trans.setDuration(DURATION);
	trans.setStagger(LayoutTransition.APPEARING, 0);
	trans.setStagger(LayoutTransition.CHANGE_APPEARING, 0);
	trans.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 0);
	trans.setStagger(LayoutTransition.CHANGING, 0);
	trans.setStagger(LayoutTransition.DISAPPEARING, 0);

    }

    private class GridAdapter extends BaseAdapter {

	private static final int DURATION = 300;
	private LayoutInflater mInflater;

	public GridAdapter(Context context) {
	    mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
	    return 30;
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    if (convertView == null) {
		convertView = mInflater.inflate(R.layout.item_image, parent,
			false);
	    }
	    // final View plh = convertView.findViewById(R.id.ph);
	    View img = convertView.findViewById(R.id.img);
	    // plh.setRotationY(0);
	    // img.setRotationY(-90);
	    // if(BuildConfig.DEBUG){
	    // Log.d(TAG, "GridAnimation.GridAdapter.getView():" +
	    // plh.getWidth());
	    // }
	    // plh.setPivotY(plh.getTop() + 194);
	    // plh.setPivotX(plh.getLeft() + 360);
	    //
	    // img.setPivotY(img.getTop() + 194);
	    // img.setPivotX(img.getLeft() + 360);
	    // plh.animate().rotationY(90).setDuration(DURATION).start();

	    img.animate().rotationY(0).setDuration(DURATION)
		    .setStartDelay(DURATION).start();
	    // ViewAnimator va = (ViewAnimator)convertView;
	    // ((ViewAnimator)convertView).showNext();
	    return convertView;
	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.grid_animation, menu);
	return true;
    }

}
