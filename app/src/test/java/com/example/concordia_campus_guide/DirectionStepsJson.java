//package com.example.concordia_campus_guide;
//
//import android.util.Log;
//
//import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
//import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataParser;
//import com.example.concordia_campus_guide.Helper.RoutesHelpers.DirectionsApiDataRetrieval;
//import com.example.concordia_campus_guide.Models.WalkingPoint;
//import com.example.concordia_campus_guide.Models.WalkingPoints;
//import com.google.gson.FieldNamingPolicy;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.StringWriter;
//import java.io.Writer;
//import java.util.List;
//
//public class DirectionStepsJson {
//
//    public DirectionsResult readingJson() {
//        String json;
//        DirectionsResult directionsResult = null;
//        InputStream is = getResources().openRawResource(R.raw.json_file);
//        Writer writer = new StringWriter();
//        char[] buffer = new char[1024];
//        try {
//            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            int n;
//            while ((n = reader.read(buffer)) != -1) {
//                writer.write(buffer, 0, n);
//            }
//        } finally {
//            is.close();
//        }
//        return directionsResult;
//    }
////    String json = "{\n" +
////            "  \"walkingPoints\": [\n" +
////            "    {\n" +
////            "      \"id\": 1,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49729154,\n" +
////            "        \"longitude\": -73.57876237\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"ELEVATOR\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        4,\n" +
////            "        34,\n" +
////            "        35\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 4,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49739588,\n" +
////            "        \"longitude\": -73.5787094\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        1,\n" +
////            "        5\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 5,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49733972,\n" +
////            "        \"longitude\": -73.57858803\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        4,\n" +
////            "        6,\n" +
////            "        8\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 6,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49724054,\n" +
////            "        \"longitude\": -73.57858669\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"CLASSROOM\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        5,\n" +
////            "        8\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 8,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49716322,\n" +
////            "        \"longitude\": -73.5787617\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        5,\n" +
////            "        6,\n" +
////            "        34,\n" +
////            "        17\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 17,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49711387,\n" +
////            "        \"longitude\": -73.57880227\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        8\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 34,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49725065,\n" +
////            "        \"longitude\": -73.57890118\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-9\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        1,\n" +
////            "        8\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 35,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49729154,\n" +
////            "        \"longitude\": -73.57876237\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"ELEVATOR\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        1,\n" +
////            "        36\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 36,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49731222,\n" +
////            "        \"longitude\": -73.5787902\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        35,\n" +
////            "        38\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 38,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.497398,\n" +
////            "        \"longitude\": -73.57871544\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        36,\n" +
////            "        40,\n" +
////            "        41,\n" +
////            "        42,\n" +
////            "        45,\n" +
////            "        47,\n" +
////            "        50\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 40,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49742244,\n" +
////            "        \"longitude\": -73.57861251\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"CLASSROOM\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        50,\n" +
////            "        38\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 41,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49745393,\n" +
////            "        \"longitude\": -73.57867721\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"CLASSROOM\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        38\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 42,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49748425,\n" +
////            "        \"longitude\": -73.57873723\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"CLASSROOM\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        38,\n" +
////            "        45\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 45,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.49749224,\n" +
////            "        \"longitude\": -73.57890788\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        38,\n" +
////            "        42,\n" +
////            "        47\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 47,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.4974255,\n" +
////            "        \"longitude\": -73.57888643\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"CLASSROOM\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        38,\n" +
////            "        45\n" +
////            "      ]\n" +
////            "    },\n" +
////            "    {\n" +
////            "      \"id\": 50,\n" +
////            "      \"coordinate\": {\n" +
////            "        \"latitude\": 45.4973369,\n" +
////            "        \"longitude\": -73.57858904\n" +
////            "      },\n" +
////            "      \"floorCode\": \"H-8\",\n" +
////            "      \"pointType\": \"NONE\",\n" +
////            "      \"connectedPointsId\": [\n" +
////            "        38,\n" +
////            "        40\n" +
////            "      ]\n" +
////            "    }\n" +
////            "  ]\n" +
////            "}";
//}
