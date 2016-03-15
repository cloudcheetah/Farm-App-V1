package com.forateq.farmapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forateq.farmapp.R;

/**
 * Created by Vallejos Family on 3/13/2016.
 */
public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        return v;
    }

    public ProfileFragment() {
        super();
    }
}
