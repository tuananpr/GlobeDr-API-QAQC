package util.excel.dataObject;

public class Cell {
    private int indexRow;
    private int indexCol;
    private Object value;
    private Boolean isFormula;

    public Cell(int indexRow, int indexCol,Object value, boolean isFormula){
        this.indexRow = indexRow;
        this.indexCol = indexCol;
        this.value = value;
        this.isFormula = isFormula;
    }


    public boolean isFormula() {
        return isFormula;
    }

    public void setFormula(boolean formula) {
        isFormula = formula;
    }

    public int getIndexRow() {
        return indexRow;
    }

    public void setIndexRow(int indexRow) {
        this.indexRow = indexRow;
    }

    public int getIndexCol() {
        return indexCol;
    }

    public void setIndexCol(int indexCol) {
        this.indexCol = indexCol;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
