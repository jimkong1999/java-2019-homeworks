package tk.jimkong.project.Field;

import javafx.geometry.Pos;
import tk.jimkong.project.Creature.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BattleField{
    private ICreature nullPos;
//    private Creature[][] goodGuys;
//    private Creature[][] badGuys;
    public static ICreature[][] guys;
    private static CalabashBros[] calabashBros;
    private static Grandpa grandpa;
    private static Scorpion scorpion;
    private static Servant[] servants;
    private static Snake snake;

    public BattleField(){
        nullPos = null;
//        goodGuys = new Creature[11][11];
//        badGuys = new Creature[11][11];
        guys = new ICreature[11][22];
        for(int i=0;i<11;i++){
            for(int j=0;j<22;j++){
//                goodGuys[i][j] = nullPos;
//                badGuys[i][j] = nullPos;
                guys[i][j] = nullPos;
            }
        }
        calabashBros = new CalabashBros[Constants.BROS_NUM];
        grandpa = new Grandpa("爺爺");
        scorpion = new Scorpion("蝎子");
        servants = new Servant[Constants.SERVANT_NUM];
        for(int i=0;i<Constants.SERVANT_NUM;i++){
            servants[i] = new Servant("嘍囉");
        }
        snake = new Snake("蛇精");

        CalabashBros first = new CalabashBros("老大", "紅色", 1);
        CalabashBros second = new CalabashBros("老二", "橙色", 2);
        CalabashBros third = new CalabashBros("老三", "黃色", 3);
        CalabashBros fourth = new CalabashBros("老四", "綠色", 4);
        CalabashBros fifth = new CalabashBros("老五", "青色", 5);
        CalabashBros sixth = new CalabashBros("老六", "藍色", 6);
        CalabashBros seventh = new CalabashBros("老七", "紫色", 7);
        CalabashBros[] bros = {first, second, third, fourth, fifth, sixth, seventh};
        calabashBros = bros;

    }
    public void randomPosCalaBros(){
        clearGoodGuys();
        Random rand = new Random();
        int x;
        int y;
        for(int i=0;i<Constants.BROS_NUM;i++){
            x = rand.nextInt(11);
            y = rand.nextInt(11);
            if(guys[x][y] == null){
                guys[x][y] = calabashBros[i];
            }
        }
    }
    private void grandpaPos(){
        Random rand = new Random();
//        int x = rand.nextInt(11);
        guys[9][8] = grandpa;
    }
    private void snakePos(){
        Random rand = new Random();
//        int x = rand.nextInt(11);
        guys[9][2 + 11] = snake;
    }
    public void changShe(){
        clearGoodGuys();
        grandpaPos();
        for(int i=2,j=0;i<=8;i++,j++){
            guys[i][8] = calabashBros[j];
        }
    }
    public void heYi(){
        clearBadGuys();
        snakePos();
        guys[2][0 + 11] = scorpion;
        int k=0;
        int i=3;
        int j=1;
        for(;i<=7;i++,j++,k++){
            guys[i][j + 11] = servants[k];
        }
        for(i=6;i>=2;i--,j++,k++){
            guys[i][j + 11] = servants[k];
        }
    }
    public void yanHang(){
        clearBadGuys();
        snakePos();
        guys[9][0 + 11] = scorpion;
        for(int i=9,j=1,k=0;i>0;i--,j++,k++){
            guys[i][j + 11] = servants[k];
        }
    }
    public void chongE(){
        clearBadGuys();
        snakePos();
        guys[1][3 + 11] = scorpion;
        for(int i=2,k=0;i<10;i+=2,k+=2){
            guys[i][2 + 11] = servants[k];
            guys[i+1][3 + 11] = servants[k+1];
        }
    }
    public void yuLin(){
        clearBadGuys();
        snakePos();
        guys[3][3 + 11] = scorpion;
        int k=0;
        int t=2;
        int j_s=2;
        int j;
        for(int r=4;r<7;r++){
            j=j_s;
            for(int i=0;i<t;i++,j+=2,k++){
                guys[r][j + 11] = servants[k];
            }
            j_s-=1;
            t++;
        }
        guys[7][3 + 11] = servants[k];
    }
    public void fangYuan(){
        clearBadGuys();
        snakePos();
        guys[3][3 + 11] = scorpion;
        int j_s = 2;
        int k=0;
        for(int r=4;r<7;r++,k+=2){
            guys[r][j_s + 11] = servants[k];
            guys[r][6-j_s + 11] = servants[k+1];
            j_s-=1;
        }
        j_s = 1;
        for(int r=7;r<10;r++,k+=2){
            guys[r][j_s + 11] = servants[k];
            guys[r][6-j_s + 11] = servants[k+1];
            j_s+=1;
        }
    }
    public void yanYue(){
        clearBadGuys();
        snakePos();
        guys[2][3 + 11] = servants[0];
        guys[2][4 + 11] = servants[1];
        guys[3][2 + 11] = servants[2];
        guys[3][3 + 11] = servants[3];
        guys[4][0 + 11] = servants[4];
        guys[4][1 + 11] = servants[5];
        guys[4][2 + 11] = servants[6];
        guys[5][0 + 11] = scorpion;
        guys[5][1 + 11] = servants[7];
        guys[5][2 + 11] = servants[8];
        guys[6][0 + 11] = servants[9];
        guys[6][1 + 11] = servants[10];
        guys[6][2 + 11] = servants[11];
        guys[7][2 + 11] = servants[12];
        guys[7][3 + 11] = servants[13];
        guys[8][3 + 11] = servants[14];
        guys[8][4 + 11] = servants[15];

    }
    public void fengShi(){
        clearBadGuys();
        snakePos();
        int k=0;
        for(int r=6,j=0;r>=4;r--,j++,k++){
            guys[r][j + 11] = servants[k];
        }
        guys[3][3 + 11] = scorpion;
        for(int r=4;r<=9;r++,k++){
            guys[r][3 + 11] = servants[k];
        }
        for(int r=4,j=4;r<=6;r++,k++,j++){
            guys[r][j + 11] = servants[k];
        }
    }

    public static boolean isEnemy(ICreature creature, MyPosition position) {
        if (guys[position.x][position.y] != null && guys[position.x][position.y].isAlive()) {
            if (creature instanceof CalabashBros || creature instanceof Grandpa) {
                if (guys[position.x][position.y] instanceof Servant ||
                        guys[position.x][position.y] instanceof Snake ||
                        guys[position.x][position.y] instanceof Scorpion)
                    return true;
            }
            else if (creature instanceof Servant || creature instanceof Scorpion || creature instanceof Snake) {
                if (guys[position.x][position.y] instanceof CalabashBros || guys[position.x][position.y] instanceof Grandpa)
                    return true;
            }
        }
        return false;
    }

    public static void swapPosition(MyPosition a, MyPosition b) {
        ICreature temp = guys[a.x][a.y];
        guys[a.x][a.y] = guys[b.x][b.y];
        guys[b.x][b.y] = temp;
    }

    public static void move(MyPosition source, MyPosition dest) {
        guys[dest.x][dest.y] = guys[source.x][source.y];
        guys[source.x][source.y] = null;
    }

    public static MyPosition getPosition(ICreature creature) {
        MyPosition res = new MyPosition(-1,-1);
        for (int i=0;i<11;i++) {
            for (int j=0;j<22;j++) {
                if (guys[i][j] != null && guys[i][j] == creature) {
                    res = new MyPosition(i,j);
                }
            }
        }
        return res;
    }

    public boolean isFinish() {
        for (int i=0;i<11;i++) {
            for (int j=0;j<22;j++) {
                if (guys[i][j] != null)
                    if (guys[i][j].isAlive())
                        return false;
            }
        }
        return true;
    }

    public void run() {
        for(CalabashBros cal: calabashBros) {
            cal.run();
        }
        grandpa.run();
        for(Servant ser: servants) {
            ser.run();
        }
        scorpion.run();
        snake.run();
    }

    public ExecutorService openThreadPool() {
        ArrayList<ICreature> target = new ArrayList<ICreature>();
        for (int i=0;i<11;i++) {
            for (int j=0;j<22;j++) {
                if (guys[i][j] != null) {
                    target.add(guys[i][j]);
                }
            }
        }
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i=0;i<target.size();i++) {
            exec.execute(target.get(i));
        }
        exec.shutdown();
        return exec;
    }

    public static void logStrToMap(String str) {
        String subString = "";
        for (int i=0;i<11;i++) {
            for (int j=0;j<22;j++) {
                subString = String.valueOf(str.charAt(0));
                str = str.substring(1);
                if (subString.equals("老") || subString.equals("蝎") || subString.equals("嘍") || subString.equals("爺") || subString.equals("蛇")) {
                    subString = subString + str.charAt(0);
                    str = str.substring(1);
                    if(String.valueOf(str.charAt(0)).equals("卒")) {
                        subString = subString + str.charAt(0);
                        str = str.substring(1);
//                        System.out.println(subString);
                    }
                }
                if (subString.equals("-")) {
                    guys[i][j] = null;
                } else if (subString.contains("老")) {
                    CalabashBros ca = new CalabashBros(subString.substring(0,2), "null", -1);
                    if (subString.contains("卒")) {
                        ca.dead();
                    }
                    guys[i][j] = ca;
                } else if (subString.contains("蝎")) {
                    Scorpion sc = new Scorpion(subString.substring(0,2));
                    if (subString.contains("卒")) {
                        sc.dead();
                    }
                    guys[i][j] = sc;
                } else if (subString.contains("嘍")) {
                    Servant servant = new Servant(subString.substring(0,2));
                    if (subString.contains("卒")) {
                        servant.dead();
                    }
                    guys[i][j] = servant;
                } else if (subString.contains("爺")) {
                    Grandpa grandpa = new Grandpa(subString.substring(0,2));
                    if (subString.contains("卒")) {
                        grandpa.dead();
                    }
                    guys[i][j] = grandpa;
                } else if (subString.contains("蛇")) {
                    Snake snake = new Snake(subString.substring(0,2));
                    if (subString.contains("卒")) {
                        snake.dead();
                    }
                    guys[i][j] = snake;
                }
            }
        }
    }

    private void clearGoodGuys(){
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                guys[i][j] = nullPos;
            }
        }
    }
    private void clearBadGuys(){
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                guys[i][j + 11] = nullPos;
            }
        }
    }
    public void printArray(){
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                if(guys[i][j] != null)
                    System.out.print(guys[i][j].getName());
                else
                    System.out.print("    ");
            }
            System.out.print("        ");
            for(int j=0;j<11;j++){
                if(guys[i][j + 11] != null)
                    System.out.print(guys[i][j + 11].getName());
                else
                    System.out.print("    ");
            }
            System.out.println();
        }
        System.out.print("\n\n\n");
    }
}

class Constants{
    public static final int BROS_NUM = 7;
    public static final int SERVANT_NUM = 20;
}