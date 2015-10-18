package idv.hsu.tpecrime.data;

import java.util.List;

public class ResultTheft implements IResult {
    public ResultTheft() {
    }

    private String offset;
    private int limit;
    private int count;
    private String sort;
    private List<ResultsTheft> results;

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public List getResults() {
        return results;
    }

    public void setResults(List<ResultsTheft> results) {
        this.results = results;
    }
}
