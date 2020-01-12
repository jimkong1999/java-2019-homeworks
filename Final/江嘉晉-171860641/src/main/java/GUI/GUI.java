package tk.jimkong.project.GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tk.jimkong.project.Creature.*;
import tk.jimkong.project.Field.*;
import tk.jimkong.project.Lock.MyLock;
import tk.jimkong.project.Logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GUI extends Application {
    public final static int HEIGHT = 550;
    public final static int WIDTH = 1100;
    private BattleField battleField = new BattleField();
    private Group root = new Group();
    private Scene theScene = new Scene(root);
    private Canvas canvas = new Canvas(WIDTH, HEIGHT);
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    public final Image background = new Image("file:./image/fightBackground.jpg");
    private ExecutorService execute = null;
    private Stage stage;

    @Override
    public void start(Stage mainStage) throws Exception {
        mainStage.setTitle("JavaProject");

        mainStage.setScene(theScene);

        root.getChildren().add(canvas);

        stage = mainStage;
//        File dir = new File("./image/fightBackground.jpg");
//        System.out.println(dir.getCanonicalPath());
//        final Image background = new Image("./image/fightBackground.jpg");

        battleField.randomPosCalaBros();
        battleField.changShe();
        battleField.heYi();
        setKeyEvent();
        gc.setFill(Color.RED);
        showArray();

        mainStage.show();
    }

    private void showArray() {
        gc.clearRect(0,0, WIDTH, HEIGHT);
        gc.drawImage(background,0,0);
        for (int i=0;i<11;i++)
            for (int j=0;j<22;j++) {
                if (battleField.guys[i][j] != null)
                    if (battleField.guys[i][j].isAlive()) {
                        gc.setFill(Color.RED);
                        gc.fillText(battleField.guys[i][j].getName(), 25 + j * 50, 25 + i * 50);
                    } else {
                        gc.setFill(Color.WHITE);
                        gc.fillText(battleField.guys[i][j].getName() + "卒", 25 + j * 50, 25 + i * 50);
                    }
            }
    }

    private String mapInfoToStr() {
        StringBuilder ret = new StringBuilder();
        for (int i=0;i<11;i++) {
            for (int j=0;j<22;j++) {
                if (battleField.guys[i][j] == null)
                    ret.append("-");
                else {
                    String name = battleField.guys[i][j].getName();
                    if (!battleField.guys[i][j].isAlive()) {
                        name += "卒";
                    }
                    ret.append(name);
                }
            }
        }
        return ret.toString();
    }

    private void startFight() throws Exception {
        execute = Executors.newSingleThreadExecutor();
        execute.execute(new Runnable() {
            public void run() {
                // 串行運行
//                while (true) {
//                    try {
////                        for (int i = 0; i < 11; i++) {
////                            for (int j = 0; j < 22; j++) {
////                                if (battleField.guys[i][j] != null) {
////                                    battleField.guys[i][j].run();
////                                }
////                            }
////                        }
//                        battleField.run();
//                        showArray();
//                        if (battleField.isFinish())
//                            break;
//                        Thread.sleep(500);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                int count = 0;

                Logger logger = new Logger();
                logger.createLog();

                ExecutorService exec = battleField.openThreadPool();
                while (!exec.isTerminated()) {
                    MyLock.guiLock.lock();
                    try {
                        Thread.sleep(100);
                        if (MyLock.guiState == 1) {
                            if (count >= 200)
                                MyLock.stop = 1;
                            logger.writeLog(mapInfoToStr());
                            showArray();
                            MyLock.guiState = 0;
                            count++;
                            System.out.println(count);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        MyLock.guiLock.unlock();
                    }
                }
                logger.closeLog();
                System.out.println("Logger finish");
            }
        });
    }

    private void readLog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Logger File");
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Log File", "*.log"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        final File logFile = fileChooser.showOpenDialog(stage);
        if (logFile == null) return;
        else {
            execute = Executors.newSingleThreadExecutor();

            execute.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStreamReader reader = new InputStreamReader(new FileInputStream(logFile));
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String line = "";
                        line = bufferedReader.readLine();
                        int no = 0;
                        while (line != null) {
//                            System.out.println(line);
                            battleField.logStrToMap(line);
                            showArray();
                            Thread.sleep(100);
                            line = bufferedReader.readLine();
                        }
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            execute.shutdown();
        }
    }

    private void setKeyEvent() {
        theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (!(execute == null || execute.isTerminated()))
                    return;
                int queueMode = 0;
                try {
                    switch (event.getCode()) {
                        case DIGIT1:
                        case DIGIT2:
                        case DIGIT3:
                        case DIGIT4:
                        case DIGIT5:
                        case DIGIT6:
                        case DIGIT7:
                            queueMode = Integer.valueOf(event.getCode().getName());
                            break;
                        case SPACE:
                            // run
                            startFight();
                            break;
                        case L:
                            // read log file
                            readLog();
                            break;
                        default:
                            break;
                    }

                    switch (queueMode) {
                        case 1:
                            battleField.heYi();
                            break;
                        case 2:
                            battleField.yanHang();
                            break;
                        case 3:
                            battleField.chongE();
                            break;
                        case 4:
                            battleField.yuLin();
                            break;
                        case 5:
                            battleField.fangYuan();
                            break;
                        case 6:
                            battleField.yanYue();
                            break;
                        case 7:
                            battleField.fengShi();
                            break;
                        default:
                            break;
                    }
                    if (queueMode >= 1 && queueMode <= 7) {
                        showArray();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
