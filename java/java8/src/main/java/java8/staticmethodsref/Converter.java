package java8.staticmethodsref;

@FunctionalInterface
public interface Converter<F, T> {
	T convert(F from);
}
