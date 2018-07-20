package com.noteIt.RecyclerViewClasses;

public interface ItemTouchHelperAdapter
{

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
