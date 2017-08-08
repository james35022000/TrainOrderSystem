package jcchen.taitiesyunbao;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by JCChen on 2017/8/8.
 */

public class StoreContainer extends FrameLayout implements Container {

    private boolean isLoading;
    private List<StoreInfo> storeList;

    private RecyclerView store_recyclerView;
    private StoreRecyclerViewAdapter adapter;

    private StoreInfoPresenter presenter;

    private Container container;
    private Context context;

    public StoreContainer (Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.presenter = new StoreInfoPresenter();
        this.container = this;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        store_recyclerView = (RecyclerView) getChildAt(0);
        storeList = new ArrayList<>();
        adapter = new StoreRecyclerViewAdapter(context, storeList);
        store_recyclerView.setAdapter(adapter);
        store_recyclerView.setLayoutManager(new LinearLayoutManager(context));
        store_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalCount = store_recyclerView.getLayoutManager().getItemCount() - 1;
                int lastVisibleItem = ((LinearLayoutManager) store_recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (!isLoading && totalCount < lastVisibleItem + 2 && presenter.getTotalCount() > 0) {
                    isLoading = true;
                    presenter.showStore(container);
                }
            }
        });
        presenter.getStoreInfo(container);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void showItem(Object object) {
        storeList.add((StoreInfo) object);
        adapter.notifyItemChanged(storeList.size() - 1);
        adapter.notifyItemChanged(storeList.size());
    }

    @Override
    public void loadingState(boolean state) {
        adapter.setLoadingState(state);
    }

}