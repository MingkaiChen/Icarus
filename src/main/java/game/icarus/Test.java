package game.icarus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import game.icarus.controller.GameController;
import game.icarus.entity.Dice;
import game.icarus.entity.Setting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

class tt {
    @JSONField(name = "aaaa")
    private int aaaa;
    public tt() {
        aaaa = 1;
    }

    public int getaaaa() {
        return aaaa;
    }

    public void setaaaa(int aaaa) {
        this.aaaa = aaaa;
    }
}

public class Test {
    public static void main(String[] args) {
        Dice d = new Dice(1);
        int[] r = new int[6];
        for (int i = 0; i < 6; i++) {
            r[i] = 0;
        }
        for (int i = 0; i < 10000000; i++) {
            for (int j : (HashSet<Integer>)d.roll().get("result")) {
                r[j-1] ++;
            }
        }
        for (int i = 0; i < 6; i++) {
            System.out.printf("%d: %f\n", i+1, r[i]/10000000.0);
        }
    }

}
