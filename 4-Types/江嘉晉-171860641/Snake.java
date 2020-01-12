class Snake<T> extends Creature{
    public Snake(T aName){
        super(aName);
    }
    public void encourage(){
        System.out.println("Good job");
    }
}