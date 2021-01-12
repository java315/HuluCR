package nju.java315.client.game;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLDefaultMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.SimpleGameMenu;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityWorldListener;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.net.Client;
import com.almasb.fxgl.pathfinding.Cell;
import com.almasb.fxgl.pathfinding.CellState;
import com.almasb.fxgl.pathfinding.astar.AStarGrid;
import com.almasb.fxgl.ui.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import nju.java315.client.game.components.IdentityComponent;
import nju.java315.client.game.components.PlayerComponent;
import nju.java315.client.game.components.ai.UnmovableMonsterAIComponent;
import nju.java315.client.game.event.EntryResultEvent;
import nju.java315.client.game.event.PutEvent;
import nju.java315.client.game.event.ReadyEvent;
import nju.java315.client.game.type.MonsterType;
import nju.java315.client.game.type.CursorEventType;
import nju.java315.client.game.type.EntityType;

import static com.almasb.fxgl.dsl.FXGL.*;
import static nju.java315.client.game.Config.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

interface randomMonster {
    MonsterType getRandomMonster();
}

public class HuluCRApp extends GameApplication {
    static private final Logger LOGGER = LoggerFactory.getLogger(HuluCRApp.class);

    // 闭包，获得随机的卡片
    private static Random rand = new Random();
    static randomMonster randomMonster = () -> MonsterType.class.getEnumConstants()[rand
            .nextInt(MonsterType.class.getEnumConstants().length)];

    List<Entity> cards = new ArrayList<>();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(WIDTH);
        settings.setHeight(HEIGHT);
        settings.setTitle("葫芦娃战争");
        settings.setVersion("0.0.1");
        settings.setProfilingEnabled(false);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        // settings.setEnabledMenuItems(EnumSet.of(MenuItem.EXTRA));

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public FXGLMenu newMainMenu() {

                return new FXGLDefaultMenu(MenuType.MAIN_MENU);
                // return new HuluCRMenu(MenuType.MAIN_MENU);
            }
        });
        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        UserAction putCard = new UserAction("put") {
            @Override
            protected void onActionBegin() {
                Point2D cursorPoint = getInput().getMousePositionUI();
                System.out.println("put:" + cursorPoint.toString());
                dealWithCursorBegin(cursorPoint);
            }

            @Override
            protected void onAction() {
                Point2D cursorPoint = getInput().getMousePositionUI();
                dealWithCursor(cursorPoint);
            }

            @Override
            protected void onActionEnd() {
                Point2D cursorPoint = getInput().getMousePositionUI();
                dealWithCursorEnd(cursorPoint);
            }
        };
        input.addAction(putCard, MouseButton.PRIMARY);
    }

    // private Entity player;
    // private PlayerComponent playerComponent;

    // 初始化事件监听和处理
    @Override
    protected void onPreInit() {
        // 事件相关
        onEvent(PutEvent.SELF_PUT, this::onSelfPut);
        onEvent(PutEvent.ENEMY_PUT, this::onEnemyPut);
        onEvent(ReadyEvent.SELF_READY, this::onSelfReady);
        onEvent(ReadyEvent.ENEMY_READY, this::onEnemyReady);
        onEvent(EntryResultEvent.SELF_ENTRY_RESULT, this::onSelfEntryResult);
        onEvent(EntryResultEvent.ENEMY_ENTRY_RESULT, this::onEnemyEntryResult);
    }

    // 建立映射表
    @Override
    protected void initGameVars(Map<String, Object> vars) {

        vars.put("upTowerLives", CHILD_TOWER_LIVES);
        vars.put("downTowerLives", CHILD_TOWER_LIVES);
        vars.put("mainTowerLives", MAIN_TOWER_LIVES);
        vars.put("waterMeter", WATER_INIT_COUNT);
        vars.put("roomID", -1);
        vars.put("playerID", -1);
        vars.put("enemyID", -1);
        // vars.put("playerIsReady", false);
        // vars.put("enemyIsReady", false);
        vars.put("min", 0);
        vars.put("sec", 0);

        vars.put("enemy_main_tower_alive",true);
        vars.put("enemy_down_tower_alive",true);
        vars.put("enemy_up_tower_alive",true);

        vars.put("self_main_tower_alive",true);
        vars.put("self_down_tower_alive",true);
        vars.put("self_up_tower_alive",true);
        
    }

    // 初始化游戏元素
    private HuluCRClientManager clientManager;

    @Override
    protected void initGame() {
        // 初始化网络
        clientManager = new HuluCRClientManager(SERVER_IP,SERVER_PORT);
        //添加工厂
        getGameWorld().addEntityFactory(new HuluCRFactory());

        // 初始化grid
        initBlock();
        initGrid();

        spawn("ReadyButton", new SpawnData(Config.READY_BUTTON_X, Config.READY_BUTTON_Y));
        spawnTowers();
        spawn("Background");

        //spawnPlayer();
    }

    // 初始化物理环境
    @Override
    protected void initPhysics() {

    }

    // 初始化ui
    @Override
    protected void initUI() {

    }

    private boolean runningFirstTime = true;
    private boolean gameLoading = false;
    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
        if(runningFirstTime) {
            getDialogService().showInputBox("请输入房间号", answer -> {
                System.out.println("room id: " + answer);
                // send room id to server
                clientManager.enterRoom(Integer.parseInt(answer));

                runOnce(this::stopLoading, Duration.seconds(2.0));

                runningFirstTime = false;
                gameLoading = true;
            });
        }
        else if (gameLoading) {
            //System.out.println("loading");
        }
        else{

        }

    }

    //确定正在进行哪种操作
    private Entity temp_monster;
    // 鼠标操作类型
    CursorEventType cursorEventType = CursorEventType.UNKNOW;
    // 以下变量用于选卡操作
    int currentCard = -1;
    Point2D lastCursorPoint = null;

    public void dealWithCursorBegin(Point2D cursorPoint){
        int x = (int)cursorPoint.getX(), y = (int)cursorPoint.getY();
        if( x >= Config.READY_BUTTON_X && x <= (Config.READY_BUTTON_X + 120)
                && y >= Config.READY_BUTTON_Y && y <= (Config.READY_BUTTON_Y + 60)){
            cursorEventType = CursorEventType.PLAYER_READY;
        }
        else if(x >= Config.CARD_X && x <= Config.CARD_X + 73){
            for(int i = 0;i < 4;i++){
                if(y >= Config.CARD_Y[i] && y <= Config.CARD_Y[i] + 73){
                    currentCard = i;
                    lastCursorPoint = cursorPoint;
                    cursorEventType = CursorEventType.PUT_CARD;
                    Entity card = cards.get(currentCard);
                    MonsterType type = (MonsterType)card.getType();
                    //temp_monster = spawn(type.getName(), cursorPoint);
                    temp_monster = spawn("FakeMonster", new SpawnData(cursorPoint).put("type", type));

                    card.setVisible(false);
                    break;
                }
            }
        }
    }

    public void dealWithCursor(Point2D cursorPoint){
        switch(cursorEventType){
            case PLAYER_READY:
                break;
            case PUT_CARD:
                if(currentCard != -1){
                    //Entity card = cards.get(currentCard);
                    double dx = (int)(cursorPoint.getX() - lastCursorPoint.getX());
                    double dy = (int)(cursorPoint.getY() - lastCursorPoint.getY());
                    temp_monster.translate(dx, dy);
                    //card.translate(dx, dy);

                    lastCursorPoint = cursorPoint;
                }
                break;
            case UNKNOW:
                break;
            default:
                break;
        }
    }

    public void dealWithCursorEnd(Point2D cursorPoint){
        int x = (int)cursorPoint.getX(), y = (int)cursorPoint.getY();
        switch(cursorEventType){
            case PLAYER_READY:
                if( x >= Config.READY_BUTTON_X && x <= (Config.READY_BUTTON_X + 120)
                        && y >= Config.READY_BUTTON_Y && y <= (Config.READY_BUTTON_Y + 60)){
                    clientManager.ready();
                }
                break;
            case PUT_CARD:
                if (currentCard == -1){

                }
                else if (!isSuitableForPutCard(cursorPoint)){
                    Entity card = cards.get(currentCard);
                    temp_monster.removeFromWorld();
                    card.setVisible(true);
                }
                else{
                    MonsterType type = (MonsterType) cards.get(currentCard).getType();

                    if((int)getd("waterMeter") >= type.getCost()){
                        inc("waterMeter", (double)(-1 * type.getCost()));

                        Entity card = cards.remove(currentCard);

                        MonsterType temp = randomMonster.getRandomMonster();
                        cards.add(
                            spawn("Card", new SpawnData(Config.CARD_X, Config.CARD_Y[4]).put("type", temp))
                        );

                        // 产生放置事件
                        card.removeFromWorld();
                        temp_monster.removeFromWorld();
                        System.out.println(type.getName());

                        // 通知服务器
                        clientManager.putMonster(type.getName(), cursorPoint);
                    }
                    else {
                        Entity card = cards.get(currentCard);
                        temp_monster.removeFromWorld();
                        card.setVisible(true);
                    }
                }
                runOnce(this::updateCardPosition, Duration.millis(200));
                break;
            case UNKNOW:
                break;
            default:
                break;
        }
        lastCursorPoint = null;
        currentCard = -1;
        cursorEventType = CursorEventType.UNKNOW;
    }

    public void updateCardPosition(){
        for(int i = 0;i < 4;i++){
            animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_IN())
                .duration(Duration.millis(200))
                .translate(cards.get(i))
                .from(cards.get(i).getPosition())
                .to(new Point2D(Config.CARD_X, Config.CARD_Y[i]))
                .buildAndPlay();
        }
    }

    public boolean isSuitableForPutCard(Point2D cursorPoint){
        int x = (int)cursorPoint.getX(), y = (int)cursorPoint.getY();
        if(x >= 235 && x <= 545 && y >= 50 && y <= 550)
            return true;
        else if(x >= 210 && x<= 235 && y >= 215 && y <= 385)
            return true;
        return false;
    }

    // private void spawnPlayer() {
    //     player = spawn("Player", 15, 50);
    //     // playerComponent = player.getComponent(PlayerComponent.class);
    // }

    private void stopLoading(){
        gameLoading = false;
    }

    private void onSelfPut(PutEvent event){
        Point2D putPoint = new Point2D(event.getX(), event.getY());

        spawn("LargeHulu", new SpawnData(putPoint).put("flag", IdentityComponent.SELF_FLAG));

        //spawn("Fireball", putPoint);
    }

    private void onEnemyPut(PutEvent event) {
        float newX = Config.LEFT_BOUND_X + Config.RIGHT_BOUND_X - event.getX();
        Point2D putPoint = new Point2D(newX, event.getY());

        spawn("LargeHulu", new SpawnData(putPoint).put("flag", IdentityComponent.ENEMY_FLAG));
    }

    private boolean playerIsReady = false;
    private boolean enemyIsReady = false;

    private void onSelfReady(ReadyEvent event){
        playerIsReady = true;
        getGameWorld().getSingleton(EntityType.READY_BUTTON).removeFromWorld();
        spawn("ReadyTitle", new SpawnData(Config.SELF_READY_TITLE_X, Config.READY_TITLE_Y));
        if(enemyIsReady)
            startGameAnimation();

    }

    private void onEnemyReady(ReadyEvent event){
        enemyIsReady = true;
        spawn("ReadyTitle", new SpawnData(Config.ENEMY_READY_TITLE_X, Config.READY_TITLE_Y));
        if(playerIsReady)
            startGameAnimation();
    }

    @Setter
    @Getter
    @AllArgsConstructor
    static class MyBoolean{
        Boolean b;
    }

    private void startGameAnimation(){
        MyBoolean initFlag = new MyBoolean(false);
        getGameWorld().getEntitiesByType(EntityType.READY_TITLE).forEach((entity)->{
            animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_IN())
                .duration(Duration.millis(500))
                .onFinished(()->{
                    entity.removeFromWorld();
                    if(!initFlag.getB()){
                        spawn("StartTitle", new SpawnData(Config.START_TITLE_X, Config.START_TITLE_Y));
                        startTitleAnimation();
                        initFlag.setB(true);
                    }

                })
                .translate(entity)
                .from(entity.getPosition())
                .to(new Point2D(Config.CENTER_READY_TITLE_X, Config.READY_TITLE_Y))
                .buildAndPlay();
        });
    }

    private void startTitleAnimation(){
        getGameWorld().getEntitiesByType(EntityType.START_TITLE).forEach((entity)->{
            animationBuilder()
                .interpolator(Interpolators.EXPONENTIAL.EASE_IN())
                .duration(Duration.millis(500))
                .onFinished(()->{
                    runOnce(()->{
                        getGameWorld().getSingleton(EntityType.START_TITLE).removeFromWorld();

                        initUIController();

                        //初始化卡片栏
                        initCards();

                        //圣水自动增加
                        initWaterTimer();

                        //计时器
                        initClockTimer();
                    }, Duration.millis(500));
                })
                .scale(entity)
                .from(new Point2D(0, 0))
                .to(new Point2D(1,1))
                .buildAndPlay();
        });
    }

    private void onSelfEntryResult(EntryResultEvent event){
        int roomID = event.getRoomID();
        int enemyID = event.getEnemyID();
        if(roomID == -1){
            getDialogService().showInputBox("房间已满，请输入新的房间号", answer -> {
                System.out.println("room id: " + answer);
                // send room id to server
                clientManager.enterRoom(Integer.parseInt(answer));
            });
            LOGGER.info("self enter room fault");
        }else{
            set("roomID", roomID);
            LOGGER.info("self enter room :{}", roomID);

            if(enemyID != -1){
                set("enemyID", enemyID);
                if(event.getEnemyIsReady())
                    getEventBus().fireEvent(new ReadyEvent(ReadyEvent.ENEMY_READY));
            }
        }
    }

    private void onEnemyEntryResult(EntryResultEvent event){
        int enemyID = event.getEnemyID();
        set("enemyID", enemyID);
        LOGGER.info("player {} enter room", enemyID);
    }

    // AStar
    private AStarGrid grid;

    public AStarGrid getGrid() {
        return grid;
    }

    private void initGrid() {
        grid = AStarGrid.fromWorld(getGameWorld(), HORIZONTAL_GRID_NUM, VERTICAL_GRID_NUM, CELL_WIDTH, CELL_HEIGHT, (type) -> {
            if (type == HuluCRType.BLOCK){
                return CellState.NOT_WALKABLE;
            }

            return CellState.WALKABLE;
        });
        set("grid", grid);
      
    }

    private void initBlock() {
        int HULUIMG_SIZE = 40;
        spawn("Block", new SpawnData(208,16).put("height", 32).put("width", 932-208));
        spawn("Block", new SpawnData(208,556-HULUIMG_SIZE).put("height", 32+HULUIMG_SIZE).put("width", 932-208));

        spawn("Block", new SpawnData(208,390).put("height", 554-390).put("width", 20));
        spawn("Block", new SpawnData(208,48).put("height", 554-390).put("width", 20));
        spawn("Block", new SpawnData(188,188).put("height", 411-188).put("width", 20));

        spawn("Block", new SpawnData(916-HULUIMG_SIZE,48).put("height", 554-390).put("width", 20+HULUIMG_SIZE));
        spawn("Block", new SpawnData(916-HULUIMG_SIZE,390).put("height", 554-390).put("width", 20+HULUIMG_SIZE));
        spawn("Block", new SpawnData(937-HULUIMG_SIZE,188).put("height", 411-188).put("width", 20+HULUIMG_SIZE));

        spawn("Block", new SpawnData(552-HULUIMG_SIZE,20 - HULUIMG_SIZE).put("height", 123-20).put("width", 27+HULUIMG_SIZE));
        spawn("Block", new SpawnData(552-HULUIMG_SIZE,480 - HULUIMG_SIZE).put("height", 123-20).put("width", 27+HULUIMG_SIZE));
        spawn("Block", new SpawnData(552-HULUIMG_SIZE,167 - HULUIMG_SIZE).put("height", 262).put("width", 27+HULUIMG_SIZE));
    }

    private HuluCRController uiController;

    // monster list
    private ArrayList<Entity> selfTowers = new ArrayList<>();
    private ArrayList<Entity> enemyTowers = new ArrayList<>();
    private void spawnTowers(){
        selfTowers.add(spawn("Tower", new SpawnData(SELF_MAIN_TOWER_POSITION).put("flag", IdentityComponent.SELF_FLAG)));
        selfTowers.add(spawn("Tower", new SpawnData(SELF_UP_TOWER_POSITION).put("flag", IdentityComponent.SELF_FLAG)));
        selfTowers.add(spawn("Tower", new SpawnData(SELF_DOWN_TOWER_POSITION).put("flag", IdentityComponent.SELF_FLAG)));

        enemyTowers.add(spawn("Tower", new SpawnData(ENEMY_MAIN_TOWER_POSITION).put("flag", IdentityComponent.ENEMY_FLAG)));
        enemyTowers.add(spawn("Tower", new SpawnData(ENEMY_UP_TOWER_POSITION).put("flag", IdentityComponent.ENEMY_FLAG)));
        enemyTowers.add(spawn("Tower", new SpawnData(ENEMY_DOWN_TOWER_POSITION).put("flag", IdentityComponent.ENEMY_FLAG)));

        String[] pos = {"main","up","down"};
        for(int i=0;i<3;++i) {
            selfTowers.get(i).getComponent(UnmovableMonsterAIComponent.class).setName("self_"+pos[i]+"_tower");
            enemyTowers.get(i).getComponent(UnmovableMonsterAIComponent.class).setName("enemy_"+pos[i]+"_tower");
        }
    }
    private void initClockTimer(){
        getGameTimer().runAtInterval(()->{
            inc("sec", 1);
            if(geti("sec")>=60){
                inc("min", 1);
                set("sec", 0);
            }
            String minValue, secValue;
            if(geti("min") < 10)
                minValue = "0" + String.valueOf(geti("min"));
            else
                minValue = String.valueOf(geti("min"));

            if(geti("sec") < 10)
                secValue = "0" + String.valueOf(geti("sec"));
            else
                secValue = String.valueOf(geti("sec"));

            uiController.getTimeLabel().setText(minValue + ":" + secValue);
        }, Duration.seconds(1));
    }

    private void initWaterTimer(){
        getGameTimer().runAtInterval(()->{
            if(getd("waterMeter") < WATER_MAX_COUNT){
                inc("waterMeter", 1 / WATER_UP_STEP);
                if(getd("waterMeter") > WATER_MAX_COUNT)
                    set("waterMeter", WATER_MAX_COUNT);
            }
        }, Duration.seconds(WATER_UP_TIME / WATER_UP_STEP));
    }

    private void initCards(){
        for(int i = 0;i < 4; i++){
            MonsterType temp = randomMonster.getRandomMonster();

            cards.add(
                spawn("Card", new SpawnData(Config.CARD_X, Config.CARD_Y[i]).put("type", temp))
            );
        }
    }

    private void initUIController(){
        uiController = new HuluCRController(getGameScene());

        UI ui = getAssetLoader().loadUI(Asset.FXML_MAIN_UI, uiController);

        uiController.getWaterMeter().currentValueProperty().bind(getdp("waterMeter"));

        getGameScene().addUI(ui);
    }
}
