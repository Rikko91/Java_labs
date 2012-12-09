import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * 
 */

/**
 * @author 1
 *
 */
public class Vector<T> implements IVector<T>, Iterable <T> {

	private T array[];
	private int CountObjectInArray;
	

	public Vector() {
		System.out.println("Создан новый объект");
		this.array =(T[])new Object[15];
		this.CountObjectInArray=0;
	}
	
	public void add(T o) {
		if (CountObjectInArray%15==0 && CountObjectInArray>0) 
			increase_array_size();
		array[CountObjectInArray]=o;
		CountObjectInArray++;
	}
	public void add(T o, int pos) {
		T [] new_array=(T[])new Object[array.length];
		if(pos < array.length) {
			if (pos <CountObjectInArray) {
				for (int i=0; i<CountObjectInArray;i++) {
					if (i<pos) 
						new_array[i]=array[i];
					else 
						new_array[i+1]=array[i];
				}
		
				new_array[pos]=o;
				CountObjectInArray++;
				for (int i=0;i<CountObjectInArray;i++) {
					array[i]=new_array[i];
				}
			}
			else { 
				array[pos]=o;
				CountObjectInArray++;
			}
		}
		else {
			System.out.println("Плохой индекс");
		}
	}

	public void increase_array_size() {
		//T new_array []=new T[CountObjectInArray*2];
		T [] new_array=(T[])new Object[array.length+5];
		
		for (int i=0;i<array.length;i++) {
			new_array[i]=array[i];		
		}
		array  = (T[])new Object[array.length+5];
		for (int i=0;i<array.length;i++) {
			array[i]=new_array[i];
		}
		//T[] new_array = (T[]) Array.newInstance(T.class, CountObjectInArray*2);
	}
/*	public void reduce_array_size () {
		T [] new_array=(T[])new Object[array.length-1];
		for (int i=0;i<array.length-1;i++) {
			new_array[i]=array[i];
		}
		
	} */
	
	public T get(int index) {
		// TODO Auto-generated method stub
		if(index < CountObjectInArray ) {
			return  array[index];
		}
		System.out.println("Индекс неверный");
		return null;
	}
	
	public int IndexOf(T o) {
		for (int i = 0;i < CountObjectInArray;i++){
			if (o.equals(array[i])==true)
				return i;
			
		}
		// TODO Auto-generated method stub
		return -1;
	}
	
	public void remove(int index) {
		if ( index >= array.length || index < 0 ) 
			System.out.println("Такого индекса не существует");
		else {
			CountObjectInArray--;
			T [] new_array=(T[])new Object[array.length-1];
			for (int i=0;i<index;i++) {
				new_array[i]=array[i];
			}
			for (int i=index;i<array.length-1;i++) {
				new_array[i]=array[i+1];
				
			}
			//надо уменьшить размерность array на 1 
			array  = (T[])new Object[array.length-1];
			for (int i=0;i<array.length;i++) {
				array[i]=new_array[i];
				
			}
			
		}
		
		
	}


	public void print () {
		for (int i=0;i<array.length;i++) {
			if (array[i]!=null)
			System.out.println("[" + i + "] - " + array[i]);
			
		}
		System.out.println("Количество элементов в массиве - " + CountObjectInArray);
	}

	public Iterator <T> iterator() {
		return new MyIterator();
	}

	class MyIterator implements Iterator <T> {
		private int position = 0;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if (position > CountObjectInArray)
				return false;
			return true;
		}

		@Override
		public T next() {
			// TODO Auto-generated method stub
			T NextIt;
			if(array[position]==null) {
				position++;
				return null;
			}
			NextIt=array[position];
			position++;
			return NextIt;
					
		}
		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
}