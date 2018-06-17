package com.example.adria.myappmvp.gridViewCustom;

import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.adria.myappmvp.task.MyDragShadowBuilder;
import com.example.adria.myappmvp.task.TaskAdapter;

import static android.content.ContentValues.TAG;

public class GridViewCustom extends GridView implements AdapterView.OnItemLongClickListener
{
    private final static long ANIMATION_DURATION = 225;

    private long mCurrViewId;
    private long mNextViewId;
    private View mCurrView;
    private View mNextView;

    private ActionMode mActionMode;

    private TaskAdapter mAdapter;

    private CompositeListener compositeListener;

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

    void init()
    {

        mAdapter = (TaskAdapter) getAdapter();
        compositeListener = new CompositeListener();
        super.setOnTouchListener(mOnTouchListener);
        super.setOnItemLongClickListener(compositeListener);
        super.setOnItemLongClickListener(mOnLongClickListener);
    }

    private AdapterView.OnItemLongClickListener mOnLongClickListener = new AdapterView.OnItemLongClickListener()
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, final View viewClick, final int index, final long l)
        {
            mCurrViewId = index;
            ClipData data = ClipData.newPlainText("DragData", "HOLA");

            callOnClick();

//            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP )
//                viewClick.startDrag(data, new DragShadowBuilder(viewClick), viewClick, 0);
//            else
//                viewClick.startDragAndDrop(data, new DragShadowBuilder(viewClick),viewClick,0 );
            viewClick.setVisibility(INVISIBLE);

            return true;
        }
    };



    private AdapterView.OnTouchListener mOnTouchListener = new AdapterView.OnTouchListener()
    {
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            mAdapter = (TaskAdapter) getAdapter();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                final GridView parent = (GridView) v;

                int x = (int) event.getX();
                int y = (int) event.getY();
                int position = parent.pointToPosition(x, y);

                if (position > AdapterView.INVALID_POSITION) {

                    int count = parent.getChildCount();
                    for (int i = 0; i < count; i++)
                    {
                        final View curr = parent.getChildAt(i);
                        curr.setOnDragListener(new View.OnDragListener() {


                            @Override
                            public boolean onDrag(View v, DragEvent event) {
                                boolean result = true;
                                int action = event.getAction();
                                switch (action) {
                                    case DragEvent.ACTION_DRAG_STARTED:
                                        //Log.e(TAG, "test: "+mNextViewId +" curr: " + mCurrViewId);

                                        return true;
                                    case DragEvent.ACTION_DRAG_LOCATION:

                                        break;
                                    case DragEvent.ACTION_DRAG_ENTERED:
                                        if(mActionMode!= null)
                                            mActionMode.finish();
                                        mCurrView = getViewFromId(mCurrViewId);
                                        int position = parent.pointToPosition((int)v.getX(), (int)v.getY());
                                        mNextViewId = mAdapter.getItemId(position);
                                        mNextView = getViewFromId(mNextViewId);
                                        Log.e(TAG, "onTouch: mNextViewId: " + mNextViewId);
                                        Log.e(TAG, "onTouch: mCurrViewId: " + mCurrViewId);
                                        if (mCurrViewId > -1 && mNextViewId > -1) {

                                            animateDragToStart(mCurrView, mNextView);
                                            mAdapter.swapItems((int) mCurrViewId,(int) mNextViewId);
                                            mAdapter.notifyDataSetChanged();
                                            mCurrViewId = mNextViewId;
                                        }
                                        break;
                                    case DragEvent.ACTION_DRAG_ENDED:

                                        final View droppedView = (View) event.getLocalState();
                                        droppedView.post(new Runnable(){
                                            @Override
                                            public void run() {
                                                droppedView.setVisibility(VISIBLE);
                                                if(mNextView != null )
                                                    mNextView.setVisibility(VISIBLE);
                                                if(mCurrView != null)
                                                    mCurrView.setVisibility(VISIBLE);
                                            }
                                        });
                                        break;
                                        case DragEvent.ACTION_DROP:

                                            break;
                                    default:

                                        if(mActionMode!= null)
                                            mActionMode.finish();
                                        result = false;
                                        break;
                                }

                                return result;
                            }
                        });
                    }
                }
            }
            return false;
        }
    };

    private void animateDragToStart(View currView, View nextView) {
        if(currView!= null && nextView!= null)
        {
            float topMargin = nextView.getY() - currView.getTop();
            float leftMargin = nextView.getX() - currView.getLeft();

            Animation translateAnimation = new TranslateAnimation(leftMargin,0,topMargin,0);
            translateAnimation.setDuration(ANIMATION_DURATION);
            translateAnimation.setInterpolator(new AccelerateInterpolator());
            currView.startAnimation(translateAnimation);
            currView.setVisibility(View.VISIBLE);
            nextView.setVisibility(INVISIBLE);
        }
    }

    @Override
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        compositeListener.registerListener(listener);
    }

    @Override
    public void setMultiChoiceModeListener(MultiChoiceModeListener listener) {
        super.setMultiChoiceModeListener(new MultiChoiceModeWrapper(listener));
    }

    private View getViewFromId(long id)
    {
        int relativePosition = getFirstVisiblePosition();

        for(int i = 0; i < getChildCount(); i++)
        {
            View v = getChildAt(i);
            int position = relativePosition + i;
            long itemId = mAdapter.getItemId(position);
            if(itemId == id)
                return v;
        }
        return null;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {

        mCurrViewId = index;
        ClipData data = ClipData.newPlainText("DragData", "HOPA");
        view.startDrag(data, new View.DragShadowBuilder(view), view, 0);
        view.setVisibility(INVISIBLE);

        return true;
    }

    private class MultiChoiceModeWrapper implements MultiChoiceModeListener {
        MultiChoiceModeListener mWrapped;
        boolean isDraggable;
        boolean selectOnly;

        public MultiChoiceModeWrapper(MultiChoiceModeListener listener) {
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
            if( isDraggable)
            {
                isDraggable = false;

                int item = position - getFirstVisiblePosition();
                View view = getChildAt(item);

                mCurrViewId = position;
                ClipData data = ClipData.newPlainText("DragData", "HOPA");

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP )
                     view.startDrag(data, new DragShadowBuilder(view), view, 0);
                else
                     view.startDragAndDrop(data, new DragShadowBuilder(view), view, 0);

                if(selectOnly == false) {
                    view.setVisibility(INVISIBLE);
                    selectOnly = true;
                }
            }
            mWrapped.onItemCheckedStateChanged(mode, position, id, checked);
        }
    }

}