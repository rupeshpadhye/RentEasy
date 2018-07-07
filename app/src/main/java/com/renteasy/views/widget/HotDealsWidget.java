package com.renteasy.views.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.renteasy.App;
import com.renteasy.R;
import com.renteasy.constant.AppConstant;
import com.renteasy.views.activity.SplashActivity;

import javax.inject.Inject;

/**
 * Created by RUPESH on 9/12/2016.
 */
public class HotDealsWidget  extends AppWidgetProvider {

    public static String UPDATE_LIST = "UPDATE_LIST";
    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    public HotDealsWidget() {
        super();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        App.getAppComponent(context).inject(this);
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, HotDealsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.hot_deals_widget);
           if (!sharedPreferences.contains(AppConstant.USER)) {
                widget.setTextViewText(R.id.empty_view, context.getString(R.string.login_to_explore));
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                manager.updateAppWidget(appWidgetId, widget);
                return;
            }

            widget.setRemoteAdapter(appWidgetId, R.id.widget_list_view, intent);
            widget.setEmptyView(R.id.widget_list_view, R.id.empty_view);
            Intent clickIntent = new Intent(context, SplashActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.widget_list_view, clickPI);

          /*  Intent clickIntent_one = new Intent(context, HotDealsService.class);
            clickIntent_one.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            clickIntent_one.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context, 0, clickIntent_one,PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setOnClickPendingIntent(R.id.update_list, pendingIntentRefresh);
            widget.setPendingIntentTemplate(R.id.update_list, pendingIntentRefresh);*/

            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, HotDealsWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        /*if (intent.getAction().equalsIgnoreCase(APPWIDGET_UPDATE)) {
            updateWidget(context);
        }*/
        updateWidget(context);
        super.onReceive(context, intent);
    }
}
