/**
 * Interfejs generatora punktów.
 */
public interface PointGeneratorInterface {
    /**
     * Metoda zwraca obiekt reprezentujący pojedynczy punkt. Metoda może być
     * wywoływana współbieżnie tj. wiele wątków może wywoływać tą metodę w tym samym
     * czasie.
     *
     * @return obiekt reprezentujący pojedynczy punkt w dwuwymiarowej przestrzeni.
     */
    public PointInterface getPoint();
}
