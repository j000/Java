public class NoSuchPointException extends Exception {
    public final Point problem;

    /**
     * Metoda zwraca punkt, który stanowi powód wystąpienia wyjątku.
     * 
     * @return punkt będący powodem wystąpienia wyjątku.
     */
    public Point getProblemPoint() {
        return problem;
    }

    public NoSuchPointException(Point point) {
        problem = point;
    }
}
