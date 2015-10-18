package idv.hsu.tpecrime.ui;

public interface IOnFragmentInteractionListener {
    void onFragmentInteraction(String url, int type);
    void showMap(String address);
}
