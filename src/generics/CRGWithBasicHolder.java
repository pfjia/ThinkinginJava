package generics;


class Subtype extends BasicHolder<Subtype> {

}

public class CRGWithBasicHolder {
	public static void main(String[] args) {
		Subtype st1 = new Subtype();
		Subtype st2 = new Subtype();
		st1.set(st2);
		Subtype st3 = st1.get();
		st1.f();
		System.out.println(st2 == st3);

	}

}