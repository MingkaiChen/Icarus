package game.icarus.entity;

import java.util.*;

public class Dice {
    private final int amount;

    private LinkedHashSet<Integer> getTwoNumbersAvailableResult(int ta, int tb) {
        LinkedHashSet<Integer> ans = new LinkedHashSet<>();
        double tmp;
        int a = Math.max(ta, tb);
        int b = Math.min(ta, tb);
        ans.add(a + b);
        if (a != b) ans.add(a - b);
        if ((a * b) <= 12) ans.add(a * b);
        tmp = (double) a / (double) b;
        if (tmp == Math.floor(tmp)) ans.add(a / b);
        return ans;
    }

    private LinkedHashSet<Integer> getAllAvailableResult(int[] numbers) {
        LinkedHashSet<Integer> ans = new LinkedHashSet<>();
        if (amount > 4) { //全排列的和
            for (int i = 1; i <= (1 << amount); i++) {
                int sum = 0;
                for (int j = 0; j < amount; j++) {
                    if ((1 << j & i) != 0) {
                        sum += numbers[j];
                    }
                }
                ans.add(sum);
            }
        } else {
            switch (amount) {
                case 1:
                    ans.add(numbers[0]);
                    break;
                case 2:
                    ans = getTwoNumbersAvailableResult(numbers[0], numbers[1]);
                    break;
                case 3:
                    for (int i : getTwoNumbersAvailableResult(numbers[0], numbers[1])) {
                        ans.addAll(getTwoNumbersAvailableResult(i, numbers[2]));
                    }
                    break;
            }
        }
        return ans;
    }

    public Dice() {
        this(2);
    }

    public Dice(int amount) {
        this.amount = amount;
    }

    public Map<String, Object> roll() {
        int[] numbers = new int[amount];
        Map<String, Object> ans = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            numbers[i] = random.nextInt(6) + 1;
        }
        ans.put("raw", numbers);
        ans.put("result", getAllAvailableResult(numbers));
        return ans;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getRange() {
        return this.amount * 6;
    }
}
