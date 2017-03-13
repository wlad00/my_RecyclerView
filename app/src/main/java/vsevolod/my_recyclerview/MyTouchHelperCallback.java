package vsevolod.my_recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

/**
 * Created by Vsevolod on 12.03.17.
 */

public class MyTouchHelperCallback extends ItemTouchHelper.Callback {

    private MyTouchHelperListener listener;
    private Context context;

    public MyTouchHelperCallback( Context context,MyTouchHelperListener listener) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(ItemTouchHelper.DOWN|ItemTouchHelper.UP
                ,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
            , RecyclerView.ViewHolder target) {
        listener.onMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        listener.onSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        Log.d("MY_TAG", "onChildDraw() called with: c = [" + c + "], recyclerView = [" + recyclerView + "], viewHolder = [" + viewHolder + "], dX = [" + dX + "], dY = [" + dY + "], actionState = [" + actionState + "], isCurrentlyActive = [" + isCurrentlyActive + "]");

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            View view = viewHolder.itemView;
            Paint paint = new Paint();
            Bitmap bitmap;
            if (dX > 0) { // swiping right
                paint.setColor(context.getResources().getColor(R.color.swipe_right));
                bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_check);
                float height = (view.getHeight()/2) - (bitmap.getHeight()/2);

                c.drawRect((float)view.getLeft(),(float)view.getTop(),dX,(float)view.getBottom(),paint);
                c.drawBitmap(bitmap,96f,view.getTop()+ height,null);
                if (dX < 200){
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            } else {
                paint.setColor(context.getResources().getColor(R.color.swipe_left));
                bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_history);
                float height = (view.getHeight()/2) - (bitmap.getHeight()/2);
                float bimapWidth = bitmap.getWidth();

                c.drawRect((float)view.getRight() + dX,(float)view.getTop(),(float)view.getRight(),(float)view.getBottom(),paint);
                c.drawBitmap(bitmap,((float)view.getRight()-bimapWidth)-96f,(float)view.getTop() + height,null);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }


//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    interface MyTouchHelperListener{
        void onSwiped(int position);
        void onMoved(int from, int to);
    }
}
