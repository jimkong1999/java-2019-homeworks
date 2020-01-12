class CalabashBros<T> extends Creature{
    private final int LEVEL;
    private final String COLOR;

    public CalabashBros(T aName, String aCOLOR, int aLEVEL){
        super(aName);
        LEVEL = aLEVEL;
        COLOR = aCOLOR;
    }
    public int getLevel(){
        return LEVEL;
    }
    public String getColor(){
        return COLOR;
    }
}