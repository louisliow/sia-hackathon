package com.percimal.singaporeairlines;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class PorterRequestDialogFragment extends DialogFragment {

    public static enum PorterState {
        AVAILABLE, UNAVAILABLE, REQUESTED
    }

    OnFragmentInteractionListener mListener;
    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = inflater.inflate(R.layout.dialog_porter_request, null);

        View.OnClickListener requestPortalClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.available_view).setVisibility(View.GONE);
                view.findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);
                mListener.requestPorter();
            }
        };

        view.findViewById(R.id.request_porter_cash).setOnClickListener(requestPortalClickListener);
        view.findViewById(R.id.request_porter_miles).setOnClickListener(requestPortalClickListener);

        builder.setView(view);
        return builder.create();
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

    public void initialize(PorterState state) {
        view.findViewById(R.id.loading_indicator).setVisibility(View.GONE);
        switch (state) {
            case AVAILABLE:
                view.findViewById(R.id.available_view).setVisibility(View.VISIBLE);
                break;
            case UNAVAILABLE:
                view.findViewById(R.id.unavailable_view).setVisibility(View.VISIBLE);
                break;
            case REQUESTED:
                // TODO: Create view for this case.
                // view.findViewById(R.id.request_view).setVisibility(View.VISIBLE);
                break;
        }
    }

    public void requestCallback(boolean success) {
        view.findViewById(R.id.loading_indicator).setVisibility(View.GONE);
        if (success) {
            // Successfully requested.
            // TODO: Switch to view for requested case.
            // view.findViewById(R.id.request_view).setVisibility(View.VISIBLE);
        } else {
            // Failed to request.
            // TODO: Show error message.
            view.findViewById(R.id.available_view).setVisibility(View.VISIBLE);
        }
    }

    public interface OnFragmentInteractionListener {
        void requestPorter();
    }
}
