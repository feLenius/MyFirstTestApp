package feLenius.myfirsttestapp.app;

import android.graphics.Color;
import android.location.Criteria;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    LocationManager locationManager;
    LocationListener mLocationListener;
    LatLng point1 = new LatLng(54.668628, 25.238040);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        if(locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        // Enabling MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);
        // Getting LocationManager object from System Service LOCATION_SERVICE

        BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher);
        GroundOverlayOptions groundOverlay = new GroundOverlayOptions()
                .image(image)
                .position(point1, 500f) //point1 - paveiksliuko centras, 500float - plotis metrais 500 metru (aukstis paskaiciuojamas automatiskai islaikant proporcija).
                .transparency(0.5f);
        mMap.addGroundOverlay(groundOverlay);

        LatLng[] triangleCoords = {
                new LatLng(25.774252, -80.190262),
                new LatLng(18.466465, -66.118292),
                new LatLng(32.321384, -64.75737),
                new LatLng(25.774252, -80.190262)
        };

        PolygonOptions bermudaTriangle = new PolygonOptions()
                .add(triangleCoords)
                .fillColor(0x7F00FF00)
                        //.strokeColor(0x7F00FF00)
                .strokeWidth(0);
        mMap.addPolygon(bermudaTriangle);
        Criteria c = new Criteria();

//        Location a = locationManager.getLastKnownLocation(locationManager.getBestProvider(c, true));
//        int lasa = 2;
//        int asda = lasa *2;
    }

    public boolean Contains(LatLng location, PolygonOptions polyLoc)
    {
        if (location==null)
            return false;
        int polygonSize = polyLoc.getPoints().size();
        List<LatLng> polygonPoints = polyLoc.getPoints();
        LatLng lastPoint = polygonPoints.get(polygonSize-1);
        boolean isInside = false;
        double x = location.longitude;
        for(int i = 0; i < polygonSize; i++)
        //for(LatLng point: polyLoc)
        {
            LatLng point = polygonPoints.get(i);
            double x1 = lastPoint.longitude;
            double x2 = point.longitude;
            double dx = x2 - x1;

            if (Math.abs(dx) > 180.0)
            {
                // we have, most likely, just jumped the dateline (could do further validation to this effect if needed).  normalise the numbers.
                if (x > 0)
                {
                    while (x1 < 0)
                        x1 += 360;
                    while (x2 < 0)
                        x2 += 360;
                }
                else
                {
                    while (x1 > 0)
                        x1 -= 360;
                    while (x2 > 0)
                        x2 -= 360;
                }
                dx = x2 - x1;
            }

            if ((x1 <= x && x2 > x) || (x1 >= x && x2 < x))
            {
                double grad = (point.latitude - lastPoint.latitude) / dx;
                double intersectAtLat = lastPoint.latitude + ((x - x1) * grad);

                if (intersectAtLat > location.latitude)
                    isInside = !isInside;
            }
            lastPoint = point;
        }

        return isInside;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
