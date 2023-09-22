public class AnyToyFactory{
    private NameGenerator nameGen = new NameGenerator();

    public AnyToyFactory() {
    }

    public Toy createCarModel(Integer count){
        return new CarModel(nameGen.create(ToyTypes.CARMODEL.toString()), count);
    }

    public Toy createChildrenDesigner(Integer count){
        return new ChildrenDesigner(nameGen.create(ToyTypes.CHILDRENDESIGNER.toString()), count);
    }

    public Toy createDoll(Integer count){
        return new Doll(nameGen.create(ToyTypes.DOLL.toString()), count);
    }

    public Toy createRobot(Integer count){
        return new Robot(nameGen.create(ToyTypes.ROBOT.toString()), count);
    }

}
