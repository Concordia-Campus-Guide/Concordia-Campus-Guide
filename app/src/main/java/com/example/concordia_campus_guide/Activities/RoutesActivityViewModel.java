package com.example.concordia_campus_guide.Activities;

import android.location.Location;
import androidx.lifecycle.ViewModel;
import com.example.concordia_campus_guide.BuildConfig;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.Models.Place;
import com.google.android.gms.maps.model.LatLng;
import com.example.concordia_campus_guide.Models.Shuttle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RoutesActivityViewModel extends ViewModel {

    private AppDatabase appDB;
    private Place from;
    private Place to;
    private List<Shuttle> shuttles;
    private final String noShuttles = "No available routes using the shuttle service";
    private DirectionsResult directionsResult;

    public RoutesActivityViewModel(AppDatabase appDB) {
        this.appDB = appDB;
    }

    public void setTo(Place place){
        this.to = place;
    }

    public Place getTo(){
        return to;
    }

    public Place getFrom(){
        return from;
    }

    public void setFrom(Place from) {
        this.from = from;
    }

    public DirectionsResult getDirectionsResult() {
        return directionsResult;
    }

    public void setDirectionsResult(DirectionsResult directionsResult) {
        this.directionsResult = directionsResult;
    }

    public void setShuttles() {
        shuttles = appDB.shuttleDao().getAll();
    }

    public List<Shuttle> getShuttles() {
        String campusFrom = "";
        String campusTo = "";
        if (getFrom() != null && getTo() != null && from.getCampus() != null && to.getCampus() != null) {
                campusFrom = from.getCampus();
                campusTo =  to.getCampus();
            if (campusFrom.compareTo(campusTo) == 0) {
                return null;
            }
        }

        Calendar calendar = Calendar.getInstance();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime());
        SimpleDateFormat time = new SimpleDateFormat("HH.mm");
        shuttles = appDB.shuttleDao().getScheduleByCampusAndDayAndTime(campusFrom, day, Double.parseDouble(time.format(calendar.getTime())));
        return shuttles;
    }

    public String getShuttleDisplayText(List<Shuttle> shuttles) {
        String content = "";
        if (shuttles == null || shuttles.size() == 0) {
            return this.noShuttles;
        }
        String campusTo = shuttles.get(0).getCampus().compareTo("SGW") == 0 ? "LOY" : "SGW";
        for (Shuttle shuttle : shuttles) {
            content += shuttle.getCampus() + "  >   " + campusTo + ", \t leaves at: " + shuttle.getTime().toString().replace(".", ":") + "\n";
        }
        return content;
    }
}
