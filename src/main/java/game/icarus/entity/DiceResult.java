package game.icarus.entity;

import java.util.ArrayList;

public class DiceResult {

    private int[] raw;
    private ArrayList<Integer> result;
    private boolean canTakeOff;
    private boolean isLucky;

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

    public boolean isCanTakeOff() {
        return canTakeOff;
    }

    public void setRaw(int[] raw) {
        this.raw = raw;
    }

    public void setResult(ArrayList<Integer> result) {
        this.result = result;
    }

    public void setCanTakeOff(boolean canTakeOff) {
        this.canTakeOff = canTakeOff;
    }

    public void setLucky(boolean lucky) {
        isLucky = lucky;
    }
}
