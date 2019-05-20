package pl.test;

public class Population {

    private int weekOfActive;
    private int amountOfClient;

    public Population(int weekOfActive, int amountOfClient) {
        this.weekOfActive = weekOfActive;
        this.amountOfClient = amountOfClient;
    }

    public int getWeekOfActive() {
        return weekOfActive;
    }

    public void setWeekOfActive(int weekOfActive) {
        this.weekOfActive = weekOfActive;
    }

    public int getAmountOfClient() {
        return amountOfClient;
    }

    public void setAmountOfClient(int amountOfClient) {
        this.amountOfClient = amountOfClient;
    }
}
