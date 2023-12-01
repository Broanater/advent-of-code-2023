import DayOne.DayOneController;

public class Main
{
	private static final DayOneController dayOneController = new DayOneController();
	public static void main(String[] args) {
		System.out.println("D1P1: " + dayOneController.One());
		System.out.println("D1P2: " + dayOneController.Two());
	}
}