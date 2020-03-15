package com.example.concordia_campus_guide.RoutesHelpers;

import android.os.AsyncTask;

import com.example.concordia_campus_guide.model.DirectionsResult;
import com.example.concordia_campus_guide.model.DirectionsRoute;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DirectionsApiDataParser extends AsyncTask<String, Integer, List<DirectionsRoute>> {
    private List<DirectionsRoute> allRoutes;

    // Parsing the data
    @Override
    protected List<DirectionsRoute> doInBackground(String... jsonData) {

        allRoutes = new ArrayList<>();

        try {
//            jObject = new JSONObject(jsonData[0]);
//            routes = parse(jObject);
//            Log.d("mylog", "Executing routes");
//            Log.d("mylog", routes.toString());

//            String jData = jsonData[0];
//            JSONArray jRoutes = new JSONObject(jData).getJSONArray("routes");
//            for (int i = 0; i < jRoutes.length(); i++) {
//
//                String route = jRoutes.get(i).toString();
//                DirectionsRoute directionsRoute = new Gson().fromJson(route, DirectionsRoute.class);
//                allRoutes.add(directionsRoute);
//
//                JSONArray jLegs = new JSONObject(route).getJSONArray("legs");
//                for (int j = 0; j < jLegs.length(); j++) {
//                    String leg = jRoutes.get(i).toString();
//                    DirectionsLeg directionsLeg = new Gson().fromJson(leg, DirectionsLeg.class);
//                    directionsRoute.legs[i] = directionsLeg;
//                }
//            }

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create();

            DirectionsResult directionsResult = gson.fromJson(jsonData[0], DirectionsResult.class);




        } catch (Exception e) {
            e.printStackTrace();
        }
        return allRoutes;
    }

//    private List<DirectionsRoute> parse(JSONObject jObject) {
//
//        List<List<HashMap<String, String>>> routes = new ArrayList<>();
//        JSONArray jRoutes;
//        JSONArray jLegs;
//        JSONArray jSteps;
//        try {
//            jRoutes = jObject.getJSONArray("routes");
//            /** Traversing all routes */
//            for (int i = 0; i < jRoutes.length(); i++) {
//                jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
//                List path = new ArrayList<>();
//                /** Traversing all legs */
//                for (int j = 0; j < jLegs.length(); j++) {
//                    jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
//
//                    /** Traversing all steps */
//                    for (int k = 0; k < jSteps.length(); k++) {
//                        String polyline = "";
//                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");
//                        List<LatLng> list = decodePoly(polyline);
//
//                        /** Traversing all points */
//                        for (int l = 0; l < list.size(); l++) {
//                            HashMap<String, String> hm = new HashMap<>();
//                            hm.put("lat", Double.toString((list.get(l)).latitude));
//                            hm.put("lng", Double.toString((list.get(l)).longitude));
//                            path.add(hm);
//                        }
//                    }
//                    routes.add(path);
//                }
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//        }
//        return routes;
//    }


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