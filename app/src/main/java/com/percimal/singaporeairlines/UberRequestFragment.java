package com.percimal.singaporeairlines;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestView;


public class UberRequestFragment extends Fragment {

    private static final int PORTER_MENU_ITEM = 1;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_uber_request, container, false);
        RideParameters rideParams = new RideParameters.Builder()
                .setDropoffLocation(1.356673, 103.988000, "Terminal 3 Departure", "Airport Blvd")
                .build();
        RideRequestView rideRequestView = (RideRequestView) view.findViewById(R.id.ride_request_view);
        rideRequestView.setRideParameters(rideParams);
        rideRequestView.load();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MenuItem menuItem = menu.add(Menu.NONE, PORTER_MENU_ITEM, Menu.NONE, "Porter Service");
        menuItem.setIcon(R.drawable.ic_porter);
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case PORTER_MENU_ITEM:
                mListener.showPorterRequestDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnFragmentInteractionListener {
        void showPorterRequestDialog();
    }
}
