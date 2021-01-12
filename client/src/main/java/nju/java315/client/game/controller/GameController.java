package nju.java315.client.game.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class GameController {
    private static GameController instance;

    private HashMap<String, MonsterController> controllers = new HashMap<>();
    private LinkedList<LinkedHashMap<String,Object>> enemies;

    private GameController() { }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public MonsterController getController(String key) {
        return controllers.get(key);
    }

    public void init() {
        this.initControllers();
        
    }

    private void initControllers(){
        MonsterController monsterController;
        
    }

}
