package idv.hsu.tpecrime.data;


public class ResponseTheft implements IResponse {
    public ResponseTheft() {
    }

    private ResultTheft result;

    public ResultTheft getResult() {
        return result;
    }

    public void setResult(ResultTheft result) {
        this.result = result;
    }
}
