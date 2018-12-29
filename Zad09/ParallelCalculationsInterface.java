/**
 * Interfejs systemu obliczeń współbieżnych z zadaną liczbą wątków i z
 * możliwością wstrzymywania i pownownego uruchamiania obliczeń.
 */
public interface ParallelCalculationsInterface {
    /**
     * Metoda pozwala na przekazanie do systemu obiektu oferującego usługę
     * generowania punktów.
     *
     * @param generator generator punktów
     */
    public void setPointGenerator(PointGeneratorInterface generator);

    /**
     * Metoda pozwala na wprowadzenie do systemu wymaganej liczby wątków. Liczba
     * wątków musi zostać ustawiona przed uruchomieniem obliczeń za pomocą metody
     * start(). Liczba wątków może zostać zmieniona po uruchomieniu obliczeń w
     * czasie, gdy obliczenia zostały wstrzymane za pomocą suspendCalculations a
     * jeszcze nie uruchomione ponownie.
     *
     * @param threads liczba wątków obliczeniowych
     */
    public void setNumberOfThreads(int threads);

    /**
     * Metoda służąca do rozpoczęcia obliczeń. Po wprowadzeniu danych do systemu za
     * pomocą pary metod setPointGenerator oraz setNumberOfThreads obliczenia są
     * uruchamiane metodą start(). Metoda start() nie może zablokować wątku
     * użytkownika, tj. musi zakończyć się i pozwolić użytkownikowi na dalsze
     * używanie tego samego wątku. Obliczenia mają być realizowane za pomocą wątków
     * utworzonych przez implementację interfejsu ParallelCalculationsInterface.
     */
    public void start();

    /**
     * Zlecenie wstrzymania obliczeń. Po zakończeniu metody suspendCalculations
     * obliczenia nie mogą być wykonywane. Czas pracy metody suspendCalculations nie
     * może być znacząco dłuższy od czasu potrzebnego do pobrania punktu z
     * generatora.
     */
    public void suspendCalculations();

    /**
     * Zlecenie kontynuacji obliczeń wstrzymanych za pomocą suspendCalculations.
     * Metoda nie może blokować wątku użytkownika.
     */
    public void continueCalculations();

    /**
     * Metoda zwraca położenie środka geometrycznego wyliczonego na podstawie
     * wszystkich pobranych z generatora punktów.
     * 
     * @return środek geometryczny
     */
    public double[] getGeometricCenter();

    /**
     * Histogram sporządzony na podstawie wszystkich pobranych z generatora punktów.
     * Jest to liczba puntków, których współrzędne odpowiadają pozycji w tablicy.
     * Pobranie np. punktu o współrzędnych [3,5] ma spowodować inkrementację
     * licznika tabeli o indeksie [3][5]. Wynikiem pracy metody jest tablica o
     * rozmiarze (PointInterface.MAX_POSITION+1)x(PointInterface.MAX_POSITION+1)
     * pozycji.
     *
     * @return histogram rozkładu współrzędnych punktów.
     */
    public int[][] getHistogram();
}
