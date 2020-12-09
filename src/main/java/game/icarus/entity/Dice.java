package game.icarus.entity;

import java.util.*;
import java.util.stream.Collectors;

public class Dice {
    private final int amount;
    private Random random;

    private HashSet<Integer> getTwoNumbersAvailableResult(int ta, int tb) {
        HashSet<Integer> ans = new HashSet<>();
        double tmp;
        int a = Math.max(ta, tb);
        int b = Math.min(ta, tb);
        ans.add(a + b);
        ans.add(a - b);
        ans.add(b - a);
        ans.add(a * b);
        if(b != 0) {
            tmp = (double) a / (double) b;
            if (tmp == Math.floor(tmp)) ans.add(a / b);
        }
        return ans;
    }

    private HashSet<Integer> getAllAvailableResult(int[] numbers) {
        HashSet<Integer> ans = new HashSet<>();
        if (amount >= 4) {
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
        return (HashSet<Integer>) ans.stream().filter(i -> (0<i && i<= getRange())).collect(Collectors.toSet());
    }

    public Dice() {
        this(2);
    }

    public Dice(int amount) {
        this.random = new Random();
        this.amount = amount;
    }

    public Map<String, Object> roll() {
        int[] numbers = new int[amount];
        Map<String, Object> ans = new HashMap<>();

        for (int i = 0; i < amount; i++) {
            numbers[i] = random.nextInt(6) + 1;
        }
        ans.put("raw", numbers);
        ans.put("result", getAllAvailableResult(numbers));
        return ans;
    }

    public int rollOnce() {
        return random.nextInt(6) + 1;
    }

    public int getAmount() {
        return this.amount;
    }

    public int getRange() {
        return this.amount * 6;
    }
}
