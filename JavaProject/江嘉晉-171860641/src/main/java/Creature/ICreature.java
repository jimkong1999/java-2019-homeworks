package tk.jimkong.project.Creature;

public interface ICreature extends Runnable {
    String getName();
    boolean isAlive();
    void dead();
}


