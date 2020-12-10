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
        GameController[] a = new GameController[100000];
        for (int i = 0; i < 100000; i++) {
            a[i] = new GameController(new Setting());
        }
        Scanner s = new Scanner(System.in);
        s.nextLine();
    }

}
