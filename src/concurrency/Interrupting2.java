package concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BlockedMutex {
	private Lock lock = new ReentrantLock();

	public BlockedMutex() {
		// TODO Auto-generated constructor stub
		// Acquired it right away ,to demonstrate interruption of a task blocked
		// on a ReentrantLock
		lock.lock();
	}

	public void f() {
		try {
			// This will never be available to a second task
			lock.lockInterruptibly();
			System.out.println("lock acquired in f()");
		} catch (InterruptedException e) {
			System.out.println("Interrupted from lock acquisition in f()");
		}
	}
}

class Blocked2 implements Runnable {
	BlockedMutex mutex = new BlockedMutex();

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Waiting for f() in BlockedMutex");
		mutex.f();
		System.out.println("Broken out of blocked call");
	}

}

public class Interrupting2 {
	public static void main(String[] args) throws InterruptedException {
		// main线程执行new Blocked2(),调用new
		// BlockedMutex()，main线程会获取BlockedMutex的lock
		Thread t = new Thread(new Blocked2());
		t.start();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("Issuing t.interrupt()");
		t.interrupt();
	}

}
