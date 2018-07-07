package com.renteasy.views.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.repository.DealsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/13/2016.
 */
public class HotDealsFactory  implements RemoteViewsService.RemoteViewsFactory {

    private int mAppWidgetId;
    private Context mContext;
    private ArrayList<String> mDealList = new ArrayList<>();
    private static final String TAG=HotDealsFactory.class.getName();
    @Inject DealsRepository dealsRepository;
    @Inject HotDealsFactory(){

    }


    public HotDealsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        App.getAppComponent(mContext).inject(this);
        getDeals();
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mDealList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        String deal=mDealList.get(position);
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.hot_deals_row);
        row.setTextViewText(R.id.deal, deal);
        Intent i = new Intent();
        Bundle extras = new Bundle();
        //extras.putSerializable(AppConstant.PROD_DETAIL, arrayList.get(position));
        i.putExtras(extras);
        row.setOnClickFillInIntent(R.id.deal, i);
        return row;

    }

    @Override
    public RemoteViews getLoadingView() {

       /* RemoteViews rv = new RemoteViews(
                mApp.getAndroidContext().getPackageName(),
                R.layout.appwidget_loading_item);
        rv.setProgressBar(R.id.appwidget_loading_item, 0, 0, true);
        return rv;*/
       return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
    }

    private void getDeals() {
        dealsRepository.getTopDeals(new HotDealsResponse() {
            @Override
            public void onSuccess(List<String> response) {
                mDealList.addAll(response);
                AppWidgetManager.getInstance(mContext)
                        .notifyAppWidgetViewDataChanged(
                                mAppWidgetId, R.id.widget_list_view);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
            }
        });
    }

    public interface HotDealsResponse{
        void onSuccess(List<String> response);
        void onError( String error);
    }
}
