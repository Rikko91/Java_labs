import java.util.Iterator;


/**
 * 
 */

/**
 * @author 1
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vector<Integer> MyVect=new Vector<Integer>();
		//добавим 15 элементов
		MyVect.add(20);
		MyVect.add(5);
		MyVect.add(8);
		MyVect.add(30);
		MyVect.add(41);
		MyVect.add(54);
		MyVect.add(4);
		MyVect.add(7);
		MyVect.add(9);
		MyVect.add(98);
		MyVect.add(113);
		MyVect.add(118);
		MyVect.add(65);
		MyVect.add(54);
		MyVect.add(32);
		//добавим еще, чтобы проверить метод increase_size_array
		MyVect.add(21);
		MyVect.add(15);
		MyVect.print();
		//проверка метода add на определенную позицию
		System.out.println("Добавим число 777 на позицию 11");
		MyVect.add(777, 11);
		MyVect.print();
		//проверка метода remove 
		System.out.println("Удалим объект, находящийся на позиции номер 13");
		MyVect.remove(13);
		MyVect.print();
		//проверка метода get
		System.out.println("Возвратим объект на позиции номер 7");
		System.out.println(MyVect.get(7));
		//проверка метода IndexOf
		System.out.println("Возвратим индекс объекта  777 ");
		System.out.println(MyVect.IndexOf(777));
		
	}

}
