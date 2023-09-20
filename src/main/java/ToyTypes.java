public enum ToyTypes {
    CARMODEL ("CarModel"),
    CHILDRENSDESIGNER("ChildrensDesigner"),
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
