package com.example.nemus.osakaapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker[] markers = new Marker[65];

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
        OsakaData osakaData = new OsakaData();

        BitmapDescriptor free = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
        BitmapDescriptor value = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        BitmapDescriptor temp;

        for (Amuse a : osakaData.items) {
            if (a.cost.equals("무료")) temp = free;
            else temp = value;
            markers[a.id] = mMap.addMarker(new MarkerOptions().position(a.loc).title(a.id+"").snippet(a.name+"("+a.cost+")").icon(temp));
        }

        LatLng osaka = new LatLng(34.6885009, 135.4915961);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(osaka));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(osaka, 11f));

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},10);
        }else{
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                /*Uri uri = Uri.parse("http://www.osaka-info.jp/osp/kr/facility/facility.php?id="+marker.getTitle());
                Intent it  = new Intent(Intent.ACTION_VIEW,uri);*/
                Intent it = new Intent(MapsActivity.this, Info_viewer.class);
                it.putExtra("id",marker.getTitle());
                startActivity(it);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        if((grantResults[0]==PackageManager.PERMISSION_GRANTED)&&(grantResults[1]==PackageManager.PERMISSION_GRANTED)){
            mMap.setMyLocationEnabled(true);
        }else{
            Toast.makeText(this,"위치를 사용 할 수 없습니다.",Toast.LENGTH_SHORT).show();
        }
    }
}
