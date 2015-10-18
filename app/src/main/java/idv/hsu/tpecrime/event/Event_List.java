package idv.hsu.tpecrime.event;

import idv.hsu.tpecrime.data.IResponse;

public class Event_List {
    private IResponse response;
    private int type;

    public Event_List(IResponse res, int type) {
        this.response = res;
        this.type = type;
    }

    public IResponse getData() {
        return response;
    }

    public int getType() {
        return type;
    }
}
