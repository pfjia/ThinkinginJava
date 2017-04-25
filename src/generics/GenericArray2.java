package generics;

public class GenericArray2<T> {
	private Object[] array;

	public GenericArray2(int size) {
		// TODO Auto-generated constructor stub
		array = new Object[size];
	}

	public void put(int index, T item) {
		array[index] = item;
	}

	public T get(int index) {
		return (T) array[index];
	}

	public T[] rep() {
		return (T[]) array;
	}

	public static void main(String[] args) {
		GenericArray2<Integer> gai = new GenericArray2<Integer>(10);
		for (int i = 0; i < 10; i++) {
			gai.put(i, i);
		}
		for (int i = 0; i < 10; i++) {
			System.out.print(gai.get(i) + " ");
		}

		System.out.println();
		try {
			Integer[] ia = gai.rep();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}