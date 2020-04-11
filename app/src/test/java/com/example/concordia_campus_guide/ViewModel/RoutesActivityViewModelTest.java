package com.example.concordia_campus_guide.ViewModel;

import com.example.concordia_campus_guide.view_models.RoutesActivityViewModel;
import com.example.concordia_campus_guide.ClassConstants;
import com.example.concordia_campus_guide.database.AppDatabase;
import com.example.concordia_campus_guide.database.daos.ShuttleDao;
import com.example.concordia_campus_guide.googleMapsServicesTools.googleMapsServicesModels.DirectionsResult;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtils;
import com.example.concordia_campus_guide.ModelTests.TestUtils.TestUtilsRoutes;
import com.example.concordia_campus_guide.models.Building;
import com.example.concordia_campus_guide.models.routes.Route;
import com.example.concordia_campus_guide.models.Shuttle;

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
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoutesActivityViewModelTest {
    private Shuttle shuttle1;
    private Shuttle shuttle2;
    private TestUtils testUtils;
    private TestUtilsRoutes testUtilsRoutes;

    @Mock
    AppDatabase mockAppDb;
    RoutesActivityViewModel mViewModel;

    @Mock
    ShuttleDao shuttleDao;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        testUtils = new TestUtils();
        testUtilsRoutes = new TestUtilsRoutes();
        mViewModel = new RoutesActivityViewModel(mockAppDb);
        mViewModel.setTo(testUtils.building2);
        mViewModel.setFrom(testUtils.building1);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH.mm");
        shuttle1 = testUtilsRoutes.getShuttle1();
        shuttle2 = testUtilsRoutes.getShuttle2();


        when(mockAppDb.shuttleDao()).thenReturn(shuttleDao);
        when(mockAppDb.shuttleDao().getAll()).thenReturn(Arrays.asList(shuttle1, shuttle2));
        when(mockAppDb.shuttleDao().getScheduleByCampusAndDayAndTime("SGW", "Friday", Double.parseDouble(time.format(calendar.getTime())))).thenReturn(Arrays.asList(shuttle1));
    }


    @Test
    public void getSetTo() {
        Building sampleBuilding = new Building("H");
        mViewModel.setTo(sampleBuilding);
        assertEquals(mViewModel.getTo(), sampleBuilding);
    }

    @Test
    public void getSetFrom() {
        Building sampleBuilding = new Building("H");
        mViewModel.setFrom(sampleBuilding);
        assertEquals(mViewModel.getFrom(), sampleBuilding);
    }

    @Test
    public void getFromAndToCampuses() {
        Building sampleBuildingFrom = new Building("H");
        sampleBuildingFrom.setCampus("SGW");
        mViewModel.setFrom(sampleBuildingFrom);
        Building sampleBuildingTo = new Building("AD");
        sampleBuildingTo.setCampus("LOY");
        mViewModel.setTo(sampleBuildingFrom);
        assertEquals("SGW,LOY", mViewModel.getFromAndToCampus(sampleBuildingFrom, sampleBuildingTo));
    }

    @Test
    public void adaptShuttleToRoutesTest() {
        List<Shuttle> shuttles = new ArrayList<>();
        shuttles.add(shuttle1);
        mViewModel.adaptShuttleToRoutes(shuttles);
        assertNotNull(mViewModel.getRouteOptions());
    }

    @Test
    public void getShuttlesByDayAndTimeTest() {
        assertEquals(0, mViewModel.getShuttlesByDayAndTime("SGW", "Sunday", 7.05).size());
    }

    @Test
    public void getSetTransportTypeTest() {
        mViewModel.setTransportType(ClassConstants.DRIVING);
        assertEquals(ClassConstants.DRIVING, mViewModel.getTransportType());
    }

    @Test
    public void getRouteOptions() {
        List<Route> routes = new ArrayList();
        Route route = new Route(null, "", "", "1 day 20 hours", "I-80 E", "driving");
        routes.add(route);

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
