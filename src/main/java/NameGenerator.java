import java.util.*;

public class NameGenerator {
    private final HashMap<String, ArrayList<String>> names = new HashMap<>();

    public NameGenerator() {
        names.put(ToyTypes.CARMODEL.toString(), new ArrayList<>(Arrays.asList(
            "Porsche",
            "BMW",
            "Lincoln",
            "Maserati",
            "Mercedes")));
        names.put(ToyTypes.CHILDRENDESIGNER.toString(), new ArrayList<>(Arrays.asList(
            "Bondibon",
            "Magformers",
            "LEGO",
            "Bauer",
            "1TOY")));
        names.put(ToyTypes.DOLL.toString(), new ArrayList<>(Arrays.asList(
            "Superman",
            "Barbie",
            "Joker",
            "Dark Elf",
            "Disney's Elsa")));
        names.put(ToyTypes.ROBOT.toString(), new ArrayList<>(Arrays.asList(
            "Bumblebee",
            "Waltron",
            "Smart RoboDog",
            "RoboSpider",
            "Iron Man")));
    }

    public String create(String toyType){
        ArrayList<String> toyNameList = names.get(toyType);
        int nameIndex = new Random().nextInt(toyNameList.size());
        String currentName = toyNameList.get(nameIndex);
        if (toyNameList.size() > 1){
            toyNameList.remove(nameIndex);
        }
        return currentName;
    }
}
