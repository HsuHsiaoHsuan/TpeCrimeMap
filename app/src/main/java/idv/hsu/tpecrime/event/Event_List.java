package idv.hsu.tpecrime.event;

import idv.hsu.tpecrime.data.Response;

public class Event_List {
    private Response response;
    private int type;

    public Event_List(Response res, int type) {
        this.response = res;
        this.type = type;
    }

    public Response getData() {
        return response;
    }

    public int getType() {
        return type;
    }
}
