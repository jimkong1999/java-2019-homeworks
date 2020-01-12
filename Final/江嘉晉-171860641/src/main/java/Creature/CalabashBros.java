package tk.jimkong.project.Creature;

import tk.jimkong.project.Field.BattleField;
import tk.jimkong.project.Field.MyPosition;
import tk.jimkong.project.Lock.MyLock;

import javax.swing.text.Position;
import java.util.Random;

public class CalabashBros implements ICreature {
    private String name;
    private final int LEVEL;
    private final String COLOR;
    private boolean alive;

    public CalabashBros(String aName, String aCOLOR, int aLEVEL){
        name = aName;
        LEVEL = aLEVEL;
        COLOR = aCOLOR;
        alive = true;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void dead() {
        alive = false;
    }

    public int getLevel(){
        return LEVEL;
    }

    public String getColor(){
        return COLOR;
    }

    public boolean Win(ICreature enemy) {
        Random rand = new Random();
        int winRate = rand.nextInt(10);
        if (winRate > 1)
            return true;
        return false;
    }

    public void action() {
        if (!isAlive()) return;

        MyPosition position = BattleField.getPosition(this);
        final int x = position.x;
        final int y = position.y;
        MyPosition enemyPos = new MyPosition(x, y+1);
//        System.out.println(name + "x: " + position.x + ",y: " + position.y);
        // 前方有無敵人
        if (BattleField.isEnemy(this, enemyPos)) {
            if (Win(BattleField.guys[enemyPos.x][enemyPos.y])) {
                BattleField.guys[enemyPos.x][enemyPos.y].dead();
            } else {
                dead();
            }
            BattleField.swapPosition(position, enemyPos);
            return;
        }
        // 中位以下的人向下打，以上的人向上打
        if (x > 5) {
            enemyPos = new MyPosition(x+1, y);
        } else {
            enemyPos = new MyPosition(x-1, y);
        }
        // 上(下)向有無敵人
        if (BattleField.isEnemy(this, enemyPos)) {
            if (Win(BattleField.guys[enemyPos.x][enemyPos.y])) {
                BattleField.guys[enemyPos.x][enemyPos.y].dead();
            } else {
                dead();
            }
            BattleField.swapPosition(position, enemyPos);
            return;
        }
        // 能否向前走
        MyPosition dest = new MyPosition(x, y+1);
        if (BattleField.guys[dest.x][dest.y] == null && y < 12) {
            BattleField.move(position, dest);
            return;
        }

        // 能否向上(下)走
        if (x > 5) {
            dest = new MyPosition(x+1, y);
        } else {
            dest = new MyPosition(x-1, y);
        }
        if (x>1 && x<9 && BattleField.guys[dest.x][dest.y] == null) {
            BattleField.move(position, dest);
//            System.out.println(x);
            return;
        }
    }

    public void run() {
        while (true) {
            MyLock.lock.lock();
            try {
                if (MyLock.stop == 1)
                    return;
                if (!isAlive())
                    return;
                while (true) {
                    MyLock.guiLock.lock();
                    try {
                        if (MyLock.guiState == 0) {
                            action();
                            MyLock.guiState = 1;
                            break;
                        } else {
                            Thread.sleep(50);
                        }
                    } catch (Exception e) {
                        throw e;
                    } finally {
                        MyLock.guiLock.unlock();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                MyLock.lock.unlock();
            }
        }
    }
}