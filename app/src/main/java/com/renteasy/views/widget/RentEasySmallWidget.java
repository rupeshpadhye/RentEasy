package com.renteasy.views.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.renteasy.R;
import com.renteasy.views.activity.SplashActivity;

/**
 * Created by RUPESH on 9/12/2016.
 */
public class RentEasySmallWidget extends AppWidgetProvider {

    private Context mContext;
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        mContext = context;
        for (int widgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, widgetId);
        }
    }

    private void updateAppWidget(Context context,
                                 AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.app_widget_small);
        Intent intent = new Intent(mContext, SplashActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_img_clicker, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

}
