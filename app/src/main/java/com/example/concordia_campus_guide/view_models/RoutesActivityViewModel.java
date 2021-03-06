package com.example.concordia_campus_guide.view_models;

import androidx.lifecycle.ViewModel;

import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.Coordinates;
import com.example.concordia_campus_guide.models.Floor;
import com.example.concordia_campus_guide.models.Place;
import com.example.concordia_campus_guide.models.RoomModel;
import com.example.concordia_campus_guide.models.Shuttle;
import com.example.concordia_campus_guide.models.helpers.RouteBuilder;
import com.example.concordia_campus_guide.models.helpers.RouteBuilderDirector;
import com.example.concordia_campus_guide.models.routes.Route;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import static com.example.concordia_campus_guide.view_models.LocationFragmentViewModel.LOGGER;

public class RoutesActivityViewModel extends ViewModel {

    private AppDatabase appDB;
    private Place from;
    private Place to;
    private @ClassConstants.TransportType
    String transportType = ClassConstants.TRANSIT; // default value
    private List<Shuttle> shuttles;
    private DirectionsResult directionsResult;
    private List<Route> routeOptions;
    private RouteBuilderDirector director = new RouteBuilderDirector();

    public RoutesActivityViewModel(AppDatabase appDB) {
        this.appDB = appDB;
    }

    public void setTo(Place place) {
        this.to = place;
    }

    public Place getTo() {
        return to;
    }

    public String getToDisplayName() {
        return getTo().getDisplayName();
    }

    public Place getEntrance(Place place) {
        if (!(place instanceof RoomModel || place instanceof Floor)) return place;
        String floorCode = place instanceof RoomModel ? ((RoomModel) place).getFloorCode() : ((Floor) place).getFloorCode();
        Building building = appDB.buildingDao().getBuildingByBuildingCode(floorCode.substring(0, floorCode.indexOf('-')).toUpperCase());
        String entranceFloor = building.getBuildingCode() + "-" + building.getEntranceFloor();
        return appDB.roomDao().getRoomByIdAndFloorCode("entrance", entranceFloor);
    }

    public Coordinates getFromEntranceCoordinates() {
        return this.getEntrance(this.getFrom()).getCenterCoordinates();
    }

    public Coordinates getToEntranceCoordinates() {
        return this.getEntrance(this.getTo()).getCenterCoordinates();
    }

    public Place getFrom() {
        return from;
    }

    public String getFromDisplayName() {
        return getFrom().getDisplayName();
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

    public String getFromAndToCampus(Place from, Place to) {
        String campusFrom = "";
        String campusTo = "";
        if (from != null && to != null && from.getCampus() != null && to.getCampus() != null) {
            campusFrom = from.getCampus();
            campusTo = to.getCampus();
        }
        return campusFrom + "," + campusTo;
    }

    public List<Shuttle> filterShuttles() {
        String[] campuses = getFromAndToCampus(getFrom(), getTo()).split(",");
        // campus[0] is campusFrom and campus[1] is campusTo
        if (campuses.length == 0 || campuses[0].compareTo(campuses[1]) == 0) {
            return new ArrayList<>();
        }
        Calendar calendar = Calendar.getInstance();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime());
        SimpleDateFormat time = new SimpleDateFormat("HH.mm");
        shuttles = getShuttlesByDayAndTime(campuses[0], day, Double.parseDouble(time.format(calendar.getTime())));
        return shuttles;
    }

    public List<Shuttle> getShuttles() {
        return shuttles;
    }

    public List<Shuttle> getShuttlesByDayAndTime(String campus, String day, Double time) {
        return appDB.shuttleDao().getScheduleByCampusAndDayAndTime(campus, day, time);
    }

    public List<Route> adaptShuttleToRoutes(List<Shuttle> shuttles) {
        routeOptions = new ArrayList<>();
        int loopStop = shuttles.size() > 6 ? 6 : shuttles.size();
        for (int i = 0; i < loopStop; i++) {
            Shuttle shuttle = shuttles.get(i);
            BigDecimal bd = BigDecimal.valueOf(shuttle.getTime());
            String departureTime = bd.setScale(2, RoundingMode.HALF_UP).toString().replace(".", ":");
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            String arrivalDateString = "";
            try {
                Date arrivalDate = dateFormat.parse(departureTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(arrivalDate);
                calendar.add(Calendar.MINUTE, 25);
                arrivalDateString = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
            } catch (ParseException e) {
                LOGGER.log(Level.WARNING, e.getMessage(), e);
            }
            String campusFrom = shuttle.getCampus();
            String campusTo = campusFrom.compareTo("SGW") == 0 ? "LOY" : "SGW";

            RouteBuilder routeBuilder = new RouteBuilder();
            director.constructShuttleRoute(routeBuilder, departureTime, arrivalDateString, "25 mins", campusFrom + "," + campusTo);
            routeOptions.add(routeBuilder.getRoute());
        }
        return routeOptions;
    }

    public List<Route> getRouteOptions() {
        return routeOptions;
    }

    public void setRouteOptions(List<Route> routeOptions) {
        this.routeOptions = routeOptions;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public void noRoutesOptions(String noRoutes) {
        RouteBuilder routeBuilder = new RouteBuilder();
        director.constructRouteWithSummaryOnly(routeBuilder, noRoutes, transportType);
        Route noRoute = routeBuilder.getRoute();
        routeOptions.add(noRoute);
    }
}
