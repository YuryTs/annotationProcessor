package hw.cvetkov;

public class hw.cvetkov.PersonToString {
    private Person object = new Person();

    public String customToString() {
        return "hw.cvetkov.PersonToString [" +
                "Age=" + object.getAge() + ", " +
                "Name=" + object.getName() + ", " +
                "]";
    }
}
