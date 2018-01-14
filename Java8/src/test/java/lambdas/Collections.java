package lambdas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class Collections {
	final List<String> friends = Arrays.asList("Moha", "Carlos", "Carlos Arturo", "Alejandro",
			"Maria", "Ramon");

	// Example 1
	@Test
	@Ignore("Do not use this, use method reference.")
	public void givenFriendsWhenIterateThenPrintAllOldWay() {
		friends.forEach(name -> System.out.println(name));
	}

	@Test
	@Ignore
	public void givenFriendsWhenIterateThenPrintWithMethodReference() throws Exception {
		friends.forEach(System.out::println);
	}

	// Example 2
	@Test
	@Ignore("Old way")
	public void givenFriendsWhenUpperCaseOldWayThenPrint() throws Exception {
		ArrayList<String> upperCase = new ArrayList<>();
		for (String name : friends)
			upperCase.add(name.toUpperCase());

		for (String name : upperCase)
			System.out.println(name);
	}

	@Test
	public void givenFriendsWhenUpperCaseThenPrint() throws Exception {
		friends.stream()
				.map(String::toUpperCase)
				.forEach(System.out::println);
	}
}
