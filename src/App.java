import java.io.*;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class App {

    public static void main(String[] args) throws IOException {
        Animal[] animalM1 = {new Animal("Cat"), new Animal("Dog"), new Animal("Elephant"),
                new Animal("Cock"), new Animal("Bull"), new Animal("Ant"),
                new Animal("Tentecles"), new Animal("Worm")};
        ByteArrayOutputStream bai = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bai);
        oos.writeInt(animalM1.length);
        for (int i = 0; i < animalM1.length; i++) {
            oos.writeObject(animalM1[i]);
        }
        oos.flush();
        oos.close();
        for(Animal animal : deserializeAnimalArray(bai.toByteArray())){
            System.out.println(animal.toString());
        }
    }

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) new Object[2];
        stream.forEach(x -> {
            if (x != null && (array[0] == null)) {
                array[0] = array[1] = x;
            }
            if (order.compare(x, array[0]) < 0) {
                array[0] = x;
            } else if (order.compare(x, array[1]) > 0) {
                array[1] = x;
            }
        });
        minMaxConsumer.accept(array[0], array[1]);
    }

    public static Animal[] deserializeAnimalArray(byte[] data) {
        ByteArrayInputStream inputByteArrayStream = new ByteArrayInputStream(data);
        Animal[] array;
        try {
            ObjectInputStream objectIO = new ObjectInputStream(inputByteArrayStream);
            int count = objectIO.readInt();
            array = new Animal[count];
            for (int i = 0; i < array.length; i++) {
                array[i] = (Animal) objectIO.readObject();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return array;
    }
}
