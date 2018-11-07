public interface BusLineInterface {
    /**
     * Metoda zwraca liczbę przystanków, które wchodzą w jej skład.
     * 
     * @return liczba przystanków danej lini
     */
    public int getNumberOfBusStops();

    /**
     * Metoda zwraca obiekt reprezentujący przystanek o podanym numerze. Prawidłowe
     * numery przystanów mieszczą się w przedziale od 0 do getNumberOfBusStops()-1.
     * Tylko podanie błednego numeru przystanku spowoduje zwrócenie null.
     * 
     * @param number numer przystanku
     * @return obiekt reprezentujący przystanek o numerze number
     */
    public BusStopInterface getBusStop(int number);
}
