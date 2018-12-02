public class WrongArgumentException extends Exception {
    public final Point problem;

    /**
     * Metoda zwraca punkt, który stanowi powód wystąpienia wyjątku.
     * 
     * @return punkt będący powodem wystąpienia wyjątku.
     */
    public Point getProblemPoint() {
        return problem;
    }

    public WrongArgumentException(Point point) {
        problem = point;
    }

}
