import java.util.List;
import java.util.Map;

public interface GeometricShapeInterface {
    /**
     * Metoda dodaje punkt. Punkt dodawany jest na koniec kolekcji.
     * 
     * @param point dodawany punkt
     */
    public void add(Point point);

    /**
     * Metoda usuwa punkt, o ile taki istnieje. Jeśli w kolekcji punktów jest więcej
     * takich samych jak point, usuwany jest tylko pierwszy z nich.
     * 
     * @param point punkt do usunięcia
     * @return true - punkt istniał w kolekcji i został usunięty, false - takiego
     *         punktu nie było i w związku z tym nie został usunięty.
     */
    public boolean remove(Point point);

    /**
     * Metoda dodaje punkt przed punktem beforePoint.
     * 
     * @param point       dodawany punkt
     * @param beforePoint punkt, bezpośrednio przed którym nowy należy dodać
     * @return true - punkt odniesienia istniał i dodano nowy punkt prawidłowo,
     *         false - wskazanego punktu odniesienia nie było, dodanie nowego punktu
     *         nie było możliwe.
     */
    public boolean addBefore(Point point, Point beforePoint);

    /**
     * Metoda dodaje punkt za punktem afterPoint.
     * 
     * @param point      dodawany punkt
     * @param afterPoint punkt, bezpośrednio za którym nowy należy dodać
     * @return true - punkt odniesienia istniał i dodano nowy punkt prawidłowo,
     *         false - wskazanego punktu odniesienia nie było, dodanie nowego punktu
     *         nie było możliwe.
     */
    public boolean addAfter(Point point, Point afterPoint);

    /**
     * Metoda usuwa punkt przed punktem beforePoint.
     * 
     * @param beforePoint punkt istniejący bezpośrednio przed beforePoint należy
     *                    usunąć.
     * @return Gdy punkt odniesienia istniał oraz istniał punkt do usunięcia,
     *         zwracana jest referencja do usuniętego punktu. Gdy punkt odniesienia
     *         lub punkt przed nim nie istniał zwracany jest NULL.
     */
    public Point removeBefore(Point beforePoint);

    /**
     * Metoda usuwa punkt za punktem afterPoint.
     * 
     * @param afterPoint punkt istniejący bezpośrednio po afterPoint należy usunąć.
     * @return Gdy punkt odniesienia istniał oraz istniał punkt do usunięcia,
     *         zwracana jest referencja do usuniętego punktu. Gdy punkt odniesienia
     *         lub punkt za nim nie istniał zwracany jest NULL.
     */
    public Point removeAfter(Point afterPoint);

    /**
     * Metoda usuwa skutek ostatniego polecenia add, remove, addAfter, addBefore,
     * removeAfter lub removeBefore. Poprzez wielokrotne wykonywanie metody undo()
     * możliwe jest usunięcie wcześniejszych poleceń. Usunięciu podlegają wyłącznie
     * polecenia poprawnie zrealizowane, tych, które nie zmieniły stanu listy
     * punktów nie należy zapamietywać i uwzględniać w działaniu undo.
     *
     * @return true - polecenie undo przywróciło poprzedni stan obiektu. false -
     *         brak poleceń do usunięcia, undo nie zmieniło stanu obiektu.
     */
    public boolean undo();

    /**
     * Metoda przywraca stan systemu sprzed wykonania polecenia undo, które
     * bezpośrednio poprzedza wykonanie redo (uwaga: operacje odczytu stanu obiektu
     * mogą pojawić się pomiędzy undo a redo, podobnie jak inne operacje, które nie
     * zmienią stanu obiektu czyli np. błęde zlecnie addBefore)
     * 
     * @return true - poprawnie przywrócono zmianę, którą usunięto wcześnie za
     *         pomocą undo, false - bezpośrednio przed redo nie było undo i
     *         polecenie nie mogło zadziałać prawidłowo.
     */
    public boolean redo();

    /**
     * Metoda zwraca aktualną listę wszystkich punktów.
     * 
     * @return lista punktów
     */
    public List<Point> get();

    /**
     * Metoda zwraca listę punktów bez kolejnych powtórzeń. Kolejność punktów w tej
     * kolekcji ma być taka sama, jak w przypadku list zwracanej za pomocą metody
     * get jednakże bez powtórzeń. Jeśli kolejność punktów zwracana za pomocą get to
     * np. P1, P2, P2, P3, P2, P1, P1, P1, P4 to metoda getUniq ma zwrócić: P1, P2,
     * P3, P2, P1 i P4, czyli usuwane są wszystkie kolejne wystąpienia danego
     * punktu. Powtórzenie punktu ma miejsce wtedy, gdy P1.equals(P2)=true. Uwaga:
     * metoda zwraca listę (nie zbiór) punktów, wciąż jeden punkt może wystąpić
     * wielokrotnie w wyniku.
     * 
     * @return kolekcja punktów bez powtórzeń.
     */
    public List<Point> getUniq();

    /**
     * Metoda zwraca mapę liczby występień punktów. Jako klucz w mapie występuje
     * punkt, jako wartość liczba wystąpień tego punktu w budowanym kształcie.
     * 
     * @return mapa liczby wystąpien punktów.
     */
    public Map<Point, Integer> getPointsAsMap();

}
