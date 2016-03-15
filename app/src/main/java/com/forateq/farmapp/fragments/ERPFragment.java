package com.forateq.farmapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.forateq.farmapp.FarmApplication;
import com.forateq.farmapp.R;
import com.forateq.farmapp.views.MainMenuView;

/**
 * Created by Vallejos Family on 3/13/2016.
 */
public class ERPFragment extends Fragment {

    private static LinearLayout containerScrollView;
    private MainMenuView mainMenuView;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.erp_fragment, container, false);
        init();
        return v;
    }

    public void init(){
        containerScrollView = (LinearLayout) v.findViewById(R.id.container);
        mainMenuView = new MainMenuView(getActivity());
        containerScrollView.addView(mainMenuView);
    }

    public static void changeView(View view){
        containerScrollView.removeAllViews();
        containerScrollView.addView(view);
    }

    public static void backView(){
        containerScrollView.removeAllViews();
        containerScrollView.addView(FarmApplication.viewDeque.removeLast());
    }

    public ERPFragment() {
        super();
    }
}
