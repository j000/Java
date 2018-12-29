/**
 * Klasa reprezentuje wyjątek zgłaszany gdy użyta liczba wymiarów nie zgadza się
 * z oczekiwaną.
 */
public class WrongNumberOfDimensionsException extends Exception {
    /**
     * Oczekiwana liczba wymiarów
     */
    private final int expectedDimensions;
    /**
     * Faktycznie użyta liczba wymiarów, niezgodna z oczekiwaną
     */
    private final int actualDimensions;

    /**
     * Metoda zwraca oczekiwaną liczbę wymiarów.
     * 
     * @return oczekiwana liczba wymiarów.
     */
    public int getExpectedDimensions() {
        return expectedDimensions;
    }

    /**
     * Metoda zwraca faktycznie użytą liczbę wymiarów.
     * 
     * @return użyta liczba wymiarów.
     */
    public int getActualDimensions() {
        return actualDimensions;
    }

    public WrongNumberOfDimensionsException(int expectedDimensions, int actualDimensions) {
        this.expectedDimensions = expectedDimensions;
        this.actualDimensions = actualDimensions;
    }

}
