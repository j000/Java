public interface PathFinderInterface {
    /**
     * Metoda dodaje linię autobusową do serwisu. Ten sam autobus
     * obsługuje linię w obu kierunkach.
     * @param line linia autobusowa
     * @param bus autobus, który ją obsługuje
     */
    public void addLine( BusLineInterface line, BusInterface bus );

    /**
     * Metoda zleca znalezienie połączenia autobusowego
     * prowadzącego od przystanku from do przystanku to
     * z uwzględnieniem podanej liczby przesiadek.
     * Liczba przesiadek równa zero oznacza, że poszukiwane jest
     * połączenie bezpośrednie.
     * @param from przystanek początkowy
     * @param to przystanek końcowy
     * @param transfers liczba przesiadek
     */
    public void find( BusStopInterface from, BusStopInterface to, int transfers );

    /**
     * Liczba odnalezionych rozwiązań.
     * @return liczba rozwiązań. Przed wykonaniem metody find
     * metoda zwraca zawsze 0.
     */
    public int getNumerOfSolutions();

    /**
     * Liczba przystanków autobusowych należących do rozwiązania
     * o podanym numerze. Przystanek o numerze 0 to przystanek, od
     * którego rozpoczynana jest podróż (from). Przystanek o numerze
     * getNumerOfSolutions()-1 to przystanek końcowy (to).
     * @param solution numer rozwiązania
     * @return liczba przystanków.
     */
    public int getBusStops( int solution );

    /**
     * Metoda zwraca przystanek o numerze busStop w rozwiązaniu
     * o numerze solution.
     * @param solution numer rozwiązania
     * @param busStop numer przystanku w obrębie danego rozwiązania
     * @return przystanek o podanych numerach identyfikacyjnych
     */
    public BusStopInterface getBusStop( int solution, int busStop );

    /**
     * Dla wszystkich przystanków poza ostatnim, metoda zwraca autobus, który
     * obsługuje połączenie z przystanku o numerze busStop do następnego.
     * Dla przystanku ostatniego, autobus, który obsługiwał przejazd
     * z przystanku busStop-2 do busStop-1 (czyli ostatniego).
     * @param solution numer rozwiązania
     * @param busStop numer przystanku w obrębie danego rozwiązania
     * @return autobus, którym pasażer odjeżdża z danego przystanku lub
     * w przypadku przystanku docelowego, autobus, z którego pasażer
     * na tym przystanku wysiadł.
     */
    public BusInterface getBus( int solution, int busStop );
}
