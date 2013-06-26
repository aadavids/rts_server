package server_battle;

public class Battle {

	private static final int EXECUTION_POOL_SIZE = 4;

	public static void main(String args[]) {
		OrderQueue queue = new OrderQueue();

		OrderService runner = new OrderService(queue, EXECUTION_POOL_SIZE);
		runner.run();
    }
}
