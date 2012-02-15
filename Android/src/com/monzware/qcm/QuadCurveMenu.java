/**
 * Created by Peer Bech Hansen - peer.bech.hansen@gmail.com
 * 
 * This is a Android version of the QuadCurveMenu by Levey
 * https://github.com/levey/QuadCurveMenu
 */
package com.monzware.qcm;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class QuadCurveMenu extends ViewGroup {

	final static double kQuadCurveMenuDefaultNearRadius = 110.0f;
	final static double kQuadCurveMenuDefaultEndRadius = 120.0f;
	final static double kQuadCurveMenuDefaultFarRadius = 140.0f;
	final static double kQuadCurveMenuDefaultTimeOffset = 0.036f;
	final static double kQuadCurveMenuDefaultRotateAngle = 0.0f;
	final static double kQuadCurveMenuDefaultMenuWholeAngle = Math.PI * 2d;

	private ChildClickListener pressedListener = new ChildClickListener(this);

	public static final LayoutParams LayoutParams = null;

	private double nearRadius, endRadius, farRadius, timeOffset, rotateAngle, menuWholeAngle;
	private static boolean expanded;

	private Point center;

	public QuadCurveMenu(Context context) {
		super(context);

		nearRadius = kQuadCurveMenuDefaultNearRadius;
		endRadius = kQuadCurveMenuDefaultEndRadius;
		farRadius = kQuadCurveMenuDefaultFarRadius;
		timeOffset = kQuadCurveMenuDefaultTimeOffset;
		rotateAngle = kQuadCurveMenuDefaultRotateAngle;
		menuWholeAngle = kQuadCurveMenuDefaultMenuWholeAngle;

		// addView(iv);

	}

	public QuadCurveMenu(Context context, AttributeSet set, int i) {
		super(context, set, i);
	}

	public QuadCurveMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		nearRadius = kQuadCurveMenuDefaultNearRadius;
		endRadius = kQuadCurveMenuDefaultEndRadius;
		farRadius = kQuadCurveMenuDefaultFarRadius;
		timeOffset = kQuadCurveMenuDefaultTimeOffset;
		rotateAngle = kQuadCurveMenuDefaultRotateAngle;
		menuWholeAngle = kQuadCurveMenuDefaultMenuWholeAngle;

	}

	@Override
	protected void onFinishInflate() {

		View view = getChildAt(0);

		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!expanded) {
					expandMenu();
				} else {
					closeMenu();
				}

				expanded = !expanded;
			}
		});

		super.onFinishInflate();

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

		int centerX = (right - left) / 2;
		int centerY = (bottom - top) / 2;

		center = new Point(centerX, centerY);

		int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {

			View child = getChildAt(i);

			int measuredHeight = child.getMeasuredHeight();
			int measuredWidth = child.getMeasuredWidth();

			// First view. This is the center view
			if (i == 0) {
				int x = centerX - measuredWidth / 2;
				int y = centerY - measuredHeight / 2;
				child.layout(x, y, x + measuredWidth, y + measuredHeight);
			} else {

				if (expanded) {
					Point endPoint = getPointOncirkel(center, i - 1, childCount - 1, endRadius);
					int x = endPoint.x - measuredWidth / 2;
					int y = endPoint.y - measuredHeight / 2;
					child.layout(x, y, x + measuredWidth, y + measuredHeight);
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int childCount = getChildCount();

		for (int i = 0; i < childCount; i++) {

			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
		}
	}

	private void expandMenu() {
		Log.d("", "expandMenu");

		int offset = 0;

		int childCount = getChildCount();
		for (int i = 1; i < childCount; i++) {

			View child = getChildAt(i);

			// child.setOnClickListener(pressedListener);

			int measuredHeight = child.getMeasuredHeight();
			int measuredWidth = child.getMeasuredWidth();

			int x = center.x - measuredWidth / 2;
			int y = center.y - measuredHeight / 2;

			child.layout(x, y, x + measuredWidth, y + measuredHeight);

			AnimationSet set = new AnimationSet(true);

			Point farPoint = getPointOncirkel(center, i - 1, childCount - 1, farRadius);
			Point nearPoint = getPointOncirkel(center, i - 1, childCount - 1, nearRadius);
			Point endPoint = getPointOncirkel(center, i - 1, childCount - 1, endRadius);

			TranslateAnimation far = new TranslateAnimation(0, farPoint.x - center.x, 0, farPoint.y - center.y);
			far.setDuration(300);
			far.setStartOffset(offset);

			offset += 300;

			TranslateAnimation near = new TranslateAnimation(0, nearPoint.x - farPoint.x, 0, nearPoint.y - farPoint.y);
			near.setDuration(30);
			near.setStartOffset(offset);

			offset += 30;

			TranslateAnimation end = new TranslateAnimation(0, endPoint.x - nearPoint.x, 0, endPoint.y - nearPoint.y);
			end.setDuration(100);
			end.setStartOffset(offset);

			set.addAnimation(far);
			set.addAnimation(near);
			set.addAnimation(end);

			set.setFillAfter(true);

			// end.setAnimationListener(new AnimationEndsListener(child,
			// endPoint));

			// overide applyTransformation

			child.startAnimation(set);

			offset -= 300;

		}

	}

	private void closeMenu() {
		int offset = 0;

		int childCount = getChildCount();
		for (int i = 1; i < childCount; i++) {

			View child = getChildAt(i);

			int measuredHeight = child.getMeasuredHeight();
			int measuredWidth = child.getMeasuredWidth();

			Point farPoint = getPointOncirkel(center, i - 1, childCount - 1, farRadius);
			Point endPoint = getPointOncirkel(center, i - 1, childCount - 1, endRadius);

			int x = endPoint.x - measuredWidth / 2;
			int y = endPoint.y - measuredHeight / 2;

			child.layout(x, y, x + measuredWidth, y + measuredHeight);

			AnimationSet set = new AnimationSet(true);

			TranslateAnimation far = new TranslateAnimation(0, farPoint.x - endPoint.x, 0, farPoint.y - endPoint.y);
			far.setDuration(80);
			far.setStartOffset(offset);

			offset += 80;

			TranslateAnimation centerAnim = new TranslateAnimation(0, center.x - farPoint.x, 0, center.y - farPoint.y);
			centerAnim.setDuration(300);
			centerAnim.setStartOffset(offset);

			AlphaAnimation fade = new AlphaAnimation(1, 0);

			fade.setDuration(50);
			fade.setStartOffset(offset + 300 - 50);

			set.addAnimation(far);
			set.addAnimation(centerAnim);
			set.addAnimation(fade);

			set.setFillAfter(true);

			child.startAnimation(set);

			offset += 50;

		}

		/*
		 * View addButton = getChildAt(0);
		 * 
		 * int measuredHeight = addButton.getMeasuredHeight(); int measuredWidth
		 * = addButton.getMeasuredWidth();
		 * 
		 * int x = center.x - measuredWidth / 2; int y = center.y -
		 * measuredHeight / 2;
		 * 
		 * addButton.layout(x, y, measuredWidth, measuredHeight);
		 */

	}

	private Point getPointOncirkel(Point center, int i, int childCount, double radius) {
		return new Point((int) (center.x + radius * Math.sin(i * menuWholeAngle / childCount)), (int) (center.y - radius * Math.cos(i * menuWholeAngle / childCount)));
	}
}
