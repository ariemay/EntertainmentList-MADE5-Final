package com.ariemay.entertainmentlistmade3.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * The configuration screen for the {@link FavoriteWidget FavoriteWidget} AppWidget.
 */
public class FavoriteWidgetConfigureActivity extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackConf(this.getApplicationContext(), intent);
    }

}
