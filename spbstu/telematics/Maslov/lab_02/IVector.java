/**
 * 
 */

/**
 * @author 1
 *
 */
public interface IVector<T> {
/**
 * Добавляет объект в коллекцию
 * @param o
 */
	void add (T o);
	
	/**
	 * Добавляет объект в коллекцию на указанную позицию
	 *@param o
	 *@param pos
	 */
	void add(T o, int pos);
	
	/**
	 * Удаляет объект из коллекции, находящийся на определенной позиции
	 * @param index
	 * @return
	 */
	T get(int index);
	
	/**
	 * Возвращает индекс объекта, если такой есть в вектре. Если такого нет, то -1.
	 * @param o
	 * @return
	 */
	int IndexOf(T o);
	
	 /**
     * Удаляет объект из коллекции, находящийся на указанной позиции
     * @param index
     */
    void remove(int index);

}
