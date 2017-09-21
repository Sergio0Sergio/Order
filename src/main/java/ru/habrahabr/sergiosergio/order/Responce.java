package ru.habrahabr.sergiosergio.order;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responce {

    @SerializedName("query")
    @Expose
    private Query query;
    @SerializedName("rows")
    @Expose
    private List<Row> rows = null;

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

}
