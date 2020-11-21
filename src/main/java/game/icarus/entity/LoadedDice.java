package game.icarus.entity;

import java.util.ArrayList;

public class LoadedDice extends Dice{
    public LoadedDice() {
        this(2);
    }
    public LoadedDice(int amount) {
        super(amount);
    }
    @Override
    public ArrayList<Integer> roll() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 1; i <= this.getRange(); i++) {
            ans.add(i);
        }
        return ans;
    }
}
