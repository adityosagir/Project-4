package com.example.asagir.spotacart;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_LOCATION = 1;
    private Location myLocation;
    private double mCurrentLatitude;
    private double mCurrentLongitude;
    private ArrayList<FoodCart> mCartList;
    private HashMap<String, FoodCart> mMarkerMap;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mCartList = new ArrayList<>();
        mMarkerMap = new HashMap<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        // Create client because we use google play services for the gms location service
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Reference to /items node
        Firebase.setAndroidContext(this);

//        Location myLocation = locationManager.getLastKnownLocation(provider);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        } else {
            // permission has been granted, continue as usual

            mGoogleApiClient.connect();
            Log.d("hello", "DID NOT WORK");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add("List").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("List")) {
            item.setTitle("Map");
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                    .replace(R.id.fragmentHolder, new ListFragment())
                    .addToBackStack("List").commit();
        } else if (item.getTitle().equals("Map")) {
            item.setTitle("List");
            getFragmentManager().popBackStackImmediate("List", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        return true;
    }


    public void setupFragment() {

        myLocation =
                LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (myLocation == null) {
            LocationRequest locationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                    .setInterval(5 * 1000)         // 5 seconds, in milliseconds
                    .setFastestInterval(1000);
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mGoogleApiClient, locationRequest, (com.google.android.gms.location.LocationListener) this);
        } else {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setUpMap();

        Firebase mRef = new Firebase("https://brilliant-fire-1347.firebaseio.com/");

        mCartList.clear();

        // Get data from Firebase by current location
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                final FoodCart foodCart = dataSnapshot.getValue(FoodCart.class);

                mCartList.add(foodCart);
                final int position = mCartList.size() - 1;

                double cartLatitude = foodCart.getLatitude();
                double cartLongitude = foodCart.getLongitude();

                // Get carts relative to current location
//                if ((cartLatitude < mCurrentLatitude + .005 && cartLatitude > mCurrentLatitude - .005)
//                        && (cartLongitude < mCurrentLongitude + .005 && cartLongitude > mCurrentLongitude - .005)) {

                LatLng latLng = new LatLng(cartLatitude, cartLongitude);

                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(foodCart.getName()).snippet(foodCart.getAddress()).icon(BitmapDescriptorFactory.fromResource(R.drawable.cart)));

                mMarkerMap.put(marker.getId(), foodCart);

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        FoodCart foodCart1 = mMarkerMap.get(marker.getId());

                        CartDetails cartDetail = new CartDetails();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("FoodCart", foodCart1);

                        cartDetail.setArguments(bundle);

                        getFragmentManager()
                                .beginTransaction()
                                .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                                .replace(R.id.fragmentHolder, cartDetail)
                                .addToBackStack("List").commit();
                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void setUpMap() {
        // Enable MyLocation Layer of Google Map
        mMap.setMyLocationEnabled(true);

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // set map type
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Get latitude of the current location
        mCurrentLatitude = myLocation.getLatitude();

        // Get longitude of the current location
        mCurrentLongitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng latLng = new LatLng(mCurrentLatitude, mCurrentLongitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(mMap.getMaxZoomLevel() * 0.8f)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // We can now safely use the API we requested access to

                mGoogleApiClient.connect();

            } else {
                // Permission was denied or request was cancelled
            }
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        setupFragment();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mGoogleApiClient.disconnect();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fragmentManager = getFragmentManager();

        if (!fragmentManager.popBackStackImmediate()) {
            super.onBackPressed();
        }
    }
}
