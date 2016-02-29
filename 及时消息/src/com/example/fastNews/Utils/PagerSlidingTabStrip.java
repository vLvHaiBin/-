package com.example.fastNews.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.fastNews.R;
import com.nineoldandroids.view.ViewHelper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/****
 * Android-瀵艰埅鏍忕壒鏁堬紝涓昏鏄鑸爮瀛椾綋澶у皬鍜岄鑹茬殑娓愬彉鐗规�?
 *
 * @author guxiuzhong Email :gfj19900401@163.com
 */
public class PagerSlidingTabStrip extends HorizontalScrollView {

	public interface IconTabProvider {
		int getPageIconResId(int position);
	}

	// @formatter:off
	private static final int[] ATTRS = new int[] { android.R.attr.textSize, android.R.attr.textColor };
	// @formatter:on
	private LinearLayout.LayoutParams matchParentTabLayoutParams;
	private LinearLayout.LayoutParams defaultTabLayoutParams;
	private LinearLayout.LayoutParams expandedTabLayoutParams;

	private PageListener pageListener;
	public OnPageChangeListener delegatePageListener;

	private LinearLayout tabsContainer;
	private ViewPager pager;

	private int tabCount;

	private int currentPosition = 0;
	private int selectedPosition = 0;
	private float currentPositionOffset = 0f;

	private Paint rectPaint;
	private Paint dividerPaint;

	private int indicatorColor = 0xFF666666;
	private int underlineColor = 0x1A000000;
	private int dividerColor = 0x1A000000;

	// 璇ュ睘鎬ц〃绀洪噷闈㈢殑TAB鏄惁鍧囧垎鏁翠釜PagerSlidingTabStrip鎺т欢鐨勫�?,
	// true鏄�,false涓嶅潎鍒�?,浠庡乏鍒板彸鎺掑�?,榛樿false
	private boolean shouldExpand = false;
	private boolean textAllCaps = true;

	private int scrollOffset = 52;
	private int indicatorHeight = 8;
	private int underlineHeight = 2;
	private int dividerPaddingTopBottom = 7;
	private int tabPadding = 10;
	private int dividerWidth = 1;

	private int tabTextSize = 12;
	private int tabTextColor = 0xFF666666;
	private int selectedTabTextColor = 0xFF45c01a;
	private Typeface tabTypeface = null;
	private int tabTypefaceStyle = Typeface.NORMAL;

	private int lastScrollX = 0;

	private int tabBackgroundResId;
	private Locale locale;
	private Context context;
	private boolean smoothScrollWhenClickTab = true;// 璁剧疆褰撶偣鍑籘AB鏃�,鍐呭鍖哄煙鐨刅iewPager鏄惁闇�瑕佸姩鐢诲垏鎹�?
	private List<Map<String, View>> tabViews = new ArrayList<Map<String, View>>();
	private boolean mFadeEnabled = true;
	private float zoomMax = 0.3f;
	private State mState;

	private enum State {
		IDLE, GOING_LEFT, GOING_RIGHT
	}

	private int oldPage;
	private int size =0;
	private float selectTab = (float) 0.2;

	public PagerSlidingTabStrip(Context context) {
		this(context, null);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		setFillViewport(true);
		setWillNotDraw(false);

		tabsContainer = new LinearLayout(context);
		tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
		tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		addView(tabsContainer);

		// 璁剧疆榛樿鍊�
		DisplayMetrics dm = getResources().getDisplayMetrics();
		scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
		indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
		underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
		dividerPaddingTopBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPaddingTopBottom,
				dm);
		tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
		dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
		tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

		// get system attrs (android:textSize and android:textColor)
		TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
		tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
		tabTextColor = a.getColor(1, tabTextColor);
		a.recycle();

		// get custom attrs

		a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);

		indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
		underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
		dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
		indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight,
				indicatorHeight);
		underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight,
				underlineHeight);
		dividerPaddingTopBottom = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPaddingTopBottom,
				dividerPaddingTopBottom);
		tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
		tabBackgroundResId = a.getResourceId(R.styleable.PagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
		shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
		scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
		selectTab = a.getFloat(R.styleable.PagerSlidingTabStrip_pstsselectTab, selectTab);
		textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);
		selectedTabTextColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsTextSelectedColor, selectedTabTextColor);
		zoomMax = a.getFloat(R.styleable.PagerSlidingTabStrip_pstsScaleZoomMax, zoomMax);
		smoothScrollWhenClickTab = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsSmoothScrollWhenClickTab,
				smoothScrollWhenClickTab);
		a.recycle();

		rectPaint = new Paint();
		rectPaint.setAntiAlias(true);
		rectPaint.setStyle(Style.FILL);

		dividerPaint = new Paint();
		dividerPaint.setAntiAlias(true);
		dividerPaint.setStrokeWidth(dividerWidth);

		defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		matchParentTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

		if (locale == null) {
			locale = getResources().getConfiguration().locale;
		}
		pageListener = new PageListener();
	}

	/****
	 * 鍏宠仈ViewPager
	 *
	 * @param pager
	 *            pager
	 */
	public void setViewPager(ViewPager pager) {
		this.pager = pager;
		this.size = size;
		if (this.pager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}
		this.pager.addOnPageChangeListener(pageListener);
		this.notifyDataSetChanged();
	}

	/****
	 * 璁剧疆鐘舵�佺洃鍚�
	 *
	 * @param listener
	 *            listener
	 */
	public void setOnPageChangeListener(OnPageChangeListener listener) {
		this.delegatePageListener = listener;
	}

	private void notifyDataSetChanged() {
		tabsContainer.removeAllViews();
		tabCount = pager.getAdapter().getCount();
		for (int i = 0; i < tabCount; i++) {
			if (pager.getAdapter() instanceof IconTabProvider) {
				addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
			} else {
				addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
			}
		}
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
				currentPosition = pager.getCurrentItem();
				scrollToChild(currentPosition, 0);
				updateTabStyles();
			}
		});
	}

	private void addTextTab(final int position, String title) {
		TextView tab = new TextView(getContext());
		tab.setText(title);
		tab.setGravity(Gravity.CENTER);
		tab.setSingleLine();
		tab.setIncludeFontPadding(false);

		TextView tab2 = new TextView(getContext());
		tab2.setText(title);
		tab2.setGravity(Gravity.CENTER);
		tab2.setSingleLine();
		tab2.setIncludeFontPadding(false);

		addTab(position, tab, tab2);
	}

	private void addIconTab(final int position, int resId) {
		ImageButton tab = new ImageButton(getContext());
		tab.setImageResource(resId);
		addTab(position, tab, null);
	}

	private void addTab(final int position, View tab, View tab2) {

		tab.setPadding(tabPadding, 0, tabPadding, 0);
		tab2.setPadding(tabPadding, 0, tabPadding, 0);

		FrameLayout framelayout = new FrameLayout(context);
		framelayout.addView(tab, 0, matchParentTabLayoutParams);
		framelayout.addView(tab2, 1, matchParentTabLayoutParams);
		tabsContainer.addView(framelayout, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);

		framelayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mFadeEnabled = false;// 鐐瑰嚮鏃舵病鏈夋枃�?�楅鑹叉笎鍙樻晥鏋�?
				pager.setCurrentItem(position, smoothScrollWhenClickTab);
				currentPosition = position;
				scrollToChild(position, 0);// 婊氬姩HorizontalScrollView
			}
		});

		Map<String, View> map = new HashMap<String, View>();

		ViewHelper.setAlpha(tab, 1);
		map.put("normal", tab);

		ViewHelper.setAlpha(tab2, 0);
		map.put("selected", tab2);

		tabViews.add(position, map);
	}

	private void updateTabStyles() {
		for (int i = 0; i < tabCount; i++) {
			FrameLayout frameLayout = (FrameLayout) tabsContainer.getChildAt(i);
			frameLayout.setBackgroundResource(tabBackgroundResId);

			for (int j = 0; j < frameLayout.getChildCount(); j++) {
				View v = frameLayout.getChildAt(j);
				if (v instanceof TextView) {
					TextView tab = (TextView) v;
					tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
					tab.setTypeface(tabTypeface, tabTypefaceStyle);
					if (j == 0) {
						tab.setTextColor(tabTextColor);
					} else {
						tab.setTextColor(selectedTabTextColor);
					}
					ViewHelper.setAlpha(tabViews.get(i).get("normal"), 1);
					ViewHelper.setAlpha(tabViews.get(i).get("selected"), 0);

					// set normal Scale
					ViewHelper.setPivotX(frameLayout, frameLayout.getMeasuredWidth() * 0.5f);
					ViewHelper.setPivotY(frameLayout, frameLayout.getMeasuredHeight() * 0.5f);
					ViewHelper.setScaleX(frameLayout, 1f);
					ViewHelper.setScaleY(frameLayout, 1f);

					// setAllCaps() is only available from API 14, so the upper
					// case is made manually if we are on a
					// pre-ICS-build
					if (textAllCaps) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
							tab.setAllCaps(true);
						} else {
							tab.setText(tab.getText().toString().toUpperCase(locale));
						}
					}
					if (i == selectedPosition) {
						ViewHelper.setAlpha(tabViews.get(i).get("normal"), 0);
						ViewHelper.setAlpha(tabViews.get(i).get("selected"), 1);

						// set select Scale
						ViewHelper.setPivotX(frameLayout, frameLayout.getMeasuredWidth() * 0.5f);
						ViewHelper.setPivotY(frameLayout, frameLayout.getMeasuredHeight() * 0.5f);
						ViewHelper.setScaleX(frameLayout, 1 + zoomMax);
						ViewHelper.setScaleY(frameLayout, 1 + zoomMax);
					}
				}
			}
		}
	}

	private void scrollToChild(int position, int offset) {

		if (tabCount == 0) {
			return;
		}

		int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

		if (position > 0 || offset > 0) {
			newScrollX -= scrollOffset;
		}

		if (newScrollX != lastScrollX) {
			lastScrollX = newScrollX;
			// 涓嶅眳涓殑
			// smoothScrollTo(newScrollX, 0);
			// 浠ヤ笅鏄綋tab寰堝鏃讹紝鐐瑰嚮灞忓箷鍙宠竟鐨勶紝鐐瑰嚮鐨勯偅涓眳涓�?!!!
			int k = tabsContainer.getChildAt(position).getMeasuredWidth();
			int l = tabsContainer.getChildAt(position).getLeft() + offset;
			int i2 = (int) (l + k / 2 - this.getMeasuredWidth() * (selectTab));
			smoothScrollTo(i2, 0);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isInEditMode() || tabCount == 0) {
			return;
		}
		final int height = getHeight();

		// draw underline
		rectPaint.setColor(underlineColor);
		canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

		// draw indicator line
		rectPaint.setColor(indicatorColor);

		// default: line below current tab
		View currentTab = tabsContainer.getChildAt(currentPosition);
		float lineLeft = currentTab.getLeft();
		float lineRight = currentTab.getRight();

		// if there is an offset, start interpolating left and right coordinates
		// between current and next tab
		if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

			View nextTab = tabsContainer.getChildAt(currentPosition + 1);
			final float nextTabLeft = nextTab.getLeft();
			final float nextTabRight = nextTab.getRight();

			lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
			lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
		}
		//画指示线的形状
		canvas.drawRoundRect(lineLeft, height - indicatorHeight, lineRight, height-25, 50, 50, rectPaint);

		// draw divider

		dividerPaint.setColor(dividerColor);
		for (int i = 0; i < tabCount - 1; i++) {
			View tab = tabsContainer.getChildAt(i);
			canvas.drawLine(tab.getRight(), dividerPaddingTopBottom, tab.getRight(), height - dividerPaddingTopBottom,
				dividerPaint);
			
		}
	}

	private class PageListener implements OnPageChangeListener {
		private int oldPosition = 0;

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			currentPosition = position;
			currentPositionOffset = positionOffset;

			if (tabsContainer != null && tabsContainer.getChildAt(position) != null) {
				scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
			}

			invalidate();

			if (delegatePageListener != null) {
				delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}

			if (mState == State.IDLE && positionOffset > 0) {
				oldPage = pager.getCurrentItem();
				mState = position == oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
			}
			boolean goingRight = position == oldPage;
			if (mState == State.GOING_RIGHT && !goingRight)
				mState = State.GOING_LEFT;
			else if (mState == State.GOING_LEFT && goingRight)
				mState = State.GOING_RIGHT;

			float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;

			View mLeft = tabsContainer.getChildAt(position);
			View mRight = tabsContainer.getChildAt(position + 1);

			if (effectOffset == 0) {
				mState = State.IDLE;
			}

			if (mFadeEnabled)
				animateFadeScale(mLeft, mRight, effectOffset, position);

		}

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == ViewPager.SCROLL_STATE_IDLE) {
				scrollToChild(pager.getCurrentItem(), 0);
				mFadeEnabled = true;
			}
			if (delegatePageListener != null) {
				delegatePageListener.onPageScrollStateChanged(state);
			}
		}

		@Override
		public void onPageSelected(int position) {
			selectedPosition = position;

			// set old view statue
			ViewHelper.setAlpha(tabViews.get(oldPosition).get("normal"), 1);
			ViewHelper.setAlpha(tabViews.get(oldPosition).get("selected"), 0);
			View v_old = tabsContainer.getChildAt(oldPosition);
			ViewHelper.setPivotX(v_old, v_old.getMeasuredWidth() * 0.5f);
			ViewHelper.setPivotY(v_old, v_old.getMeasuredHeight() * 0.5f);
			ViewHelper.setScaleX(v_old, 1f);
			ViewHelper.setScaleY(v_old, 1f);

			// set new view statue
			ViewHelper.setAlpha(tabViews.get(position).get("normal"), 0);
			ViewHelper.setAlpha(tabViews.get(position).get("selected"), 1);
			View v_new = tabsContainer.getChildAt(position);
			ViewHelper.setPivotX(v_new, v_new.getMeasuredWidth() * 0.5f);
			ViewHelper.setPivotY(v_new, v_new.getMeasuredHeight() * 0.5f);
			ViewHelper.setScaleX(v_new, 1 + zoomMax);
			ViewHelper.setScaleY(v_new, 1 + zoomMax);

			if (delegatePageListener != null) {
				delegatePageListener.onPageSelected(position);
			}
			oldPosition = selectedPosition;
		}
	}

	public void setIndicatorColor(int indicatorColor) {
		this.indicatorColor = indicatorColor;
		invalidate();
	}

	public void setIndicatorColorResource(int resId) {
		this.indicatorColor = getResources().getColor(resId);
		invalidate();
	}

	public int getIndicatorColor() {
		return this.indicatorColor;
	}

	public void setIndicatorHeight(int indicatorLineHeightDp) {
		this.indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorLineHeightDp,
				getResources().getDisplayMetrics());
		invalidate();
	}

	public int getIndicatorHeight() {
		return indicatorHeight;
	}

	public void setUnderlineColor(int underlineColor) {
		this.underlineColor = underlineColor;
		invalidate();
	}

	public void setUnderlineColorResource(int resId) {
		this.underlineColor = getResources().getColor(resId);
		invalidate();
	}

	public int getUnderlineColor() {
		return underlineColor;
	}

	public void setDividerColor(int dividerColor) {
		this.dividerColor = dividerColor;
		invalidate();
	}

	public void setDividerColorResource(int resId) {
		this.dividerColor = getResources().getColor(resId);
		invalidate();
	}

	public int getDividerColor() {
		return dividerColor;
	}

	public void setUnderlineHeight(int underlineHeightDp) {
		this.underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeightDp,
				getResources().getDisplayMetrics());
		invalidate();
	}

	public int getUnderlineHeight() {
		return underlineHeight;
	}

	public void setDividerPaddingTopBottom(int dividerPaddingDp) {
		this.dividerPaddingTopBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPaddingDp,
				getResources().getDisplayMetrics());
		invalidate();
	}

	public int getDividerPaddingTopBottom() {
		return dividerPaddingTopBottom;
	}

	public void setScrollOffset(int scrollOffsetPx) {
		this.scrollOffset = scrollOffsetPx;
		invalidate();
	}

	public int getScrollOffset() {
		return scrollOffset;
	}

	public void setShouldExpand(boolean shouldExpand) {
		this.shouldExpand = shouldExpand;
		notifyDataSetChanged();
	}

	public boolean getShouldExpand() {
		return shouldExpand;
	}

	public boolean isTextAllCaps() {
		return textAllCaps;
	}

	public void setAllCaps(boolean textAllCaps) {
		this.textAllCaps = textAllCaps;
	}

	public void setTextSize(int textSizeSp) {
		this.tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSp,
				getResources().getDisplayMetrics());
		updateTabStyles();
	}

	public int getTextSize() {
		return tabTextSize;
	}

	public void setTextColor(int textColor) {
		this.tabTextColor = textColor;
		updateTabStyles();
	}

	public void setTextColorResource(int resId) {
		this.tabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getTextColor() {
		return tabTextColor;
	}

	public void setSelectedTextColor(int textColor) {
		this.selectedTabTextColor = textColor;
		updateTabStyles();
	}

	public void setSelectedTextColorResource(int resId) {
		this.selectedTabTextColor = getResources().getColor(resId);
		updateTabStyles();
	}

	public int getSelectedTextColor() {
		return selectedTabTextColor;
	}

	public void setTypeface(Typeface typeface, int style) {
		this.tabTypeface = typeface;
		this.tabTypefaceStyle = style;
		updateTabStyles();
	}

	public void setTabBackground(int resId) {
		this.tabBackgroundResId = resId;
		updateTabStyles();
	}

	public int getTabBackground() {
		return tabBackgroundResId;
	}

	public void setTabPaddingLeftRight(int paddingDp) {
		this.tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingDp,
				getResources().getDisplayMetrics());
		updateTabStyles();
	}

	public int getTabPaddingLeftRight() {
		return tabPadding;
	}

	public boolean isSmoothScrollWhenClickTab() {
		return smoothScrollWhenClickTab;
	}

	public void setSmoothScrollWhenClickTab(boolean smoothScrollWhenClickTab) {
		this.smoothScrollWhenClickTab = smoothScrollWhenClickTab;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		currentPosition = savedState.currentPosition;
		requestLayout();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPosition = currentPosition;
		return savedState;
	}

	static class SavedState extends BaseSavedState {
		int currentPosition;

		public SavedState(Parcelable superState) {
			super(superState);
		}

		private SavedState(Parcel in) {
			super(in);
			currentPosition = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPosition);
		}

		public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public void setFadeEnabled(boolean enabled) {
		mFadeEnabled = enabled;
	}

	public boolean getFadeEnabled() {
		return mFadeEnabled;
	}

	public float getZoomMax() {
		return zoomMax;
	}

	public void setZoomMax(float zoomMax) {
		this.zoomMax = zoomMax;
	}

	private boolean isSmall(float positionOffset) {
		return Math.abs(positionOffset) < 0.0001;
	}

	protected void animateFadeScale(View left, View right, float positionOffset, int position) {
		if (mState != State.IDLE) {
			if (left != null) {
				ViewHelper.setAlpha(tabViews.get(position).get("normal"), positionOffset);
				ViewHelper.setAlpha(tabViews.get(position).get("selected"), 1 - positionOffset);
				float mScale = 1 + zoomMax - zoomMax * positionOffset;
				ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(left, mScale);
				ViewHelper.setScaleY(left, mScale);
			}
			if (right != null) {
				ViewHelper.setAlpha(tabViews.get(position + 1).get("normal"), 1 - positionOffset);
				ViewHelper.setAlpha(tabViews.get(position + 1).get("selected"), positionOffset);
				float mScale = 1 + zoomMax * positionOffset;
				ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
				ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
				ViewHelper.setScaleX(right, mScale);
				ViewHelper.setScaleY(right, mScale);
			}
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (null != pageListener && this.pager != null)
			this.pager.removeOnPageChangeListener(pageListener);
	}
	
	
	
}
