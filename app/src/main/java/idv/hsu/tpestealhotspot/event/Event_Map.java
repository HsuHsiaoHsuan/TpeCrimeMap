package idv.hsu.tpestealhotspot.event;

import idv.hsu.tpestealhotspot.data.Response;

public class Event_Map {
    private Response response;
    private int type;

    public Event_Map(Response res, int type) {
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
