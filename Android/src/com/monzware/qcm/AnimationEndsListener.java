/**
 * Created by Peer Bech Hansen - peer.bech.hansen@gmail.com
 * 
 * This is a Android version of the QuadCurveMenu by Levey
 * https://github.com/levey/QuadCurveMenu
 */
package com.monzware.qcm;

import android.graphics.Point;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class AnimationEndsListener implements AnimationListener {

	private final View child;
	private final Point endPoint;

	public AnimationEndsListener(View child, Point endPoint) {
		this.child = child;
		this.endPoint = endPoint;
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// child.setLayoutParams()
		int x = endPoint.x;
		int y = endPoint.y;

		child.layout(x, y, x + child.getMeasuredWidth(), y + child.getMeasuredHeight());

		/*
		 * child.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Log.d("Click", v.toString());
		 * 
		 * } });
		 */

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}
