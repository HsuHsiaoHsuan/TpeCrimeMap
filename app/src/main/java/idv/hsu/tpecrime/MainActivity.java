package idv.hsu.tpecrime;

import android.content.Context;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import idv.hsu.tpecrime.conn.ConnControl;
import idv.hsu.tpecrime.data.CrimeTypeEnum;
import idv.hsu.tpecrime.data.IResponse;
import idv.hsu.tpecrime.data.ResponseTheft;
import idv.hsu.tpecrime.data.ResponseWomanChildInjured;
import idv.hsu.tpecrime.event.Event_Address;
import idv.hsu.tpecrime.event.Event_List;
import idv.hsu.tpecrime.event.Event_Refresh;
import idv.hsu.tpecrime.ui.FragmentList;
import idv.hsu.tpecrime.ui.IOnFragmentInteractionListener;

public class MainActivity extends AppCompatActivity implements IOnFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean D = false;

    private static final String RID_HOUSE =
            "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=876a83ac-c27a-457f-8d00-25751373a93c";
    private static final String RID_CAR =
            "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=999daf96-ef99-42e9-94ac-9095f77203b8";
    private static final String RID_BIKE =
            "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=47c6fdfa-8849-4f73-badd-689d577ccb7e";
    private static final String RID_WOMEN_CHILD =
            "http://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=efe5c923-fa09-4d55-896e-877c553f04e0";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static RequestQueue queue;
    final JsonFactory factory = new JsonFactory();
    final ObjectMapper mapper = new ObjectMapper();

    private ViewPager mViewPager;
    private View mapFragment;
    private FloatingActionButton fab;
    private SupportMapFragment map;

    private boolean idDeviceOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int navBarHeight = getNavigationBarHeight();
        findViewById(R.id.container).setPadding(0, 0, 0, navBarHeight);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        queue = ConnControl.getInstance(this).getRequestQueue();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment = findViewById(R.id.map);
        mapFragment.setVisibility(View.INVISIBLE);
        mapFragment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.controller_slide_init));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapFragment.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.controller_slide_out));
                mapFragment.setVisibility(View.INVISIBLE);
                map.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        googleMap.clear();
                    }
                });
                fab.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            EventBus.getDefault().post(new Event_Refresh());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(String url, final int type) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url.toString(),
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (D) {
                            Log.d(TAG, "onResponse: " + response);
                        }
                        JsonParser parser = null;
                        try {
                            parser = factory.createParser(response.toString());
//                            ResponseTheft data =
//                                    mapper.readValue(parser, ResponseTheft.class);
                            IResponse data = null;
                            switch (type) {
                                case 0:
                                case 1:
                                case 2:
                                    data = mapper.readValue(parser, ResponseTheft.class);
                                    break;
                                case 3:
                                    data = mapper.readValue(parser, ResponseWomanChildInjured.class);
                                    break;
                            }
                            EventBus.getDefault().post(new Event_List(data, type));
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        request.setRetryPolicy(
                new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    @Override
    public void showMap(final String location) {
        if (location != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocationName(location, 1);
                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);

                            EventBus.getDefault().post(new Event_Address(location,
                                    new LatLng(address.getLatitude(), address.getLongitude())));
                        } else {
                            EventBus.getDefault().post(new Event_Address(null, null));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int[] titles = {
                R.string.house,
                R.string.car,
                R.string.bike,
                R.string.woman_child_injured
        };

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FragmentList.newInstance(RID_HOUSE, CrimeTypeEnum.TYPE_HOUSE.getValue());
                case 1:
                    return FragmentList.newInstance(RID_CAR, CrimeTypeEnum.TYPE_CAR.getValue());
                case 2:
                    return FragmentList.newInstance(RID_BIKE, CrimeTypeEnum.TYPE_BIKE.getValue());
                case 3:
                    return FragmentList.newInstance(RID_WOMEN_CHILD, CrimeTypeEnum.TYPE_WOMAN_CHILD_INJURED.getValue());
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titles[position]);
        }
    }

    private int getNavigationBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public void onEventMainThread(final Event_Address event) {
        if (event.getLatLng() == null) {
            Toast.makeText(this, R.string.cant_on_map, Toast.LENGTH_SHORT).show();
        } else {
            mapFragment.startAnimation(AnimationUtils.loadAnimation(this, R.anim.controller_slide_in));
            findViewById(R.id.map).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.VISIBLE);
            map.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.getUiSettings().setMapToolbarEnabled(false);
                    googleMap.addMarker(
                            new MarkerOptions()
                                    .position(event.getLatLng())
                                    .title(event.getLocation()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(event.getLatLng(), 17));
                }
            });
        }
    }
}
