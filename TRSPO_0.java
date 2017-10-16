import java.util.Scanner;

public class TRSPO_0 {
	private static int add(int a, int b) {
		return a + b;
	}
	
	private static int subtract(int a, int b) {
		return a - b;
	}
	
	private static int multiply(int a, int b) {
		return a * b;
	}
	
	private static double divide(int a, int b) {
		return 1.0*a / b;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter equations to calculate. Numbers should be positive integers. To stop enter 'X':");
		String line = sc.nextLine();
		while (!line.equals("X")) {
			if(line.indexOf("+") != -1) {
				System.out.println(add(Integer.parseInt(line.substring(0, line.indexOf("+")).trim()), Integer.parseInt(line.substring(line.indexOf("+") + 1).trim())));
			} else if(line.indexOf("-") != -1) {
				System.out.println(subtract(Integer.parseInt(line.substring(0, line.indexOf("-")).trim()), Integer.parseInt(line.substring(line.indexOf("-") + 1).trim())));
			} else if(line.indexOf("*") != -1) {
				System.out.println(multiply(Integer.parseInt(line.substring(0, line.indexOf("*")).trim()), Integer.parseInt(line.substring(line.indexOf("*") + 1).trim())));
			} else if(line.indexOf("/") != -1) {
				System.out.println(divide(Integer.parseInt(line.substring(0, line.indexOf("/")).trim()), Integer.parseInt(line.substring(line.indexOf("/") + 1).trim())));
			} else if(!line.equals("X")) {
				System.out.println("Wrong equation. It should have '+', '-', '*', or '/'");
			}
			line = sc.nextLine();
		}
		System.out.println("Session has been ended succesfully");
		sc.close();
	}
}