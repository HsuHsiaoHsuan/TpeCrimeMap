package idv.hsu.tpecrime.data;


public class ResponseWomanChildInjured implements IResponse {
    public ResponseWomanChildInjured() {
    }

    private ResultWomanChildInjured result;

    public ResultWomanChildInjured getResult() {
        return result;
    }

    public void setResult(ResultWomanChildInjured result) {
        this.result = result;
    }
}
