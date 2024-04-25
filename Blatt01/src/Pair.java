import java.util.Objects;

public class Pair<E> {

private E first;
private E second;

public Pair(E first, E second) {
    this.first = first;
    this.second = second;
}

public Pair(Pair<E> other) {
    this.first = other.getFirst();
    this.second = other.getSecond();
}

public String toString() {
    return "Pair<" + first + ", " + second + ">";
}

public E getFirst() {
    return first;
}

public E getSecond() {
    return second;
}

public void setFirst(E first) {
    this.first = first;
}

public void setSecond(E second) {
    this.second = second;
}

public boolean equals(Pair<E> obj) {
    if (this == obj) {
        return true;
    }
    if (this.first == obj.getFirst() && this.second == obj.getSecond()) {
        return true;
    }
    return false;
}

public void swap() {
     E tmp = this.first;
     this.first = this.second;
     this.second = tmp;
}
    public static void main(String[] args) {
        Pair<Integer> pair1 = new Pair<>(1, 2);
        Pair<Integer> pair2 = new Pair<>(1, 2);
        System.out.println("Variable pair1 hat den Wert: " + pair1);
        System.out.println("Variable pair2 hat den Wert: " + pair2);
        System.out.println("Syntaktische Gleichheit von pair1 und pair2 ist: " + (pair1==pair2));
        System.out.println("Semantische Gleichheit von pair1 und pair2 ist: " + pair1.equals(pair2));
        Pair<Integer> pair1b = pair1;
        Pair<Integer> pair2b = new Pair<>(pair2);
        pair1.swap();
        pair2.setFirst(10);
        System.out.println("Nach swap() hat Variable pair1 den Wert: " + pair1);
        System.out.println("Nach setFirst(10) hat Variable pair2 den Wert: " + pair2);
        System.out.println("Die zuvor erstellte Kopie pair1b hat den Wert: " + pair1b);
        System.out.println("Die zuvor erstellte Kopie pair2b hat den Wert: " + pair2b);
        /*
        Die erwartete Ausgabe ist:
Variable pair1 hat den Wert: Pair<1, 2>
Variable pair2 hat den Wert: Pair<1, 2>
Syntaktische Gleichheit von pair1 und pair2 ist: false
Semantische Gleichheit von pair1 und pair2 ist: true
Nach swap() hat Variable pair1 den Wert: Pair<2, 1>
Nach setFirst(10) hat Variable pair2 den Wert: Pair<10, 2>
Die zuvor erstellte Kopie pair1b hat den Wert: Pair<2, 1>
Die zuvor erstellte Kopie pair2b hat den Wert: Pair<1, 2>
         */
    }
}

