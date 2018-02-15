package week2;


/**
 * Created by aleksei on 15.02.18.
 */
public class Point2D {
    protected float x,y;

    /**
     * Хэш-код объекта. Используется для быстрой проверки равенства
     * @return Число -- хэш код
     */
    public int hashCode() {

        return (int) (1000*x) ^ (int) (1000*y);
    }

    /**
     * Проверка на равенство
     * @param other Point2D, равенство с которым мы проверяем
     * @return True, если объекты равны, иначе False
     */
    public boolean equals(Point2D other) {
        return x == other.x && y == other.y;
    }

    /**
     * Строковое представление объекта
     * @return Удобно читаемая строка, характеризуеющая объект
     */
    public String toString() {
        return "x is: " + x + "\ny is: " + y;
    }
}
