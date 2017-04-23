package exceptions;

public class FullConstructors {
	public static void f() throws MyException {
		System.out.println("Throwing MyException from f()");
		throw new MyException();
	}

	public static void g() throws MyException {
		System.out.println("Throwing MyException from g()");
		throw new MyException("Generator in g()");
	}

	public static void main(String[] args) {
		try {
			f();
		} catch (MyException e) {
			e.printStackTrace(System.out);
		}

		try {
			g();
		} catch (MyException e) {
			e.printStackTrace(System.out);
		}
	}

}

class MyException extends Exception {
	public MyException() {
		// TODO Auto-generated constructor stub
	}

	public MyException(String msg) {
		super(msg);
	}
}
