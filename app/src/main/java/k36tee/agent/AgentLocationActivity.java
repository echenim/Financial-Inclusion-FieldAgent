package k36tee.agent;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import k36tee.agent.Pref.AppPref;
/**
 * Created by myron echenim  on 8/8/16.
 */
public class AgentLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIfGpsIsEnabled();
        startTracking();
    }



    @Override
    public void onConnected(Bundle bundle) {

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000); // milliseconds
        locationRequest.setFastestInterval(1000); // the fastest rate in milliseconds at which your app can handle location updates
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if ((location != null)) {
            if (location.getAccuracy() < 500.0f) {
                new AppPref().setFloatValue(this, AppPref.AGENT_LAT, (float) location.getLatitude());
                new AppPref().setFloatValue(this, AppPref.AGENT_LNG, (float) location.getLongitude());

            }

        }
    }


    private void startTracking() {

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            if (!googleApiClient.isConnected() || !googleApiClient.isConnecting()) {
                googleApiClient.connect();
            }
        }
    }


    private void checkIfGpsIsEnabled(){
        LocationManager lm  = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();


        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }






}

//public class AgentLocationActivity extends AppCompatActivity implements
//        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
//
//    protected static final String TAG = "location-updates-sample";
//
//    /**
//     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
//     */
//    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
//
//    /**
//     * The fastest rate for active location updates. Exact. Updates will never be more frequent
//     * than this value.
//     */
//    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
//            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
//
//    // Keys for storing activity state in the Bundle.
//    protected final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
//    protected final static String LOCATION_KEY = "location-key";
//    protected final static String LAST_UPDATED_TIME_STRING_KEY = "last-updated-time-string-key";
//
//    /**
//     * Provides the entry point to Google Play services.
//     */
//    protected GoogleApiClient mGoogleApiClient;
//
//    /**
//     * Stores parameters for requests to the FusedLocationProviderApi.
//     */
//    protected LocationRequest mLocationRequest;
//
//
//    /**
//     * Tracks the status of the location updates request. Value changes when the user presses the
//     * Start Updates and Stop Updates buttons.
//     */
//    protected Boolean mRequestingLocationUpdates;
//
//    /**
//     * Time when the location was updated represented as a String.
//     */
//    protected String mLastUpdateTime;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        mRequestingLocationUpdates = false;
//        mLastUpdateTime = "";
//
//        // Update values using data stored in the Bundle.
//        updateValuesFromBundle(savedInstanceState);
//
//        // Kick off the process of building a GoogleApiClient and requesting the LocationServices
//        // API.
//        buildGoogleApiClient();
//    }
//
//    /**
//     * Updates fields based on data stored in the bundle.
//     *
//     * @param savedInstanceState The activity state saved in the Bundle.
//     */
//    private void updateValuesFromBundle(Bundle savedInstanceState) {
//        Log.i(TAG, "Updating values from bundle");
//        if (savedInstanceState != null) {
//            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
//            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
//            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
//                mRequestingLocationUpdates = savedInstanceState.getBoolean(
//                        REQUESTING_LOCATION_UPDATES_KEY);
//            }
//
//            if (savedInstanceState.keySet().contains(LAST_UPDATED_TIME_STRING_KEY)) {
//                mLastUpdateTime = savedInstanceState.getString(LAST_UPDATED_TIME_STRING_KEY);
//            }
//        }
//    }
//
//    /**
//     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
//     * LocationServices API.
//     */
//    protected synchronized void buildGoogleApiClient() {
//        Log.i(TAG, "Building GoogleApiClient");
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
//        createLocationRequest();
//    }
//
//    /**
//     * Sets up the location request. Android has two location request settings:
//     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
//     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
//     * the AndroidManifest.xml.
//     * <p>
//     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
//     * interval (5 seconds), the Fused Location Provider API returns location updates that are
//     * accurate to within a few feet.
//     * <p>
//     * These settings are appropriate for mapping applications that show real-time location
//     * updates.
//     */
//    protected void createLocationRequest() {
//        mLocationRequest = new LocationRequest();
//
//        // Sets the desired interval for active location updates. This interval is
//        // inexact. You may not receive updates at all if no location sources are available, or
//        // you may receive them slower than requested. You may also receive updates faster than
//        // requested if other applications are requesting location at a faster interval.
//        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
//
//        // Sets the fastest rate for active location updates. This interval is exact, and your
//        // application will never receive updates faster than this value.
//        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
//
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }
//
//
//    /**
//     * Requests location updates from the FusedLocationApi.
//     */
//    protected void startLocationUpdates() {
//        // The final argument to {@code requestLocationUpdates()} is a LocationListener
//        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
//    }
//
//
//    /**
//     * Removes location updates from the FusedLocationApi.
//     */
//    protected void stopLocationUpdates() {
//        // It is a good practice to remove location requests when the activity is in a paused or
//        // stopped state. Doing so helps battery performance and is especially
//        // recommended in applications that request frequent location updates.
//
//        // The final argument to {@code requestLocationUpdates()} is a LocationListener
//        // (http://developer.android.com/reference/com/google/android/gms/location/LocationListener.html).
//        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        // Within {@code onPause()}, we pause location updates, but leave the
//        // connection to GoogleApiClient intact.  Here, we resume receiving
//        // location updates if the user has requested them.
//
//        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
//        if (mGoogleApiClient.isConnected()) {
//            stopLocationUpdates();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        mGoogleApiClient.disconnect();
//
//        super.onStop();
//    }
//
//    /**
//     * Runs when a GoogleApiClient object successfully connects.
//     */
//    @Override
//    public void onConnected(Bundle connectionHint) {
//        Log.i(TAG, "Connected to GoogleApiClient");
//
//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
//    }
//
//    /**
//     * Callback that fires when the location changes.
//     */
//    @Override
//    public void onLocationChanged(Location location) {
//        new AppPref().setFloatValue(this, AppPref.AGENT_LAT, (float) location.getLatitude());
//        new AppPref().setFloatValue(this, AppPref.AGENT_LNG, (float) location.getLongitude());
//    }
//
//
//    @Override
//    public void onConnectionSuspended(int cause) {
//        // The connection to Google Play services was lost for some reason. We call connect() to
//        // attempt to re-establish the connection.
//        Log.i(TAG, "Connection suspended");
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult result) {
//        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
//        // onConnectionFailed.
//        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
//    }
//
//
//    /**
//     * Stores activity data in the Bundle.
//     */
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
//        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
//        super.onSaveInstanceState(savedInstanceState);
//    }
//}