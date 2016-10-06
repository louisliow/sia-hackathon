package com.percimal.singaporeairlines;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class UberAuthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uber_auth, container, false);
    }

    public void loginFailCallback() {
        Toast.makeText(getContext(), "Unable to connect to Uber", Toast.LENGTH_SHORT).show();
    }

}
