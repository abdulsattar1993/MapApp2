package a.mapapp;

import android.app.Dialog;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(googleServiceAvailable()){

            Toast.makeText(getApplicationContext(), "Perfect", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_maps);
            Button btn=(Button)findViewById(R.id.button3);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        geoLocate(view);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else
        {

            //No map layout
        }
    }
    private void initMap(){

        MapFragment mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);
    }
    public boolean googleServiceAvailable(){
GoogleApiAvailability api=GoogleApiAvailability.getInstance();
        int isAvailable=api.isGooglePlayServicesAvailable(this);
        if(isAvailable== ConnectionResult.SUCCESS){
            return true;
        }
        else if(api.isUserResolvableError(isAvailable)){
            Dialog dialog=api.getErrorDialog(this,isAvailable,0);
        }
        else {
            Toast.makeText(getApplicationContext(), "cant connect to play services", Toast.LENGTH_LONG).show();
        }

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap; Toast.makeText(getApplicationContext(), "map getting ready", Toast.LENGTH_LONG).show();
        LatLng latLng = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(latLng).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.setOnMarkerDragListener((GoogleMap.OnMarkerDragListener) this);
        mMap.setOnMapLongClickListener((GoogleMap.OnMapLongClickListener) this);

    }

    private void goToLocation(double lat, double lon) {
        LatLng ll=new LatLng(lat,lon);
        CameraUpdate update= CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);


    }
    private void goToLocation(double lat, double lon,float zoom) {
        LatLng ll=new LatLng(lat,lon);
        CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,zoom);
        mMap.moveCamera(update);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
     getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
switch(item.getItemId()){


}
return true;
    }
    public void geoLocate(View view) throws IOException {
        EditText ed=(EditText) findViewById(R.id.editText);
    String location=ed.getText().toString();
        Geocoder gc=new Geocoder(this);
        List<android.location.Address> list=gc.getFromLocationName(location,1);
 android.location.Address address=list.get(0);
        String locality=address.getLocality();
        Toast.makeText(this,locality,Toast.LENGTH_LONG).show();
        double lat=address.getLatitude();
        double lon=address.getLongitude();
        goToLocation(lat,lon,15);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    /*@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }*/
}
