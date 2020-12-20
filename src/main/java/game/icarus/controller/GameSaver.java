package game.icarus.controller;

import com.alibaba.fastjson.JSON;
import game.icarus.entity.Save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class GameSaver {

    public static Slot[] getSlots() {
        Slot[] slots = new Slot[3];
        for (int i = 0; i < 3; i++) {
            Save tmp;
            try {
                tmp = loadSave(String.format("Save%d.json", i+1));
            } catch (FileNotFoundException e) {
                slots[i] = new Slot();
                continue;
            }
            slots[i] = new Slot(tmp.getName(), tmp.getSaveTime());
        }
        return slots;
    }

    public static void saveGame(Save s, String file) {
        String json = JSON.toJSONString(s);
        System.out.println(json);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Save loadSave(String file) throws FileNotFoundException {
        File f = new File(file);
        Scanner scanner = new Scanner(f);
        StringBuilder json = new StringBuilder();
        while (scanner.hasNextLine()) {
            json.append(scanner.nextLine());
        }
        return JSON.parseObject(String.valueOf(json), Save.class);
    }

    public static GameController loadGame(String file) throws FileNotFoundException {
        return new GameController(loadSave(file));
    }

    public static class Slot {
        String name;
        String time;
        public Slot() {
            this.name = "New Save";
            this.time = "";
        }
        public Slot(String name, LocalDateTime time) {
            this.name = name;
            this.time = time.toString();
        }
    }
}