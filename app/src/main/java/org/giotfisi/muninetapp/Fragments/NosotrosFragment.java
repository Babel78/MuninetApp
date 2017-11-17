package org.giotfisi.muninetapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.giotfisi.muninetapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NosotrosFragment extends Fragment {


    public NosotrosFragment() {
        // Required empty public constructor
    }

    public static NosotrosFragment newInstance(){
        return new NosotrosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nosotros, container, false);
    }

}
