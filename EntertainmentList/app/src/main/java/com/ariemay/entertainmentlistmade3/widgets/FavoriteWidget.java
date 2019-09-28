package com.ariemay.entertainmentlistmade3.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.ariemay.entertainmentlistmade3.R;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link FavoriteWidgetConfigureActivity FavoriteWidgetConfigureActivity}
 */
public class FavoriteWidget extends AppWidgetProvider {

    public static final String EXTRA_ITEM = "com.ariemay.entertainmentlistmade3.EXTRA_ITEM";
    public static final String UPDATE_ACTION = "com.ariemay.entertainmentlistmade3.UPDATE_ACTION";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, FavoriteWidgetConfigureActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);
        views.setRemoteAdapter(R.id.stack_gambar, intent);
        views.setEmptyView(R.id.stack_gambar, R.id.nodata);

        Intent updateIntent = new Intent(context, FavoriteWidget.class);
        updateIntent.setAction(FavoriteWidget.UPDATE_ACTION);
        updateIntent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(context, 400, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.stack_gambar, updatePendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (intent.getAction() != null) {
            if (intent.getAction().equals(UPDATE_ACTION)){
                ComponentName thisWidget = new ComponentName(context, FavoriteWidget.class);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_gambar);
            }
        }
        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

