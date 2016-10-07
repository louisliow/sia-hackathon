package com.percimal.singaporeairlines;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class UberAuthFragment extends Fragment {

    OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uber_auth, container, false);
        view.findViewById(R.id.uber_login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.uberLogin();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UberRequestFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void loginFailCallback() {
        Toast.makeText(getContext(), "Unable to connect to Uber", Toast.LENGTH_SHORT).show();
    }

    public interface OnFragmentInteractionListener {
        void uberLogin();
    }
}
