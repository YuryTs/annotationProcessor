package hw.cvetkov;

public class DogBuilder {

    private Dog object = new Dog();

    public Dog build() {
        return object;
    }

    public DogBuilder setName(java.lang.String value) {
        object.setName(value);
        return this;
    }

    public DogBuilder setAge(int value) {
        object.setAge(value);
        return this;
    }

}
