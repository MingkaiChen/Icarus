package game.icarus.model;

import java.util.ArrayList;
import java.util.Random;

public class Dice {
    private int amount;
    private int[] numbers;

    private ArrayList<Integer> getAllAvailableResult() {
        throw new UnsupportedOperationException("这算法我还没想出来");
        //ArrayList<Integer> ans = new ArrayList<>();
        //return ans;
    }

    public Dice() {
        this(2);
    }
    public Dice(int amount) {
        this.amount = amount;
        numbers = new int[this.amount];
    }
    public ArrayList<Integer> roll() {
        Random random = new Random();
        for (int i = 0; i < this.amount; i++) {
            this.numbers[i] = random.nextInt(6) + 1;
        }
        return this.getAllAvailableResult();
    }

    public int getRange() {
        return this.amount * 6;
    }
}
