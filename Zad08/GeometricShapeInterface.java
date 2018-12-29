import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GeometricShapeInterface {
        /**
         * Metoda dodaje punkt. Punkt dodawany na koniec kolekcji.
         *
         * @param point dodawany punkt
         * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy nowododawany
         *                                          punkt posiada inną liczbę wymiarów
         *                                          niż te, które już wcześniej przed
         *                                          nim dodano. O poprawnej liczbie
         *                                          wymiarów decyduje <b>pierwszy</b>
         *                                          punkt dodany do kształtu.
         */
        public void add(Point point) throws WrongNumberOfDimensionsException;

        /**
         * Metoda usuwa punkt point, o ile taki istnieje. Jeśli w kolekcji punktów jest
         * więcej takich samych jak point, usuwany jest tylko pierwszy z nich.
         *
         * @param point punkt do usunięcia
         * @throws WrongArgumentException wyjątek zgłaszany gdy zlecane jest usunięcie
         *                                punktu, który nie należy do figury.
         */
        public void remove(Point point) throws WrongArgumentException;

        /**
         * Metoda dodaje punkt przed punktem beforePoint.
         *
         * @param point       dodawany punkt
         * @param beforePoint punkt, bezpośrednio przed którym nowy należy dodać
         * @throws WrongArgumentException           wyjątek zgłaszany, gdy jako
         *                                          beforePoint przekazany został punkt,
         *                                          który nie został wcześniej dodany do
         *                                          figury.
         * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
         *                                          wymiarów punktu point lub
         *                                          beforePoint nie jest zgodna z liczbą
         *                                          wymiarów punktów dodanych wcześniej
         *                                          do kształtu.
         */
        public void addBefore(Point point, Point beforePoint)
                        throws WrongArgumentException, WrongNumberOfDimensionsException;

        /**
         * Metoda dodaje punkt za punktem afterPoint.
         *
         * @param point      dodawany punkt
         * @param afterPoint punkt, bezpośrednio za którym nowy należy dodać
         * @throws WrongArgumentException           wyjątek zgłaszany, gdy jako
         *                                          afterPoint przekazany został punkt,
         *                                          który nie został wcześniej dodany do
         *                                          figury.
         * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
         *                                          wymiarów punktu point lub afterPoint
         *                                          nie jest zgodna z liczbą wymiarów
         *                                          punktów dodanych wcześniej do
         *                                          kształtu.
         */
        public void addAfter(Point point, Point afterPoint)
                        throws WrongNumberOfDimensionsException, WrongArgumentException;

        /**
         * Metoda usuwa punkt przed punktem beforePoint.
         *
         * @param beforePoint punkt istniejący bezpośrednio przed beforePoint należy
         *                    usunąć.
         * @return Gdy punkt odniesienia istniał oraz istniał punkt do usunięcia,
         *         zwracana jest referencja do usuniętego punktu.
         * @throws NoSuchPointException             wyjątek zgłaszany, gdy punkt
         *                                          beforePoint jest pierwszym z punktów
         *                                          figury i innego punktu przed nim nie
         *                                          ma.
         * @throws WrongArgumentException           wyjątek zgłaszany, gdy punkt
         *                                          beforePoint nie został wcześniej
         *                                          dodany do figury.
         * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
         *                                          wymiarów punktu beforePoint nie jest
         *                                          zgodna z liczbą wymiarów punktów
         *                                          dodanych wcześniej do kształtu.
         */
        public Point removeBefore(Point beforePoint)
                        throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException;

        /**
         * Metoda usuwa punkt za punktem afterPoint.
         *
         * @param afterPoint punkt istniejący bezpośrednio po afterPoint należy usunąć.
         * @return Gdy punkt odniesienia istniał oraz istniał punkt do usunięcia,
         *         zwracana jest referencja do usuniętego punktu.
         * @throws NoSuchPointException             wyjątek zgłaszany, gdy punkt
         *                                          beforePoint jest pierwszym z punktów
         *                                          figury i innego punktu przed nim nie
         *                                          ma.
         * @throws WrongArgumentException           wyjątek zgłaszany, gdy punkt
         *                                          beforePoint nie został wcześniej
         *                                          dodany do figury.
         * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy liczba
         *                                          wymiarów punktu beforePoint nie jest
         *                                          zgodna z liczbą wymiarów punktów
         *                                          dodanych wcześniej do kształtu.
         */

        public Point removeAfter(Point afterPoint)
                        throws NoSuchPointException, WrongNumberOfDimensionsException, WrongArgumentException;

        /**
         * Metoda zwraca aktualną listę wszystkich punktów.
         *
         * @return lista punktów
         */
        public List<Point> get();

        /**
         * Metoda zwraca zbiór punktów czyli kolekcję punktów bez powtórzeń. Kolejność
         * punktów w tej kolekcji nie ma znaczenia. Powtórzenie punktu ma miejsce wtedy,
         * gdy P1.equals(P2)=true.
         *
         * @return kolekcja punktów bez powtórzeń.
         */
        public Set<Point> getSetOfPoints();

        /**
         * Metoda zwraca obiekt typu Optional zawierający (o ile istnieje) punkt,
         * którego współrzędne przekazywane są na liście positions. Jeśli istnieje wiele
         * punktów o podanych wspołrzędnych zwracany jest punkt, który został dodany
         * jako ostatni. Metoda nigdy nie może zakończyć się zwróceniem null, jeśli
         * punktu o podanych współrzędnych nie ma, należy zwrócić pusty obiekt Optional.
         *
         * @param positions lista współrzędnych
         * @return obiekt Optional zawierający (o ile istnieje) punkt, o przekazanych za
         *         pomocą positions współrzędnych, w przeciwnym wypadku pusty obiekt
         *         Optional.
         * @throws WrongNumberOfDimensionsException wyjątek zgłaszany, gdy rozmiar listy
         *                                          jest niezgodny z liczbą wymiarów
         *                                          punktów należących do kształtu.
         */
        public Optional<Point> getByPosition(List<Integer> positions) throws WrongNumberOfDimensionsException;
}
