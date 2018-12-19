package matth.dungeon;

public class LevelTile {

    private int type;
    private boolean visited;

    LevelTile(int type) {
        this.type = type;
        visited = false;
    }

    public String test() {
        return Integer.toString(type);
    }

    public int getType() {
        return type;
    }

    public void setType(int num) {
        type = num;
    }
}