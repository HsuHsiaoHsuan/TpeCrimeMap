package idv.hsu.tpecrime.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import idv.hsu.tpecrime.R;
import idv.hsu.tpecrime.data.Results;
import idv.hsu.tpecrime.event.Event_List;

public class FragmentList extends ListFragment {
    private static final String TAG = FragmentList.class.getSimpleName();
    private static final boolean D = true;

    private static final String PARAM_RID = "RID";
    private static final String PARAM_TYPE = "TYPE";
    private String rid;
    private int type;
    private List<Results> listData;
    private Adapter_List mAdapter;

    private IOnFragmentInteractionListener mListener;

    public static FragmentList newInstance(String rid, int type) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putString(PARAM_RID, rid);
        args.putInt(PARAM_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rid = getArguments().getString(PARAM_RID);
            type = getArguments().getInt(PARAM_TYPE);
        }
        listData = new ArrayList<>();
        mAdapter = new Adapter_List(getActivity().getLayoutInflater(), listData);
        setListAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        if (mListener != null) {
            mListener.onFragmentInteraction(rid, type);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
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

    public void onEvent(Event_List event) {
        if (event.getType() == type) {
            if (D) {
                Log.d(TAG, "onEvent! type = " + type);
            }
            listData.clear();
            listData.addAll(event.getData().getResult().getResults());
            mAdapter.notifyDataSetChanged();
//            Log.d(TAG, "onEvent! listData size : " + listData.size());

//            new AsyncTask_getLatLng(getActivity(), listData).forceLoad();
//            int size = listData.size();
//            for (int x=0; x < size; x++) {
//                Log.i(TAG, listData.get(x).getLocation());
//                MainActivity.getLatLongFromAddress(listData.get(x).getLocation());
//            }
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.showMap(listData.get(position).getLocation());
    }
}
