import java.util.Random;

abstract class Toy {
    private Integer id;         // динамическое поле, которое будет самостоятельным у каждого наследника класса
    private String name;        // наименование игрушки
    private Integer count;      // количество игрушек с указанным наименованием
    private String type;        // название класса на русском - для вывода на печать
    private static int newID;   // статическое поле, которое не будет принадлежать никому из наследников класса

    static { // инициализация статического поля
        newID = new Random().nextInt(100,900);
    }

    public Toy(String name, Integer count) {
        // изменение ID через посредника, имеющего статическое свойство, для того,
        // чтобы поле ID было у каждого нового объекта своё и имело уникальное значение
        id = newID++;
        this.name = name;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public void decreaseCount(){
        this.count --;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toUpperCase() + ": " +
                "ID " + getId() + ", " +
                "Имя " + getName();
    }
}
