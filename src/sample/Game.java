package sample;

public class Game{
    static int boardX = 10;
    static int boardY = 10;
    private Cell[][] cells = new Cell[boardX][boardY];
    private String cellInformation = "";

    public void loadCells(){
        //fill with dead cells first
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                cells[i][j] = new Cell(false);
            }
        }
        //create some living cells
        cells[0][0] = new Cell(true);
        cells[1][0] = new Cell(true);
        cells[0][1] = new Cell(true);
        cells[1][1] = new Cell(true);
        cells[2][2] = new Cell(true);
        cells[3][2] = new Cell(true);
        cells[4][3] = new Cell(true);
        cells[3][4] = new Cell(true);
    }

    public Cell[][] getCells(){
        return cells;
    }

    //updates the neighbours
    public void update(){
        for (int i = 0; i < boardX; i++) {
            for (int j = 0; j < boardY; j++) {
                int livingNeighbours = 0;
                int errors = 0;
                try {
                    if (cells[i-1][j-1]!=null && cells[i-1][j-1].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i-0][j-1]!=null && cells[i-0][j-1].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i+1][j-1]!=null && cells[i+1][j-1].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i-1][j-0]!=null && cells[i-1][j-0].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i-1][j+1]!=null && cells[i-1][j+1].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i+1][j+1]!=null && cells[i+1][j+1].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i+1][j-0]!=null && cells[i+1][j-0].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                try {
                    if (cells[i-0][j+1]!=null && cells[i-0][j+1].isAlive()){
                        livingNeighbours++;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {errors++;}
                cells[i][j].setLivingNeighbours(livingNeighbours);
                System.out.println("cell "+i+j+" has "+livingNeighbours+" neighbours."+" errors: "+errors);
                cellInformation+="cell "+i+j+" has "+livingNeighbours+" neighbours."+" errors: "+errors+"\n";
            }
        }
    }

    public String getCellInformation(){
        return cellInformation;
    }
}
