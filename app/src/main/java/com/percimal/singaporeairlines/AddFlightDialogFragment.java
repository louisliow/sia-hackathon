package com.percimal.singaporeairlines;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class AddFlightDialogFragment extends DialogFragment {

    private OnFragmentInteractionListener mListener;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_add_flight, container, false);

        view.findViewById(R.id.add_flight_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText pnrInput = (EditText) view.findViewById(R.id.pnr);
                EditText lastNameInput = (EditText) view.findViewById(R.id.last_name);
                String pnr = pnrInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                Boolean hasError = false;
                if(pnr.equals("")) {
                    pnrInput.setError("Booking reference number is required");
                    hasError = true;
                }
                if (lastName.equals("")) {
                    lastNameInput.setError("Last name is required");
                    hasError = true;
                }
                if (!hasError) {
                    view.findViewById(R.id.loading_indicator).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.form).setVisibility(View.GONE);
                    mListener.addFlight(pnr, lastName);
                }
            }
        });

        return view;
    }

    public void addFlightCallback(Boolean isSuccessful) {
        if (isSuccessful) {
            Toast.makeText(getActivity(), "Flights added!", Toast.LENGTH_SHORT).show();
            dismiss();
        } else {
            Toast.makeText(getActivity(), "Unable to add flights!", Toast.LENGTH_SHORT).show();
            view.findViewById(R.id.loading_indicator).setVisibility(View.GONE);
            view.findViewById(R.id.form).setVisibility(View.VISIBLE);
        }
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

    public interface OnFragmentInteractionListener {
        void addFlight(String pnr, String lastName);
    }

}
