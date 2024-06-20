package util.excel.dataObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sheet {
    private String name;
    private Integer index;
    private List<Row> rows = new ArrayList<>();

    public Sheet(String name){
        this.name = name;
    }

    public Sheet(Integer index){
        this.index = index;
    }

    public void addRow(List<Row> list){
        this.rows.addAll(list);
    }

    public void addRow(Row r){
        this.rows.addAll(Arrays.asList(r));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }



}
