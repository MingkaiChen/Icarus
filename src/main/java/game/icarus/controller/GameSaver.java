package game.icarus.controller;

import com.alibaba.fastjson.JSON;
import game.icarus.entity.Save;

import java.io.FileWriter;
import java.io.IOException;

public class GameSaver {
    public static boolean saveGame(Save s, String file) {
        String json = JSON.toJSONString(s);
        System.out.println(json);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}