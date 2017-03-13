package vsevolod.my_recyclerview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

///12345
///6789
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MY_TAG";
    private RecyclerView myRecyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myRecyclerView.addItemDecoration(new DividerItemDecoration
                (this, DividerItemDecoration.VERTICAL));


        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MyAdapter(new MyAdapter.ClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Was clicke row " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemButtonclick(View view, int position) {
                Toast.makeText(MainActivity.this, "Was clicke btn " + position, Toast.LENGTH_SHORT).show();
            }
        });

        myRecyclerView.setAdapter(adapter);

        myRecyclerView.addOnItemTouchListener(new MyOnItemTouchListener
                (this, myRecyclerView, new MyOnItemTouchListener.MyOnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Was clicked " + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onButtonClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Was cklick on Button " + position, Toast.LENGTH_SHORT).show();
                    }
                }
                ));
        //=====================


        MyTouchHelperCallback callback = new MyTouchHelperCallback
                (this, new MyTouchHelperCallback.MyTouchHelperListener() {
                    @Override
                    public void onSwiped(int position) {
                        showDeleteDialog(position);
                    }

                    @Override
                    public void onMoved(int from, int to) {
                        adapter.move(from, to);
                    }
                });
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(myRecyclerView);

    }

    private void showDeleteDialog(final int position){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Delete row " + position)
                .setMessage("Are you sur that you want delete it?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.removeItem(position);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyItemChanged(position);
                    }
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_item){
            adapter.addItem();
        } else if (item.getItemId() == R.id.remove_item){
            adapter.removeItem();
        }
        return super.onOptionsItemSelected(item);
    }




}
