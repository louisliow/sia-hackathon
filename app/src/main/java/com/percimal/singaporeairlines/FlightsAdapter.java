package com.percimal.singaporeairlines;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    private Context mContext;
    private List<Flight> flightList;

    public FlightsAdapter(Context mContext, List<Flight> flightList) {
        this.mContext = mContext;
        this.flightList = flightList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Flight flight = flightList.get(position);
        holder.title.setText("Flight from " + flight.originAirport + " to " + flight.destinationAirport);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, FlightDetailActivity.class);
                intent.putExtra("flight", flight);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return flightList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }
}
