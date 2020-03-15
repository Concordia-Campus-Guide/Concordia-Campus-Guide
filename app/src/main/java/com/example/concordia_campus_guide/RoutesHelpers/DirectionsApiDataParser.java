package com.example.concordia_campus_guide.RoutesHelpers;

import android.os.AsyncTask;
import android.util.Log;

import com.example.concordia_campus_guide.model.DirectionsResult;
import com.example.concordia_campus_guide.model.DirectionsRoute;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

public class DirectionsApiDataParser extends AsyncTask<String, Integer, List<DirectionsRoute>> {
    private List<DirectionsRoute> allRoutes;

    // Parsing the data
    @Override
    protected List<DirectionsRoute> doInBackground(String... jsonData) {

        allRoutes = new ArrayList<>();

        try {
            Log.d("mylog", "Executing routes");

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            DirectionsResult directionsResult = gson.fromJson(jsonData[0], DirectionsResult.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return allRoutes;
    }


//    // Executes in UI thread, after the parsing process
//    @Override
//    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
//        ArrayList<LatLng> points;
//        PolylineOptions lineOptions = null;
//        // Traversing through all the routes
//        for (int i = 0; i < result.size(); i++) {
//            points = new ArrayList<>();
//            lineOptions = new PolylineOptions();
//            // Fetching i-th route
//            List<HashMap<String, String>> path = result.get(i);
//            // Fetching all the points in i-th route
//            for (int j = 0; j < path.size(); j++) {
//                HashMap<String, String> point = path.get(j);
//                double lat = Double.parseDouble(point.get("lat"));
//                double lng = Double.parseDouble(point.get("lng"));
//                LatLng position = new LatLng(lat, lng);
//                points.add(position);
//            }
//            // Adding all the points in the route to LineOptions
//            lineOptions.addAll(points);
//            if (directionMode.equalsIgnoreCase("walking")) {
//                lineOptions.width(10);
//                lineOptions.color(Color.MAGENTA);
//            } else {
//                lineOptions.width(20);
//                lineOptions.color(Color.BLUE);
//            }
//            Log.d("mylog", "onPostExecute lineoptions decoded");
//        }
//
//    }
}