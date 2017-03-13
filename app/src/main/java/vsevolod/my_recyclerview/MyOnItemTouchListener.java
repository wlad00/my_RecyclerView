package vsevolod.my_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by Vsevolod on 09.03.17.
 */

public class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    public MyOnItemTouchListener(Context context, RecyclerView rv, final MyOnItemClickListener listener) {

        recyclerView = rv;
        gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View view = recyclerView.findChildViewUnder(e.getX(),e.getY());
                Log.d("Touch","onSingle");

                if (view != null){
                    Button clickBtn = (Button) view.findViewById(R.id.clickBtn);

                    if (e.getX() > clickBtn.getX() && e.getX() < clickBtn.getX() + clickBtn.getMeasuredWidth()
                            && e.getY() > view.getY() + clickBtn.getY()
                            && e.getY() < view.getY() + clickBtn.getY() +clickBtn.getMeasuredHeight()){
                        listener.onButtonClick(clickBtn,recyclerView.getChildAdapterPosition(view));
                    } else {
                        listener.onItemClick(view,recyclerView.getChildAdapterPosition(view));
                    }
                }
                return true;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    interface MyOnItemClickListener{
        void onItemClick(View view, int position);
        void onButtonClick(View view, int position);
    }
}
