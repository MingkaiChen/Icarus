package game.icarus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import game.icarus.entity.Dice;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

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
        //tt t = new tt();
       // System.out.println(JSON.toJSONString(t));
        Dice d = new Dice(2);
        int[] a = new int[12];
        for (int i = 0; i < 1000000; i++) {
            Map<String, Object> b = d.roll();
            for (int j : (HashSet<Integer>)b.get("result")) {
                a[j-1] ++;
            }
        }
        for (int i = 0; i < 12; i++) {
            System.out.printf("%d: %d |", i+1, a[i]);
        }
    }

}
