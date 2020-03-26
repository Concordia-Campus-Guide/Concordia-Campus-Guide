package com.example.concordia_campus_guide.Adapters;
import android.content.Context;
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
import com.example.concordia_campus_guide.Models.Routes.TransportType;
import com.example.concordia_campus_guide.Models.Routes.Walk;
import com.example.concordia_campus_guide.R;
import java.util.List;
import androidx.annotation.NonNull;

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

        else {
            viewHolder = (RoutesAdapter.ViewHolder) convertView.getTag();
        }

        Route route = getItem(position);
        if (route!= null) {
            LinearLayout routeOverviewLayout;

            if (route.getMainTransportType().equals(ClassConstants.WALKING)) {
                routeOverviewLayout = convertView.findViewById(R.id.routeOverviewLayout);

                ImageView walkIcon = new ImageView(context);
                walkIcon.setImageResource(R.drawable.ic_directions_walk_red);

                TextView summary = new TextView(context);
                summary.setText(route.getSummary());

                TextView duration = new TextView(context);
                duration.setText(route.getDuration());

                routeOverviewLayout.addView(walkIcon);
                routeOverviewLayout.addView(summary);
                routeOverviewLayout.addView(duration);

            } else if (route.getMainTransportType().equals(ClassConstants.DRIVING)) {
                routeOverviewLayout = convertView.findViewById(R.id.routeOverviewLayout);

                ImageView carIcon = new ImageView(context);
                carIcon.setImageResource(R.drawable.ic_directions_car_red);

                TextView summary = new TextView(context);
                summary.setText(route.getSummary());

                TextView duration = new TextView(context);
                duration.setText(route.getDuration());

                routeOverviewLayout.addView(carIcon);
                routeOverviewLayout.addView(summary);
                routeOverviewLayout.addView(duration);
            }
            else { // TRANSIT
                routeOverviewLayout = convertView.findViewById(R.id.routeOverviewLayout);
                for(TransportType step: route.getSteps()) {
                    if (step instanceof Bus) {
                        ImageView carIcon = new ImageView(context);
                        carIcon.setImageResource(R.drawable.ic_directions_car_red);
                    }
                    else if (step instanceof Subway) {
                    }
                }
            }
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
}