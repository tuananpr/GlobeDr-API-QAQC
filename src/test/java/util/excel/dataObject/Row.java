package util.excel.dataObject;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private int indexRow;
    private List<Cell> cells = new ArrayList<>();

    public Row(int indexRow) {
        this.indexRow = indexRow;
    }

    public void addAllCellIn(Row row){
        for (Cell cell: row.getCells()) {
            cells.add(cell);
        }
    }


    public void addCell(int indexCol, Object value, boolean isFormula){
        cells.add(new Cell(this.indexRow, indexCol, value, isFormula));
    }

    public int getIndexRow() {
        return indexRow;
    }

    public void setIndexRow(int indexRow) {
        this.indexRow = indexRow;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }


}
