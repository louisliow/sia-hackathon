package com.percimal.singaporeairlines;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

    private Context mContext;
    private DaoSession daoSession;
    private List<Flight> flightList;

    public void updateFlightList() {
        flightList = new ArrayList<>();

        // Add mock flight.
        Flight f = new Flight();
        f.departure = new Date(1475897100000L);
        f.arrival = new Date(1475942100000L);;
        f.originAirport = "SIN";
        f.originTerminal = "3";
        f.destinationAirport = "SFO";
        f.destinationTerminal = "G";
        f.flightNumber = "2";
        f.marketingAirline = "SQ";
        f.operatingAirline = "SQ";
        f.bookingCode = "J";
        f.travelClass = "BUSINESS";
        f.bookingStatus = "CONFIRMED";
        f.pnr = "5AZ888";
        flightList.add(f);

        flightList.addAll(daoSession.getFlightDao().loadAll());
        notifyDataSetChanged();
    }

    public FlightsAdapter(Context mContext, DaoSession daoSession) {
        this.mContext = mContext;
        this.daoSession = daoSession;
        updateFlightList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Flight flight = flightList.get(position);
        holder.cardImage.setImageResource(mContext.getResources().getIdentifier(flight.destinationAirport.toLowerCase(), "drawable", mContext.getPackageName()));
        holder.title.setText(Constants.iataCodeToCity.get(flight.destinationAirport));
        holder.flightRoute.setText(flight.originAirport + " - " + flight.destinationAirport);
        holder.flightNumber.setText(flight.marketingAirline + " " + flight.flightNumber);
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
        public TextView flightRoute;
        public TextView flightNumber;
        public ImageView cardImage;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            flightRoute = (TextView) view.findViewById(R.id.flight_route);
            flightNumber = (TextView) view.findViewById(R.id.flight_number);
            cardImage = (ImageView) view.findViewById(R.id.card_image);
        }
    }
}
