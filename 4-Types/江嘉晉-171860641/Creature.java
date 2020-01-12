class Creature<T>{
    protected final T NAME;

    public Creature(){
        NAME = null;
    }

    public Creature(T aName){
        NAME = aName;
    }
    public T getName(){
        return NAME;
    }

}