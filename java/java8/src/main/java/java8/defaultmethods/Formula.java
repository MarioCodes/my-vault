package java8.defaultmethods;

public interface Formula {
	double calculate(int a);

	/**
	 * Default methods allows us to add non-abstract method implementations to
	 * interfaces. It can be used out of the box.
	 */
	default double sqrt(int a) {
		return Math.sqrt(a);
	}
}
