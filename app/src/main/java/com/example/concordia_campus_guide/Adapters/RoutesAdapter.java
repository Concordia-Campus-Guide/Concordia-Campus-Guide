package com.example.concordia_campus_guide.Adapters;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Models.Routes.Bus;
import com.example.concordia_campus_guide.Models.Routes.Car;
import com.example.concordia_campus_guide.Models.Direction;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.Models.Routes.Subway;
import com.example.concordia_campus_guide.Models.Routes.Train;
import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.example.concordia_campus_guide.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;
import androidx.annotation.NonNull;

import org.w3c.dom.Text;

public class RoutesAdapter extends ArrayAdapter<Route> {

    List<Route> routes;
    Context context;

    private static class ViewHolder {
        private TextView textView;
    }

    public RoutesAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Route> routes) {
        super(context, textViewResourceId, routes);
        this.routes = routes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RoutesAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.list_routes, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        }

        else { viewHolder = (RoutesAdapter.ViewHolder) convertView.getTag(); }

        LinearLayout routeOverviewLayout = convertView.findViewById(R.id.routeOverviewLayout);
        LinearLayout main = convertView.findViewById(R.id.main);
        ImageView mainTransportType = convertView.findViewById(R.id.mainTransportType);
        FlexboxLayout details = convertView.findViewById(R.id.details);
        TextView duration = convertView.findViewById(R.id.duration);

        LinearLayout bottom = convertView.findViewById(R.id.bottom);
        TextView arrivalAndDepartureTime = convertView.findViewById(R.id.arrivalAndDepartureTime);

        Route route = getItem(position);
        if (route!= null) {

            if (route.getMainTransportType().equals(ClassConstants.WALKING)) {
                mainTransportType.setImageResource(R.drawable.ic_directions_walk_red);

                TextView summary = new TextView(context);
                summary.setText(route.getSummary());
                summary.setGravity(Gravity.CENTER);
                details.addView(summary);

                duration.setText(route.getDuration());
                
            } else if (route.getMainTransportType().equals(ClassConstants.DRIVING)) {
                mainTransportType.setImageResource(R.drawable.ic_directions_car_red);

                TextView summary = new TextView(context);
                summary.setText(route.getSummary());
                summary.setGravity(Gravity.CENTER);
                details.addView(summary);

                duration.setText(route.getDuration());
            }
            else { // TRANSIT
                mainTransportType.setImageResource(R.drawable.ic_directions_bus_red);
                duration.setText(route.getDuration());

                for(int i = 0; i < route.getSteps().size(); i++) {
                    TransportType step = route.getSteps().get(i);

                    if (step instanceof Bus) {
                        ImageView busIcon = new ImageView(context);
                        busIcon.setImageResource(R.drawable.ic_directions_bus_red);

                        TextView busNumber = new TextView(context);
                        busNumber.setText(((Bus) step).getBusNumber());
                        busNumber.setGravity(Gravity.CENTER);

                        details.addView(busIcon);
                        details.addView(busNumber);
                    }

                    else if (step instanceof Subway) {
                        ImageView subwayIcon = new ImageView(context);
                        subwayIcon.setImageResource(pickCorrectSubwayColor(((Subway) step).getColor()));

                        details.addView(subwayIcon);
                    }

                    else if (step instanceof Train) {
                        ImageView trainIcon = new ImageView(context);
                        trainIcon.setImageResource(R.drawable.ic_train_red);

                        TextView trainShortName = new TextView(context);
                        trainShortName.setText(((Train) step).getTrainShortName());
                        trainShortName.setGravity(Gravity.CENTER);

                        details.addView(trainIcon);
                        details.addView(trainShortName);
                    }

                    else if (step instanceof Walk) {
                        ImageView walkIcon = new ImageView(context);
                        walkIcon.setImageResource(R.drawable.ic_directions_walk_red);

                        TextView durationWalk = new TextView(context);
                        durationWalk.setText(((Walk) step).getDuration());
                        durationWalk.setGravity(Gravity.CENTER);

                        details.addView(walkIcon);
                        details.addView(durationWalk);
                    }

                    if(i < route.getSteps().size() - 1) {
                        ImageView arrow = new ImageView(context);
                        arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_grey);
                        details.addView(arrow);
                    }
                }
            }

            if(route.getArrivalTime().isEmpty() || route.getDepartureTime().isEmpty())
                bottom.setVisibility(LinearLayout.GONE);
            else
                arrivalAndDepartureTime.setText(route.getDepartureTime() + " - " + route.getArrivalTime());


        }
        else
            viewHolder.textView.setText("No routes available.");

        return convertView;
    }
    @Override
    public Route getItem(int i){
        return routes.get(i);
    }
    @Override
    public int getCount(){
        return routes.size();
    }

    private int pickCorrectSubwayColor(String color) {
        if(color.equalsIgnoreCase("#0074b9"))
            return R.drawable.ic_subway_blue;
        else if(color.equalsIgnoreCase("#f27f2f"))
            return R.drawable.ic_subway_orange;
        else if(color.equalsIgnoreCase("#00a556"))
            return R.drawable.ic_subway_green;
        else if(color.equalsIgnoreCase("#f2d101"))
            return R.drawable.ic_subway_yellow;

        return -1;
    }
}