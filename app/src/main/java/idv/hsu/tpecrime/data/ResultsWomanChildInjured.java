package idv.hsu.tpecrime.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsWomanChildInjured implements IResults {
    public ResultsWomanChildInjured() {
    }

    private String _id;

    @JsonProperty(value = "District")
    private String district;

    @JsonProperty(value = "Location")
    private String location;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String getDate() {
        return "";
    }

    @Override
    public String getTime() {
        return "";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
