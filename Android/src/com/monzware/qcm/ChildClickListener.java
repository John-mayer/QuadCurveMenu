/**
 * Created by Peer Bech Hansen - peer.bech.hansen@gmail.com
 * 
 * This is a Android version of the QuadCurveMenu by Levey
 * https://github.com/levey/QuadCurveMenu
 */
package com.monzware.qcm;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class ChildClickListener implements OnClickListener {

	// private final QuadCurveMenu quadCurveMenu;

	public ChildClickListener(QuadCurveMenu quadCurveMenu) {
		// this.quadCurveMenu = quadCurveMenu;

	}

	@Override
	public void onClick(View v) {

		Log.d("Onclick", v.toString());

		/*
		 * int childCount = quadCurveMenu.getChildCount(); for (int i = 1; i <
		 * childCount; i++) { View childAt = quadCurveMenu.getChildAt(i);
		 * 
		 * if (childAt.equals(v)) {
		 * 
		 * } else {
		 * 
		 * AnimationSet set = new AnimationSet(true);
		 * 
		 * AlphaAnimation fade = new AlphaAnimation(1, 0);
		 * fade.setDuration(100); set.addAnimation(fade);
		 * 
		 * ScaleAnimation scale = new ScaleAnimation(1, 0, 1,0);
		 * scale.setDuration(100); set.addAnimation(scale);
		 * 
		 * set.setFillAfter(true);
		 * 
		 * childAt.startAnimation(set);
		 * 
		 * } }
		 */
	}
}
