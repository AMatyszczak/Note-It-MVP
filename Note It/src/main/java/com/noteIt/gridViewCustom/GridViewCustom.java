package com.noteIt.gridViewCustom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.noteIt.R;
import com.noteIt.notes.NoteAdapter;

public class GridViewCustom extends GridView implements AdapterView.OnItemLongClickListener {
    private final static long ANIMATION_DURATION = 300;

    private ActionMode mActionMode;
    private CompositeListener compositeListener;

    private int mDraggedX = -1;
    private int mDraggedY = -1;
    private View mDraggedView;

    private boolean isDragging = false;

    private final int INVALID_ID = -1;
    private long mDraggedItemId = INVALID_ID;

    private BitmapDrawable mHoverCell;
    private Rect mHoverCellCurrentBounds;
    private Rect mHoverCellOriginalBounds;

    private final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;


    public GridViewCustom(Context context) {
        super(context);
        init();
    }

    public GridViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        compositeListener = new CompositeListener();
        super.setOnTouchListener(mOnTouchListener);
        super.setOnItemLongClickListener(mOnLongClickListener);
    }

    private AdapterView.OnItemLongClickListener mOnLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, final View viewClick, final int index, final long l) {

            int position = pointToPosition(mDraggedX, mDraggedY);
            int itemId = position - getFirstVisiblePosition();
            mDraggedView = getChildAt(itemId);
            mDraggedItemId = getAdapter().getItemId(position);
            mHoverCell = getAndHoverView(mDraggedView);

            isDragging = true;
            return true;
        }
    };

    private BitmapDrawable getAndHoverView(View view) {
        int w = view.getWidth();
        int h = view.getHeight();
        int top = view.getTop();
        int left = view.getLeft();

        Bitmap b = getBitmapFromView(view);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

        mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
        mHoverCellCurrentBounds = new Rect(mHoverCellOriginalBounds);
        drawable.setBounds(mHoverCellCurrentBounds);

        return drawable;

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;

    }


    private AdapterView.OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            NoteAdapter adapter = (NoteAdapter) getAdapter();
            switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mDraggedX = (int) motionEvent.getX();
                    mDraggedY = (int) motionEvent.getY();
                    mActivePointerId = motionEvent.getPointerId(0);

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mActivePointerId == INVALID_POINTER_ID)
                        break;

                    int pointerIndex = motionEvent.findPointerIndex(mActivePointerId);


                    if (isDragging) {

                        int mLastEventX = (int) motionEvent.getX(pointerIndex);
                        int mLastEventY = (int) motionEvent.getY(pointerIndex);


                        int deltaY = mLastEventY - mDraggedY;
                        int deltaX = mLastEventX - mDraggedX;

                        int position = pointToPosition(mLastEventX, mLastEventY);
                        final long viewID = adapter.getItemId(position);

                        mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left + deltaX, mHoverCellOriginalBounds.top + deltaY);
                        mHoverCell.setBounds(mHoverCellCurrentBounds);
                        invalidate();

                        if (viewID != INVALID_ID) {
                            if (mActionMode != null)
                                mActionMode.finish();

                            View viewUnder = getViewFromId(viewID);


                            adapter.swapItems((int) mDraggedItemId, (int) viewID);
                            animateDragToStart(mDraggedView, viewUnder);

                            viewUnder.setVisibility(INVISIBLE);
                            mDraggedView.setVisibility(VISIBLE);
                            mDraggedView.setBackgroundColor(getResources().getColor(R.color.white));


                            mDraggedView = viewUnder;
                            mDraggedItemId = viewID;

                            adapter.notifyDataSetChanged();
                            return true;
                        }
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isDragging) {

                        draggingEnded(adapter);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                    if (isDragging) {
                        draggingEnded(adapter);
                    }
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    pointerIndex = (motionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = motionEvent.getPointerId(pointerIndex);
                    if (pointerId == mActivePointerId)
                        if (isDragging) {
                            draggingEnded(adapter);
                        }
                    break;
                default:
                    if (mActionMode != null)
                        mActionMode.finish();
                    adapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    };

    private void draggingEnded(BaseAdapter adapter) {
        adapter.notifyDataSetChanged();

        getViewFromId(mDraggedItemId).setVisibility(VISIBLE);
        mDraggedItemId = INVALID_ID;
        mHoverCell = null;
        isDragging = false;

        invalidate();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        int position = pointToPosition(mDraggedX, mDraggedY);
        int itemId = position - getFirstVisiblePosition();
        mDraggedView = getChildAt(itemId);
        mDraggedItemId = getAdapter().getItemId(position);
        mHoverCell = getAndHoverView(mDraggedView);

        mDraggedView.setVisibility(INVISIBLE);
        isDragging = true;

        return true;
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        compositeListener.registerListener(listener);
    }

    @Override
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        super.setMultiChoiceModeListener(new MultiChoiceModeWrapper(listener));
    }

    private void animateDragToStart(View currView, View nextView) {
        if (currView != null  && nextView != null && currView != nextView) {
            float topMargin = nextView.getTop() - currView.getTop();
            float leftMargin = nextView.getLeft() - currView.getLeft();

            Animation translateAnimation = new TranslateAnimation(leftMargin, 0, topMargin, 0);
            translateAnimation.setDuration(ANIMATION_DURATION);
            translateAnimation.setInterpolator(new AccelerateInterpolator());
            currView.startAnimation(translateAnimation);

        }
    }

    public View getViewFromId(long id) {
        int relativePosition = getFirstVisiblePosition();
        NoteAdapter adapter = (NoteAdapter) getAdapter();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int position = relativePosition + i;
            long itemId = adapter.getItemId(position);
            if (itemId == id)
                return v;
        }
        return null;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHoverCell != null) {
            mHoverCell.draw(canvas);
        }
    }

    private class MultiChoiceModeWrapper implements MultiChoiceModeListener {
        MultiChoiceModeListener mWrapped;
        boolean isDraggable;
        boolean selectOnly;

        MultiChoiceModeWrapper(MultiChoiceModeListener listener) {
            this.mWrapped = listener;
            this.selectOnly = false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            if (mWrapped.onCreateActionMode(actionMode, menu)) {
                isDraggable = true;
                return true;
            }
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
            return mWrapped.onActionItemClicked(actionMode, item);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            mActionMode = actionMode;
            return mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            isDraggable = true;
            selectOnly = false;
            mActionMode = null;
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            if (isDraggable) {
                int pos = pointToPosition(mDraggedX, mDraggedY);
                int itemId = pos - getFirstVisiblePosition();
                mDraggedView = getChildAt(itemId);
                mDraggedItemId = getAdapter().getItemId(pos);
                mHoverCell = getAndHoverView(mDraggedView);

                mDraggedView.setVisibility(INVISIBLE);
                isDragging = true;
                isDraggable = false;
                if (!selectOnly)
                    selectOnly = true;
            }
            mWrapped.onItemCheckedStateChanged(mode, position, id, checked);
        }
    }
}