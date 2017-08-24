package ex.wangll.com.recyclerviewdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.recyclerloadandrefresh.library.LoadRecyclerView;
import com.recyclerloadandrefresh.library.LoadViewCreator;


/**
 * Created by Administrator on 2017/8/21 0021.
 */

public class DefaultLoadRecyclerViewCreator implements LoadViewCreator {
    private View mLoadView;
    private ProgressBar mProbar;
    private TextView mLoadText;
    private ImageView imageView;

    @Override
    public View getLoadView(Context context, ViewGroup parent) {
        mLoadView = LayoutInflater.from(context).inflate(R.layout.load,parent,false);
        mLoadText = (TextView)mLoadView.findViewById(R.id.Txt_load);
        mProbar = (ProgressBar)mLoadView.findViewById(R.id.prb_load);
        imageView = (ImageView)mLoadView.findViewById(R.id.image_icon);
        return mLoadView;
    }

    @Override
    public void onPull(int currentRefreshHeight, int RefreshViewHeight, int status) {
        switch (status){
            case LoadRecyclerView.LOAD_PULLUPNING:
                imageView.setRotation(180);
                mLoadText.setText(mLoadView.getResources().getString(R.string.down));
                break;
            case LoadRecyclerView.LOAD_LOOSEN:
                imageView.setRotation(0);
                mLoadText.setText(mLoadView.getResources().getString(R.string.load_loosen));
                break;
        }

    }

    @Override
    public void onLoading() {
        imageView.setRotation(0);
        imageView.setVisibility(View.GONE);
        mProbar.setVisibility(View.VISIBLE);
        mLoadText.setText(mLoadView.getResources().getString(R.string.loading));

    }

    @Override
    public void onStopLoad() {
        imageView.setRotation(0);
        imageView.setVisibility(View.VISIBLE);
        mProbar.setVisibility(View.GONE);
        mLoadText.setText(mLoadView.getResources().getString(R.string.down));
    }

}
