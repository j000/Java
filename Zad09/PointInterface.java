public interface PointInterface {
    /**
     * Największa, poprawna wartość współrzędnej punktu
     */
    public static final int MAX_POSITION = 16;

    /**
     * Tablica o rozmiarze 2, zawierająca współrzędne punktu.
     *
     * @return położenie punktu w dwuwymiarowej przestrzeni
     */
    public int[] getPositions();
}
