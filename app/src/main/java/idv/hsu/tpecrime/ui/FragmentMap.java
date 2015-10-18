package idv.hsu.tpecrime.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;
import idv.hsu.tpecrime.data.Results;
import idv.hsu.tpecrime.event.Event_Map;
import idv.hsu.tpecrime.task.AsyncTask_getLatLng;

public class FragmentMap extends SupportMapFragment implements OnMapReadyCallback {
    private static final String TAG = FragmentMap.class.getSimpleName();
    private static final boolean D = true;

    private static final String PARAM_RID = "RID";
    private static final String PARAM_TYPE = "TYPE";
    private String rid;
    private int type;
    private List<Results> listData;
    private Map<String, LatLng> address;

    private IOnFragmentInteractionListener mListener;

    public static FragmentMap newInstance(String rid, int type) {
        FragmentMap fragment = new FragmentMap();
//        Bundle args = new Bundle();
//        args.putString(PARAM_RID, rid);
//        args.putInt(PARAM_TYPE, type);
//        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMap() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rid = getArguments().getString(PARAM_RID);
            type = getArguments().getInt(PARAM_TYPE);
            if (D) {
                Log.d(TAG, "RID = " + rid);
                Log.d(TAG, "TYPE = " + type);
            }
        }
        listData = new ArrayList<>();
        address= new HashMap<>();
    }

    @Override
    public void onResume() {
        super.onResume();
//        EventBus.getDefault().register(this);
        GoogleMap mMap = super.getMap();

        if (mListener != null) {
//            mListener.onFragmentInteraction(rid, type);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
//        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IOnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public void onEvent(Event_Map event) {
        if (event.getType() == type) {
            if (D) {
                Log.d(TAG, "onEvent! type = " + type);
            }
            listData = event.getData().getResult().getResults();
            Log.d(TAG, "onEvent! listData size : " + listData.size());

            new AsyncTask_getLatLng(getActivity(), listData).forceLoad();
            int size = listData.size();
            for (int x=0; x < size; x++) {
                Log.i(TAG, listData.get(x).getLocation());
//                MainActivity.getLatLongFromAddress(listData.get(x).getLocation());
            }
        }
    }
}
