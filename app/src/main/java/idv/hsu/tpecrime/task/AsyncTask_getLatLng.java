//package idv.hsu.tpecrime.task;
//
//import android.content.AsyncTaskLoader;
//import android.content.Context;
//import android.location.Address;
//import android.location.Geocoder;
//import android.util.Log;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//import idv.hsu.tpecrime.data.ResultsTheft;
//
//public class AsyncTask_getLatLng extends AsyncTaskLoader<Void> {
//    private static final String TAG = AsyncTask_getLatLng.class.getSimpleName();
//    private static final boolean D = true;
//
//    private List<ResultsTheft> data;
//
//    public AsyncTask_getLatLng(Context context, List<ResultsTheft> data) {
//        super(context);
//        this.data = data;
//    }
//
//    @Override
//    public Void loadInBackground() {
//
//        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//        for (int x=0; x < data.size(); x++ ) {
//            List<Address> addresses = null;
//            try {
//                addresses = geocoder.getFromLocationName(data.get(x).getLocation(), 1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            if (addresses.size() > 0) {
//                Address address = addresses.get(0);
//                double longitude = address.getLongitude();
//                double latitude = address.getLatitude();
//                Log.d(TAG, data.get(x).getLocation() + ", lat: " + latitude + " lng: " + longitude);
//            } else {
//                Log.d(TAG, data.get(x).getLocation() + ", 找不到");
//            }
//        }
//
//        return null;
//    }
//}
