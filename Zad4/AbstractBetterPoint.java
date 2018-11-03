public abstract class AbstractBetterPoint {

    /**
     * Metoda pozwala na ustalenie liczby wymiarów, którą punkt będzie obsługiwał.
     * Poprawne wartości dla identyfikatora wymiaru to od 0 do dimensions-1
     * włącznie.
     * 
     * @param dimensions liczba wymiarów
     */
    public abstract void setDimensions(int dimensions);

    /**
     * Metoda zwraca aktualny poziom nałożonej ochrony. Wartości większe od zero
     * pokazują ile poziomów ochrony nałożono. Wartość zero oznacza, że ochrony nie
     * ma i zmiana stanu jest możliwa.
     * 
     * @return aktualny poziom ochrony przez modyfikacją. Zero oznacza brak
     *         ograniczeń na modyfikowanie stanu obiektu.
     */
    public abstract int lockLevel();

    /**
     * Metoda blokuje modyfikacje obiektu do czasu wykonania metody unlock z
     * poprawnym hasłem, to jest tym, jakie zostało podane w wywołaniu metody lock.
     * Wielokrotne wykonanie metody lock powoduje nałożenie kolejnego poziomu
     * ochrony i wprowadzenie hasła kolejnego poziomu. Jeśli np. 3 razy wykonano
     * metodę lock podając hasła h1, h2 i h3, to aby zdjąć ochronę należy 3 razy
     * wykonać metodę unlock podając kolejno hasła h3, h2 i h1.
     *
     * @param password hasło jakie należy podać aby odblokować możliwość zmian stanu
     *                 obiektu na danym poziomie ochrony.
     * @return poziom ochrony - pierwsze wykonanie lock powinno zwrócić 1, kolejne
     *         (o ile nie wykonano unlock z poprawnym hasłem) powinno zwrócić 2 itd.
     */
    public abstract int lock(String password);

    /**
     * W przypadku podania poprawnego hasła, metoda zdejmuje jeden poziom blokady
     * możliwość zmiany stanu obiektu. Ponieważ istnieje możliwość wielopoziomowego
     * zabezpiecznia modyfikacji odblokowanie możliwości zmiany obiektu moze się
     * wiązać z wielokrotnym wykonaniem metody unlock. Zmiany są możliwe tylko
     * wtedy, gdy aktualny poziom ochrony wynosi 0 tj. popranie wykonano odpowiednią
     * liczbę wywołań unlock.
     *
     * @param password hasło, które ma odblokować dostęp do modyfikacji obiektu na
     *                 danym poziomie.
     * @return liczba poziomów ochrony, którą należy usunąć, aby modyfikacje były
     *         możliwe. 0 - modyfikacje obiektu są możliwe - obiekt nie jest
     *         chroniony. Wynik większy od 0 oznacza, że należy ochrona obiektu jest
     *         nadal aktywna.
     */
    public abstract int unlock(String password);

    /**
     * Metoda pozwala na przemieszczenie podanego wymiaru punktu o delta.
     * Modyfikacje położenia jest możliwa tylko gdy modyfikacje obiektu nie zostały
     * wcześniej wstrzymane za pomocą metody lock. Nowa wartość współrzędnej dla
     * danego wymiaru to suma wartości poprzedniej oraz delta.
     * 
     * @param dimension wymiar, który ma ulec zmianie
     * @param delta     wielkość, o którą współrzędna ma zostać zmodyfikowana
     * @return true jeśli doszło do zmiany położenia, false gdy zmiana nie nastąpiła
     *         z powodu blokady możliwości zmiany lub gdy delta była równa zero.
     */
    public abstract boolean move(int dimension, double delta);

    /**
     * Metoda pozwala na przypisanie określonej wartości do wybranej współrzędnej
     * punktu. Przypisanie wartości możliwe jest tylko, gdy modyfikacje obiektu nie
     * są zablokowane.
     * 
     * @param dimension wymiar, do którego należy wpisać value
     * @param value     nowa wartość współrzędnej dla wymiaru dimension
     * @return true - poprawnie wpisano wartość współrzędnej, false - modyfikacje
     *         zablokowane.
     */
    public abstract boolean set(int dimension, double value);

    /**
     * Metoda zwraca określoną współrzędną punktu.
     * 
     * @param dimension wymiar, który ma zostać zwrócony w wyniku
     * @return wartość podanej współrzędnej
     */
    public abstract double get(int dimension);
}
