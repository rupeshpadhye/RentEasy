package com.renteasy.views.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by RUPESH on 9/13/2016.
 */
public class HotDealsService  extends RemoteViewsService {

        @Override
        public RemoteViewsFactory onGetViewFactory(Intent intent) {
            return new HotDealsFactory(getApplicationContext(), intent);
        }

}
