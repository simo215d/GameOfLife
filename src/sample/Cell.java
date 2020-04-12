package sample;

public class Cell {
    private int livingNeighbours;
    private boolean alive;

    public Cell(boolean alive){
        this.alive=alive;
    }

    public void update(){
        if (!isAlive() && getLivingNeighbours()==3){
            setAlive(true);
            return;
        }
        if (isAlive() && getLivingNeighbours()==2 ||  isAlive() && getLivingNeighbours()==3){
            return;
        }
        setAlive(false);
    }

    public int getLivingNeighbours() {
        return livingNeighbours;
    }

    public void setLivingNeighbours(int livingNeighbours) {
        this.livingNeighbours = livingNeighbours;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
