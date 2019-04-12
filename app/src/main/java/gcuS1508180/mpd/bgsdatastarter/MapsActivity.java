/***
 * Michael Worms
 * S1508180
 * 4th Year Computing
 * MPD
 */

package gcuS1508180.mpd.bgsdatastarter;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle bundle = getIntent().getExtras();
        for(int i=0; i<bundle.size()/3; i++){
            String description = bundle.getString(i+ "description");
            Double lat = bundle.getDouble(i+ "lat");
            Double lng = bundle.getDouble(i+ "lng");

            LatLng lt = new LatLng(lat,lng);

            String[] separated = description.split(";");
            double magnitude = Double.parseDouble(separated[4].substring(13));
            String location = separated[1].substring(11);

            if(magnitude <1){
                mMap.addMarker(new MarkerOptions()
                        .position(lt)
                        .title(magnitude + "," + location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }else if(magnitude>=1 && magnitude<2){
                mMap.addMarker(new MarkerOptions()
                        .position(lt)
                        .title(magnitude + "," + location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            }else if (magnitude>=2){
                mMap.addMarker(new MarkerOptions()
                        .position(lt)
                        .title(magnitude + "," + location)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

        }

        LatLng uk = new LatLng(54.261124, -2.381588);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(uk));
        mMap.animateCamera( CameraUpdateFactory.zoomTo(4.0f));

    }
}
