package idv.hsu.tpecrime.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsTheft implements IResults{
    public ResultsTheft() {
    }

    private String _id;

    @JsonProperty(value = "編號")
    private int no;

    @JsonProperty(value = "案類")
    private String type;

    @JsonProperty(value = "發生(現)日期")
    private String date;

    @JsonProperty(value = "發生時段")
    private String time;

    @JsonProperty(value = "發生(現)地點")
    private String location;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
