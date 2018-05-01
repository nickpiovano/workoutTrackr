package com.example.piovano_nich.workouttrackr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.View;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity implements OnMapReadyCallback {
    private TabHost tabs;
    private WebView webView;
    ArrayAdapter<CharSequence> adapter;

    int check = 0;

    GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run);

        //Initialize TabHost and set it up, assign it to spec
        tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec;

        //TAB ONE
        //Initialize a TabSpec for tab1 and add it to the TabHost
        spec = tabs.newTabSpec("tag1");    //create new tab specification
        spec.setContent(R.id.tab1);    //add tab view content
        spec.setIndicator("Map");    //put text on tab
        tabs.addTab(spec);             //put tab in TabHost container

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //TAB TWO
        //Initialize a TabSpec for tab2 and add it to the TabHost
        spec = tabs.newTabSpec("tag2");        //create new tab specification
        spec.setContent(R.id.tab2);            //add view tab content
        spec.setIndicator("Web");
        tabs.addTab(spec);                    //put tab in TabHost container
        /* Initialize Web View and load google.com as default address (not required but figured it would be
        a good idea if the user wants to search for something besides the options provided in Tab 3) */
        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://google.com");
    }


    //TAB ONE
    //Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        float zoom = 13.0f;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.384587, -71.222966), zoom));

        LatLng danaCenter = new LatLng(42.384587, -71.222966);
        mMap.addMarker(new MarkerOptions().position(danaCenter).title("Dana Center").snippet("http://www.bentleyfalcons.com/facilities/dana_center"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(danaCenter));

        LatLng bostonUnderground = new LatLng(42.364951, -71.227137);
        mMap.addMarker(new MarkerOptions().position(bostonUnderground).title(" Boston Underground Strength Training").snippet("https://www.yelp.com/biz/boston-underground-strength-training-waltham"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bostonUnderground));

        LatLng walthamAthletic = new LatLng(42.385717, -71.234394);
        mMap.addMarker(new MarkerOptions().position(walthamAthletic).title("Waltham Athletic Club").snippet("https://www.yelp.com/biz/waltham-athletic-club-waltham"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(walthamAthletic));

        LatLng synergyPFT = new LatLng(42.367946, -71.229677);
        mMap.addMarker(new MarkerOptions().position(synergyPFT).title("Synergy PFT").snippet("https://www.yelp.com/biz/synergy-pft-waltham-2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(synergyPFT));

        //MarkerOnClickListener
        mMap.setOnMarkerClickListener(
                new GoogleMap.OnMarkerClickListener() {

                    public boolean onMarkerClick(Marker m) {
                        String title = m.getTitle();
                        String snip = m.getSnippet();
                        Toast.makeText(getBaseContext(), title, Toast.LENGTH_LONG).show();
                        webView.loadUrl(snip);
                        tabs.setCurrentTab(1);
                        return true;
                    }
                }
        );
    }
}