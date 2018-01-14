package lambdas;

import java.util.Arrays;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

public class Collections {
	final List<String> friends = Arrays.asList("Moha", "Carlos", "Carlos Arturo", "Alejandro",
			"Maria", "Ramon");

	@Test
	@Ignore("Do not use this, use method reference.")
	public void givenFriendsWhenIterateThenPrintAllOldWay() {
		friends.forEach(name -> System.out.println(name));
	}

	@Test
	public void givenFriendsWhenIterateThenPrintWithMethodReference() throws Exception {
		friends.forEach(System.out::println);
	}

}
