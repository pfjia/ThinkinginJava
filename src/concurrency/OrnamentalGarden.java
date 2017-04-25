package concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Count {
	private int count;
	private Random rand = new Random(47);

	// ��Remove the synchronized keyword to see counting fail

	public synchronized int increment() {
		int temp = count;

		if (rand.nextBoolean()) {
			Thread.yield();
		}
		return (count = ++temp);
	}

	// ���û��synchronizedͬ����������incrementִ���м�ִ�и÷���������count���ȶ�״̬��ֵ
	// ��������δ�����û�����⣬��Ϊ�ǵ�����incrementִ����Ϻ󣬲ŵ���value()
	public synchronized int value() {
		return count;
	}
}

class Entrance implements Runnable {
	private static Count count = new Count();
	private static List<Entrance> entrances = new ArrayList<Entrance>();
	private int number = 0;

	// Doesn't need synchronization to read
	private final int id;
	private static volatile boolean canceled = false;

	// Atomic operation on a bolatile field
	public static void cancel() {
		canceled = true;
	}

	public Entrance(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
		// Keep this task in a list.Also prevents garbage collection of dead
		// tasks
		entrances.add(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (!canceled) {
			++number;
			System.out.println(this + " Total: " + count.increment());
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("sleep interrupted");
			}
		}
		System.out.println("Stopping " + this);
	}

	public int getValue() {
		return number;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public static int getTotalCount() {
		return count.value();
	}

	public static int sumEntrances() {
		int sum = 0;
		for (Entrance entrance : entrances) {
			sum += entrance.getValue();
		}
		return sum;
	}
}

public class OrnamentalGarden {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new Entrance(i));
		}
		// Run for a while. the stop and collect the data
		TimeUnit.SECONDS.sleep(3);
		Entrance.cancel();
		exec.shutdown();
		if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
			System.out.println("Some tasks were not terminated!");
		}
		System.out.println("Total: " + Entrance.getTotalCount());
		System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
	}

}