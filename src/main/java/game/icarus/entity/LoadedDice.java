package game.icarus.entity;

import java.util.*;

public class LoadedDice extends Dice {
    public LoadedDice() {
        this(2);
    }

    public LoadedDice(int amount) {
        super(amount);
    }

    @Override
    public Map<String, Object> roll() {
        Map<String, Object> ans = new HashMap<>();
        int[] numbers = new int[getAmount()];
        for (int i = 0; i < getAmount(); i++) {
            numbers[i] = 6;
        }
        HashSet<Integer> l = new HashSet<>();
        for (int i = 1; i <= this.getRange(); i++) {
            l.add(i);
        }
        ans.put("raw", numbers);
        ans.put("result", l);
        return ans;
    }
}
