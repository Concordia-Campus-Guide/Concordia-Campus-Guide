package com.example.concordia_campus_guide.ViewModel;

import com.example.concordia_campus_guide.Activities.RoutesActivityViewModel;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.Database.AppDatabase;
import com.example.concordia_campus_guide.Database.Daos.ShuttleDao;
import com.example.concordia_campus_guide.GoogleMapsServicesTools.GoogleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.ModelTests.TestUtils;
import com.example.concordia_campus_guide.Models.Building;
import com.example.concordia_campus_guide.Models.Coordinates;
import com.example.concordia_campus_guide.Models.Place;
import com.example.concordia_campus_guide.Models.RoomModel;
import com.example.concordia_campus_guide.Models.Routes.Route;
import com.example.concordia_campus_guide.Models.Shuttle;
import com.example.concordia_campus_guide.Models.Time;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoutesActivityViewModelTest {
    private Shuttle shuttle1;
    private Shuttle shuttle2;
    private TestUtils testUtils;

    @Mock
    AppDatabase mockAppDb;
    RoutesActivityViewModel mViewModel;

    @Mock
    ShuttleDao shuttleDao;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        testUtils = new TestUtils();
        mViewModel = new RoutesActivityViewModel(mockAppDb);
        mViewModel.setTo(testUtils.building2);
        mViewModel.setFrom(testUtils.building1);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH.mm");
        shuttle1 = testUtils.getShuttle1();
        shuttle2 = testUtils.getShuttle2();


        when(mockAppDb.shuttleDao()).thenReturn(shuttleDao);
        when(mockAppDb.shuttleDao().getAll()).thenReturn(Arrays.asList(shuttle1,shuttle2));
        when(mockAppDb.shuttleDao().getScheduleByCampusAndDayAndTime("SGW","Friday",Double.parseDouble(time.format(calendar.getTime())))).thenReturn(Arrays.asList(shuttle1));
    }

    //
    @Test
    public void getSetTo(){
        Building sampleBuilding = new Building("H");
        mViewModel.setTo(sampleBuilding);
        assertEquals(mViewModel.getTo(), sampleBuilding);
    }

    @Test
    public void getSetFrom(){
        Building sampleBuilding = new Building("H");
        mViewModel.setFrom(sampleBuilding);
        assertEquals(mViewModel.getFrom(), sampleBuilding);
    }

//    @Test
//    public void getSetShuttles(){
//        mViewModel.setShuttles();
//        List<Shuttle> expectedListOfShuttles = Arrays.asList(shuttle1);
//        List<Shuttle> returnedListOfShuttles = mViewModel.getShuttles();
//        assertEquals(expectedListOfShuttles.get(0).getShuttleId(),returnedListOfShuttles.get(0).getShuttleId());
//    }

    @Test
    public void getShuttleDisplayTextTest(){
        assertEquals("SGW  >   LOY, \t leaves at: 8:2\n",mViewModel.getShuttleDisplayText(Arrays.asList(shuttle1)));
    }

    @Test
    public void getSetTransportTypeTest() {
        mViewModel.setTransportType(ClassConstants.DRIVING);
        assertEquals(ClassConstants.DRIVING, mViewModel.getTransportType());
    }

    @Test
    public void getSetRouteOptions() {
        List<Route> routes = new ArrayList();
        routes.add(testUtils.route1);

        mViewModel.setRouteOptions(routes);
        assertEquals(routes, mViewModel.getRouteOptions());
    }

    @Test
    public void getSetDirectionsResult() {
        DirectionsResult result = new DirectionsResult();
        mViewModel.setDirectionsResult(result);

        assertEquals(result, mViewModel.getDirectionsResult());
    }
}
