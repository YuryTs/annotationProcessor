package hw.cvetkov;



public class Main {
    public static void main(String[] args) {

        Person person = new Person(23, "Vlad");
        Dog dog = new DogBuilder().setAge(2).setName("zuza").build();



    }
}