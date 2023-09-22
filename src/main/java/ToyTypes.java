public enum ToyTypes {
    CARMODEL ("CarModel"),
    CHILDRENDESIGNER("ChildrenDesigner"),
    DOLL("Doll"),
    ROBOT("Robot");

    private final String title;

    ToyTypes(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
