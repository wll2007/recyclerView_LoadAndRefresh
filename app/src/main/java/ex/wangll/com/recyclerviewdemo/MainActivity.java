package ex.wangll.com.recyclerviewdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;


import com.recyclerloadandrefresh.library.LoadRecyclerView;
import com.recyclerloadandrefresh.library.MultiTypeSupport;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadRecyclerView.onRefreshListener,LoadRecyclerView.onLoadListener,MultiTypeSupport<String> {
    private LoadRecyclerView mview;
    private testAdapter mAdapter;
    private List<String> data =  new ArrayList<>();
    private DefaultRefreshRecyclerViewCreator mCreator = new DefaultRefreshRecyclerViewCreator();
    private boolean mRefreshing = false;
    private boolean mLoading = false;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 1){
                mAdapter.notifyDataSetChanged();
                mview.onStopLoad();
            }else {
                mAdapter.notifyDataSetChanged();
                mview.onStopRefresh();
            }
        }
    };
    private DefaultLoadRecyclerViewCreator mLoadCreator = new DefaultLoadRecyclerViewCreator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mview = (LoadRecyclerView) findViewById(R.id.recyView);
        int i = 14;
        while (i > 0) {
            data.add(String.valueOf(i--));
        }
        mAdapter = new testAdapter(this, this ,data);
        mview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        mview.setLayoutManager(new GridLayoutManager(this,3));
//        mview.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        mview.setAdapter(mAdapter);
        mview.addRefreshViewCreator(mCreator);
        mview.setRefreshListener(this);
        mview.addLoadViewCreator(mLoadCreator);
        mview.setLoadListener(this);
        mview.addItemDecoration(new DividerLineDecoration(this,DividerLineDecoration.VERTICAL,true));
        //mview.addItemDecoration(new DividerGridItemDecoration(this,true,true));
    }


    @Override
    public void onRefresh() {
        //加载和刷新不能同时进行,防止更新data时出现竞争关系
        if(mLoading){
            Toast.makeText(this,"正在加载,稍后",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(0);
            return;
        }
        Thread  thread = new Thread(){
            @Override
            public void run() {

                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int base = Integer.parseInt(data.get(0));
                int i = 1;
                while (i <= 12) {
                    data.add(0,String.valueOf(i+base));
                    i++;
                }

                mRefreshing = false;
                mHandler.sendEmptyMessage(0);  //需要去主线程中更新Adapter
            }
        };
        thread.start();
        mRefreshing = true;
    }


    @Override
    public void onLoad() {
        //加载和刷新不能同时进行,防止更新data时出现竞争关系
        if(mRefreshing){
            Toast.makeText(this,"正在刷新,稍后",Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessage(1);
            return;
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int base = Integer.parseInt(data.get(data.size() - 1));
                int i = 1;
                while(i <=12)
                {
                    data.add(String.valueOf(-i + base));
                    i++;
                }
                mLoading = false;
                mHandler.sendEmptyMessage(1);
            }
        };
        thread.start();
        mLoading = true;
    }

    @Override
    public int getLayoutId(String item, int position) {
        if(position == 6){
            return R.layout.other;
        }
        return R.layout.item;
    }
}
