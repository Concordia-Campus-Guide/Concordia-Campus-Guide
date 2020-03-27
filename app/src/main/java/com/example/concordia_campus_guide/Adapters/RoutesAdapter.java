package com.example.concordia_campus_guide.Adapters;
import android.annotation.SuppressLint;
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

    // UI elements
    ImageView mainTransportType;
    FlexboxLayout details;
    TextView duration;
    LinearLayout bottom;
    TextView arrivalAndDepartureTime;
    LinearLayout routeOverviewLayout;
    LinearLayout main;

    public RoutesAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Route> routes) {
        super(context, textViewResourceId, routes);
        this.routes = routes;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_routes, parent, false);
        initComponent(convertView);

        Route route = getItem(position);

        if (route!= null)
            setUIElements(route);
        else
            displayInfoMsg();

        return convertView;
    }

    private void initComponent(View convertView) {
        routeOverviewLayout = convertView.findViewById(R.id.routeOverviewLayout);
        main = convertView.findViewById(R.id.main);
        mainTransportType = convertView.findViewById(R.id.mainTransportType);
        details = convertView.findViewById(R.id.details);
        duration = convertView.findViewById(R.id.duration);
        bottom = convertView.findViewById(R.id.bottom);
        arrivalAndDepartureTime = convertView.findViewById(R.id.arrivalAndDepartureTime);
    }

    @Override
    public Route getItem(int i){
        return routes.get(i);
    }

    @Override
    public int getCount(){
        return routes.size();
    }

    // Helper methods for setting UI elements in the Routes Overview section of the Routes Activity page
    private void setUIElements(Route route) {

        switch(route.getMainTransportType()) {
            case ClassConstants.WALKING:
                setUIWalk(route);
            case ClassConstants.DRIVING:
                setUIDriving(route);
            case ClassConstants.TRANSIT:
                setUITransit(route);
        }

        setBottomLayout(route);
    }
    private void setUIWalk(Route route) {
        mainTransportType.setImageResource(R.drawable.ic_directions_walk_red);

        TextView summary = new TextView(context);
        summary.setText(route.getSummary());
        summary.setGravity(Gravity.CENTER);
        details.addView(summary);

        duration.setText(route.getDuration());
    }

    private void setUIDriving(Route route) {
        mainTransportType.setImageResource(R.drawable.ic_directions_car_red);

        TextView summary = new TextView(context);
        summary.setText(route.getSummary());
        summary.setGravity(Gravity.CENTER);
        details.addView(summary);

        duration.setText(route.getDuration());
    }

    private void setUITransit(Route route) {
        mainTransportType.setImageResource(R.drawable.ic_directions_bus_red);
        duration.setText(route.getDuration());

        // Go through each steps in the route
        for(int i = 0; i < route.getSteps().size(); i++) {
            TransportType step = route.getSteps().get(i);

            if (step instanceof Bus) { setUIBus((Bus) step); }
            else if (step instanceof Subway) { setUISubway((Subway) step); }
            else if (step instanceof Train) { setUITrain((Train) step); }
            else if (step instanceof Walk) { setUIWalkInTransit((Walk) step); }

            if(i < route.getSteps().size() - 1) { setUIArrow(); }
        }
    }

    private void setUIWalkInTransit(Walk walk) {
        ImageView walkIcon = new ImageView(context);
        walkIcon.setImageResource(R.drawable.ic_directions_walk_red);

        TextView durationWalk = new TextView(context);
        durationWalk.setText(walk.getDuration());
        durationWalk.setGravity(Gravity.CENTER);

        details.addView(walkIcon);
        details.addView(durationWalk);
    }

    private void setUIBus(Bus bus) {
        ImageView busIcon = new ImageView(context);
        busIcon.setImageResource(R.drawable.ic_directions_bus_red);

        TextView busNumber = new TextView(context);
        busNumber.setText(bus.getBusNumber());
        busNumber.setGravity(Gravity.CENTER);

        details.addView(busIcon);
        details.addView(busNumber);
    }

    private void setUISubway(Subway subway) {
        ImageView subwayIcon = new ImageView(context);
        subwayIcon.setImageResource(pickCorrectSubwayColor(subway.getColor()));

        details.addView(subwayIcon);
    }

    private int pickCorrectSubwayColor(String color) {
        if(color.equalsIgnoreCase(context.getResources().getString(R.string.subwayBlue)))
            return R.drawable.ic_subway_blue;
        else if(color.equalsIgnoreCase(context.getResources().getString(R.string.subwayOrange)))
            return R.drawable.ic_subway_orange;
        else if(color.equalsIgnoreCase(context.getResources().getString(R.string.subwayGreen)))
            return R.drawable.ic_subway_green;
        else if(color.equalsIgnoreCase(context.getResources().getString(R.string.subwayYellow)))
            return R.drawable.ic_subway_yellow;

        return -1;
    }

    private void setUITrain(Train train) {
        ImageView trainIcon = new ImageView(context);
        trainIcon.setImageResource(R.drawable.ic_train_red);

        TextView trainShortName = new TextView(context);
        trainShortName.setText(train.getTrainShortName());
        trainShortName.setGravity(Gravity.CENTER);

        details.addView(trainIcon);
        details.addView(trainShortName);
    }

    private void setUIArrow() {
        ImageView arrow = new ImageView(context);
        arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_grey);
        details.addView(arrow);
    }

    private void setBottomLayout(Route route) {
        if(route.getArrivalTime().isEmpty() || route.getDepartureTime().isEmpty())
            bottom.setVisibility(LinearLayout.GONE);
        else
            arrivalAndDepartureTime.setText(route.getDepartureTime() + " - " + route.getArrivalTime());
    }

    private void displayInfoMsg() {
        bottom.setVisibility(LinearLayout.GONE);
        details.setVisibility(LinearLayout.GONE);
        arrivalAndDepartureTime.setVisibility(LinearLayout.GONE);
        mainTransportType.setVisibility(LinearLayout.GONE);
        main.setVisibility(LinearLayout.GONE);

        TextView warningMsg = new TextView(context);
        warningMsg.setText(R.string.warning_msg);

        routeOverviewLayout.addView(warningMsg);
    }
}