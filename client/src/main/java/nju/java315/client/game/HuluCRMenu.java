package nju.java315.client.game;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Observable;
import java.util.Random;
import java.util.function.Supplier;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.AnimationBuilder;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.app.scene.FXGLDefaultMenu;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.logging.Logger;
import com.almasb.fxgl.particle.ParticleEmitter;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.particle.ParticleSystem;
import com.almasb.fxgl.texture.Texture;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HuluCRMenu extends FXGLMenu {

    class MenuBox extends VBox {
        private double layoutHeight;

        public double getLayoutHeight() {
            return 10.0 * getChildren().size();
        }

        public MenuBox(MenuButton... items) {
            for (MenuButton item : items) {
                add(item);
            }
        }

        private void add(MenuButton item) {
            item.setParent(this);
            getChildren().addAll(item);
        }
    }

    class MenuButton extends Pane {
        private MenuBox parent = null;
        private MenuContent cachedContent = null;

        private Polygon p = new Polygon(0.0, 0.0, 220.0, 0.0, 250.0, 35.0, 0.0, 35.0);
        Button btn = new Button();

        private boolean isAnimating = false;

        public MenuButton(String stringKey) {
            btn = FXGL.getUIFactoryService().newButton(FXGL.localizedStringProperty(stringKey));
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setStyle("-fx-background-color: transparent");

            p.setMouseTransparent(true);

            LinearGradient g = new LinearGradient(0.0, 1.0, 1.0, 0.2, true, CycleMethod.NO_CYCLE,
                    new Stop(0.6, Color.color(1.0, 0.8, 0.0, 0.34)), new Stop(0.85, Color.color(1.0, 0.8, 0.0, 0.74)),
                    new Stop(1.0, Color.WHITE));

            p.fillProperty().bind(Bindings.when(btn.pressedProperty()).then(Color.color(1.0, 0.8, 0.0, 0.75))
                    .otherwise(Color.color(1.0, 0.8, 0.0, 0.75)));

            p.setStroke(Color.color(0.1, 0.1, 0.1, 0.15));
            p.setEffect(new GaussianBlur());

            p.visibleProperty().bind(btn.hoverProperty());

            getChildren().addAll(btn, p);

            btn.focusedProperty().addListener((o1, o2, isFocused) -> {
                if (isFocused) {
                    boolean notExistAnimation = true;
                    for (Animation<?> animation : animations) {
                        if (animation.isAnimating()) {
                            notExistAnimation = false;
                            break;
                        }
                    }
                    boolean isOK = notExistAnimation && !isAnimating;
                    if (isOK) {
                        isAnimating = true;
                        new AnimationBuilder().onFinished(new Runnable() {
                            @Override
                            public void run() {
                                isAnimating = false;

                            }
                        }).bobbleDown(this).buildAndPlay();
                    }
                }
            });
        }

        public void setOnAction(EventHandler<ActionEvent> e) {
            btn.setOnAction(e);
        }

        public void setParent(MenuBox menu) {
            parent = menu;
        }

        public void setMenuContent(Supplier<MenuContent> contentSupplier, boolean isCached) {
            btn.addEventHandler(ActionEvent.ACTION, (event) -> {
                if (cachedContent == null || isCached)
                    cachedContent = contentSupplier.get();

                switchMenuContentTo(cachedContent);
            });
        }

        public void setChild(MenuBox menu) {
            MenuButton back = new MenuButton("menu.back");
            menu.getChildren().add(0, back);
            back.addEventHandler(ActionEvent.ACTION, (event) -> switchMenuTo(this.parent));
            btn.addEventHandler(ActionEvent.ACTION, (event) -> switchMenuTo(menu));
        }

    }

    private static Logger log = Logger.get("FXGL.HuluCRMenu");

    private ParticleSystem particleSystem = null;

    private ObjectProperty titleColor = null;
    private double t = 0.0;

    private Node menu;

    public HuluCRMenu(MenuType type) {
        super(type);
        if (getAppWidth() < 800 || getAppHeight() < 600) {
            log.warning("FXGLDefaultMenu is not designed for resolutions < 800x600");
        }

        if (getType() == MenuType.MAIN_MENU)
            menu = createMenuBodyMainMenu();
        else {
            menu = createMenuBodyGameMenu();
        }

        double menuX = 50.0;
        double menuY = getAppHeight() / 2.0 - ()menu.getLayoutHeight() / 2;

        getMenuRoot().setTranslateX(menuX);
        getMenuRoot().setTranslateY(menuY);

        getMenuContentRoot().setTranslateX(getAppWidth()-500.0);
        getMenuContentRoot().setTranslateY(menuY);

        Texture t = FXGL.texture("particles/smoke.png", 128.0, 128.0).brighter().brighter();

        ParticleEmitter emitter = ParticleEmitters.newFireEmitter();
        emitter.setBlendMode(BlendMode.SRC_OVER);
        emitter.setSourceImage(t.getImage());
        emitter.setSize(150.0, 220.0);
        emitter.setNumParticles(10);
        emitter.setEmissionRate(0.01);
        emitter.setVelocityFunction(i -> new Point2D(FXGL.random() * 2.5, -FXGL.random() * FXGL.random(80, 120)));
        emitter.setExpireFunction(i -> Duration.seconds(FXGL.random(4, 7)));
        emitter.setScaleFunction(i -> new Point2D(0.15, 0.10));
        emitter.setSpawnPointFunction(i -> new Point2D(FXGL.random(0.0, getAppWidth() - 200.0), 120.0));

        particleSystem.addParticleEmitter(emitter, 0.0, FXGL.getAppHeight());

        getContentRoot().getChildren().add(3, particleSystem.getPane());

        getMenuRoot().getChildren().addAll(menu);
        getMenuContentRoot().getChildren().add(null);
    }

    private MenuBox createMenuBodyGameMenu() {
        log.debug("createMenuBodyMainMenu()");

        MenuBox box = new MenuBox();
        EnumSet<MenuItem> enabledItems = FXGL.getSettings().getEnabledMenuItems();

        MenuButton itemNewGame = new MenuButton("menu.newGame");
        itemNewGame.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                fireNewGame();
            }
            
        });
        box.add(itemNewGame);

        MenuButton itemOptions = new MenuButton("menu.options");
        itemOptions.setChild(createOptionsMenu());
        box.add(itemOptions);


        MenuButton itemExit = new MenuButton("menu.exit");
        itemExit.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event) {
                fireExit();
            }
            
        });
        box.add(itemExit);

        return box;
    }


    private MenuBox createMenuBodyMainMenu() {
        return null;
    }

    private MenuBox createOptionsMenu() {
/*        log.debug("createOptionsMenu()");

        MenuButton itemGameplay = new MenuButton("menu.gameplay");
        itemGameplay.setMenuContent(Supplier { this.createContentGameplay() })

        MenuButton itemControls = MenuButton("menu.controls")
        itemControls.setMenuContent(Supplier { this.createContentControls() })

        MenuButton itemVideo = MenuButton("menu.video")
        itemVideo.setMenuContent(Supplier { this.createContentVideo() })
        MenuButton itemAudio = MenuButton("menu.audio")
        itemAudio.setMenuContent(Supplier { this.createContentAudio() })

        MenuButton btnRestore = MenuButton("menu.restore")
        btnRestore.setOnAction(EventHandler{ e ->
            FXGL.getDisplay().showConfirmationBox(FXGL.localize("menu.settingsRestore")) { yes ->
                if (yes!!) {
                    switchMenuContentTo(EMPTY)
                    //listener.restoreDefaultSettings()
                }
            }
        })

        return MenuBox(itemGameplay, itemControls, itemVideo, itemAudio, btnRestore)*/
        return null;
    }

    private ArrayList<Animation<?>> animations = new ArrayList<Animation<?>>();

    @Override
    public void onCreate() {
        animations.clear();

        MenuBox menuBox =  (MenuBox)getMenuRoot().getChildren().get(0) ;
        int index = 0;
        for(Node node:menuBox.getChildren()){
            node.setTranslateX(-250.0);
            Animation animation = new AnimationBuilder()
                                .delay(Duration.seconds(index * 0.07))
                                .interpolator(Interpolators.EXPONENTIAL.EASE_OUT())
                                .duration(Duration.seconds(0.66))
                                .translate(node)
                                .from(new Point2D(-250.0, 0.0))
                                .to(new Point2D(0.0, 0.0))
                                .build();
            animations.add(animation);
            animation.stop();
            animation.start();

            index++;
        }
        
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        switchMenuTo(menu);
        switchMenuContentTo(new Node(){});
        super.onDestroy();
    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);

        animations.forEach((animation) -> animation.onUpdate(tpf));
        double frequency = 1.7;
        t += tpf * frequency;
        if (particleSystem!=null)
            particleSystem.onUpdate(tpf);
        Color color = Color.color(1.0, 1.0, 1.0, FXGLMath.noise1D(t));
        if (titleColor!=null)
            titleColor.set(color); 
    }
    @Override
    protected Button createActionButton(String name, Runnable action) {
        return new Button(name);
    }

    @Override
    protected Button createActionButton(StringBinding name, Runnable action) {
        return new Button(name.get());
    }

    @Override
    protected Node createBackground(double width, double height) {
        Rectangle bg = new Rectangle(width, height, Color.rgb(10, 1, 1, (getType() == MenuType.GAME_MENU) ? 0.5
                : 1.0));
        return bg;
    }

    @Override
    protected Node createTitleView(String title) {
        titleColor = new SimpleObjectProperty(Color.WHITE);
        Text text = FXGL.getUIFactoryService().newText(title.substring(0, 1), 50.0);
        text.setFill(null);
        text.strokeProperty().bind(titleColor);
        text.setStrokeWidth(1.5);

        Text text2 = FXGL.getUIFactoryService().newText(title.substring(1, title.length), 50.0);
        text2.setFill(null);
        text2.setStroke((Paint)titleColor.getValue());
        text2.setStrokeWidth(1.5);
        
        double textWidth = text.getLayoutBounds().getWidth() + text2.getLayoutBounds().getWidth();

        Rectangle border = new Rectangle(textWidth + 30, 65.0, null);
        border.setStroke(Color.WHITE);
        border.setStrokeWidth(4.0);
        border.setArcWidth(25.0);
        border.setArcHeight(25.0);

        ParticleEmitter emitter = ParticleEmitters.newExplosionEmitter(50);
        Texture tt = FXGL.texture("particles/trace_horizontal.png", 64.0, 64.0);

        emitter.setBlendMode(BlendMode.ADD);
        emitter.setSourceImage(tt.getImage());
        emitter.setMaxEmissions(Integer.MAX_VALUE);
        emitter.setSize(18.0, 22.0);
        emitter.setNumParticles(2);
        emitter.setEmissionRate(0.2);
        emitter.setVelocityFunction((i) -> {
            if (i%2 == 0)
                return new Point2D(FXGL.random(-10.0,0.0),FXGL.random(0.0,0.0));
            else
                return new Point2D(FXGL.random(0.0,10.0),FXGL.random(0.0,0.0));
            
        });
        emitter.setExpireFunction((i) -> Duration.seconds(FXGL.random(4.0, 6.0)));
        emitter.setScaleFunction((i) -> new Point2D(-0.03, -0.03));
        emitter.setSpawnPointFunction(i -> new Point2D(FXGL.random(0.0, 0.0), FXGL.random(0.0, 0.0)));
        emitter.setAccelerationFunction(() -> new Point2D(FXGL.random(-1.0, 1.0), FXGL.random(0.0, 0.0)));

        HBox box = new HBox(text, text2);
        box.setAlignment(Pos.CENTER);

        StackPane titleRoot = new StackPane();
        titleRoot.getChildren().addAll(border, box);

        titleRoot.setTranslateX(getAppWidth() / 2.0 - (textWidth + 30) / 2);
        titleRoot.setTranslateY(50.0);

        particleSystem = new ParticleSystem();

        if (!FXGL.getSettings().isExperimentalNative())
            particleSystem.addParticleEmitter(emitter, getAppWidth() / 2.0 - 30, titleRoot.getTranslateY() + border.getHeight() - 16);

        return titleRoot;
    }

    @Override
    protected Node createVersionView(String version) {
        Text view = FXGL.getUIFactoryService().newText(version);
        view.setTranslateY(FXGL.getAppHeight() - 2.0);
        return view;
    }

    @Override
    protected Node createProfileView(String profileName) {
        Text view = FXGL.getUIFactoryService().newText(profileName);
        view.setTranslateY(FXGL.getAppHeight() - 2.0);
        view.setTranslateX(FXGL.getAppWidth() - view.getLayoutBounds().getWidth());
        return view;
    }
}
