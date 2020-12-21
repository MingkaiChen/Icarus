package game.icarus.entity;

import java.util.ArrayList;

public class DiceResult {
    private final int[] raw;
    private final ArrayList<Integer> result;
    private final boolean canTakeOff;
    private final boolean isLucky;

    public DiceResult(int[] raw, ArrayList<Integer> result, boolean canTakeOff, boolean isLucky) {
        this.raw = raw;
        this.result = result;
        this.canTakeOff = canTakeOff;
        this.isLucky = isLucky;
    }

    public ArrayList<Integer> getResult() {
        return result;
    }

    public int[] getRaw() {
        return raw;
    }

    public boolean canTakeOff() {
        return canTakeOff;
    }

    public boolean isLucky() {
        return isLucky;
    }
}
