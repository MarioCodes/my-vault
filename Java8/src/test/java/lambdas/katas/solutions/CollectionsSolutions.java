package lambdas.katas.solutions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Ignore;
import org.junit.Test;

public class CollectionsSolutions {
	final List<String> friends = Arrays.asList("Moha", "Carlos", "Carlos Arturo", "Alejandro",
			"Maria", "Ramon", "Lorem", "Ipsum", "Dolor", "Sit", "Amed");

	@Test
	@Ignore
	public void iterateTroughAListAndPrintAll() throws Exception {
		friends.stream()
				.forEach(System.out::println);
	}

	@Test
	@Ignore
	public void transformListInputIntoAnother() throws Exception {
		friends.stream()
				.map(String::toUpperCase) // .map((String name) -> name.toUpperCase());
				.forEach(System.out::println);
	}

	@Test
	@Ignore
	public void findElementsInTheList() throws Exception {
		friends.stream()
				.filter(name -> name.startsWith("Ca"))
				.forEach(System.out::println);
	}

	@Test
	@Ignore
	public void saveLambdaAsLocalVariableAndUseIt() throws Exception {
		Predicate<String> startsWith = name -> name.startsWith("Ca");
		friends.stream().filter(startsWith)
				.forEach(System.out::println);
	}

	@Test
	@Ignore
	public void createFunctionWhichReturnsLambdaAndUseIt() throws Exception {
		friends.stream()
				.filter(checkStartsWith("Ca"))
				.forEach(System.out::println);
	}

	private static Predicate<String> checkStartsWith(String letter) {
		return name -> name.startsWith(letter);
	}
}
