import java.util.*;

public class AnyToyFactory{

    public AnyToyFactory() {
    }
    private ArrayList<String> carNames = new ArrayList<>(Arrays.asList(
                "Porsche",
                "BMW",
                "Lincoln",
                "Maserati",
                "Mercedes"));
    private ArrayList<String> cdNames = new ArrayList<>(Arrays.asList(
                "Bondibon",
                "Magformers",
                "LEGO",
                "Bauer",
                "1TOY"));
    private ArrayList<String> dollNames = new ArrayList<>(Arrays.asList(
            "Superman",
            "Barbie",
            "Joker",
            "Dark Elf",
            "Disney's Elsa"));
    private ArrayList<String> roboNames = new ArrayList<>(Arrays.asList(
                "Bumblebee",
                "Waltron",
                "Smart RoboDog",
                "RoboSpider",
                "Iron Man"));

    public Toy createCarModel(Integer count){
        return new CarModel(choseToyName(carNames), count);
    }

    public Toy createChildrensDesigner(Integer count){
        return new ChildrensDesigner(choseToyName(cdNames), count);
    }

    public Toy createDoll(Integer count){
        return new Doll(choseToyName(dollNames), count);
    }

    public Toy createRobot(Integer count){
        return new Robot(choseToyName(roboNames), count);
    }

    private String choseToyName(ArrayList<String> names){
        int nameIndex = new Random().nextInt(names.size());
        String currentName = names.get(nameIndex);
        if (names.size() > 1){
            names.remove(nameIndex);
        }
        return currentName;
    }
}
