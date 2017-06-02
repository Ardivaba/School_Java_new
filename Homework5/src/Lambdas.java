import java.util.ArrayList;
import java.util.function.Predicate;

public class Lambdas {

	public static void main(String[] args) {
		print("Names that start with A", e -> e.startsWith("A"), "Mati", "Kati", "Aadu");
		print("Values that are greater than ten", e -> e > 10, 40, 0, 4, -5, -14, 35, 1, 5, 6, 7, 8);
		print("Names that start with b", e -> e.startsWith("b"), "Mati", "Kati", "Aadu");
		print("Values that are greater than thousand", e -> e > 1000, 40, 0, 4, -5, -14, 35, 1, 5, 6, 7, 8);
	}

	public static <T> void print(String question, Predicate<T> predicate, T... array) {
		System.out.print(question + ": ");
		
		ArrayList<T> matches = new ArrayList<T>();
		for(T value : array) {
			if(predicate.test(value)) {
				matches.add(value);
			}
		}
		
		if(matches.size() < 1) {
			System.out.println("No matches.");
		} else {
			for(T match : matches) {
				System.out.print(match + " ");
			}
		}
		
		System.out.println("\n");
	}
}
