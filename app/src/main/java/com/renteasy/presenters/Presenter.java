package com.renteasy.presenters;
import com.renteasy.views.View;

/**
 * Created by RUPESH on 7/30/2016.
 */
public interface Presenter {
       void onStart();

        void onStop();

        void onPause();

        void attachView (View v);

        void onCreate();

}
