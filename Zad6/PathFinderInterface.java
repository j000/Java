public interface PathFinderInterface {
    /**
     * Metoda ustawia mapę miasta.
     * 
     * @param map dwuwymiarowa tablica reprezentująca mapę dróg w mieście
     */
    public void setMap(int[][] map);

    /**
     * Metoda zwraca tablicę położeń na mapie, które reprezentują najkrótszą trasę
     * pomiędzy położeniem begin a położeniem end. Trasa najkrótsza to trasa
     * przechodząca przez możliwie najmniej położeń pośrednich. W przypadku, gdy
     * istnieją trasy o identycznej długości o wyborze decyduje preferowany kierunek
     * ruchu na skrzyżowaniach. Trasa jako pozycje skrajne zawiera położenia begin i
     * end.
     * 
     * @param begin położenie startowe
     * @param end   położenie końcowe
     * 
     * @return najkrótsza trasa od begin do end
     */
    public PositionInterface[] getShortestRoute(PositionInterface begin, PositionInterface end);

    /**
     * Metoda zwraca tablicę położeń na mapie, które reprezentują najłatwiejszą
     * trasę pomiędzy położeniem begin a położeniem end. O wyborze trasy decyduje
     * preferowany kierunek ruchu na skrzyżowaniach. Trasa jako pozycje skrajne
     * zawiera położenia begin i end.
     * 
     * @param begin położenie startowe
     * @param end   położenie końcowe
     * @return najłatwiejsza trasa od begin do end
     */
    public PositionInterface[] getEasiestRoute(PositionInterface begin, PositionInterface end);

    /**
     * Metoda zwraca tablicę położeń tablicy, które reprezentują najszybszą trasę
     * pomiędzy położeniem begin a położeniem end. Trasa najszybsza to trasa
     * przechodząca przez położenia, których suma wartości w mapie jest najmniejsza.
     * W przypadku, gdy istnieją trasy o identycznym czasie przejazdu o wyborze
     * decyduje preferowany kierunek ruchu na skrzyżowaniach. Trasa jako pozycje
     * skrajne zawiera położenia begin i end.
     * 
     * @param begin położenie startowe
     * @param end   położenie końcowe
     * @return najszybsza trasa od begin do end
     */
    public PositionInterface[] getFastestRoute(PositionInterface begin, PositionInterface end);
}
