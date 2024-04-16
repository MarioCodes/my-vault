# Delegates (csharp advanced)

Un *delegado* es un placeholder para un método. Permite pasar una función como parámetro a un método. 

Hay varios tipos de delegado:
* `Action<T>` consume una clase, devuelve `void` Ejemplo `Console.WriteLine();`
* `Predicate<T>` consume una clase, devuelve `bool` Por ejemplo para abstraer condiciones 
* `Func<T, K>` consume una clase y devuelve otra. Por ejemplo para abstraer mappings

## Action

Tenemos el struct `Book` para todos los ejemplos
~~~ csharp
public struct Book
{
	public string Title;        // Title of the book.
	public string Author;       // Author of the book.
	public decimal Price;       // Price of the book.
	public bool Paperback;      // Is it paperback?

	public Book(string title, string author, decimal price, bool paperBack)
	{
		Title = title;
		Author = author;
		Price = price;
		Paperback = paperBack;
	}
}
~~~

Luego tenemos el ejemplo `BookDBAction`
~~~ csharp
public class BookDBAction
{
	// List of all books in the database
	List<Book> list = new List<Book>();

	// initialize test data on constructor
	public BookDBAction()
	{
		list.Add(new Book("title1", "author1", 10, true));
		list.Add(new Book("title2", "author2", 20, false));
	}

	// Aqui tenemos el Action<Book> donde delegamos como se procesa cada libro
	public void ProcessPaperbackBooks(Action<Book> processBook)
	{
		foreach (Book b in list)
		{
			if (b.Paperback)
			{
				// delegate call
				processBook(b);
			}
		}
	}
}
~~~

Llamada donde ejecutamos el código y llamamos al `Action<Book>`
~~~ csharp
public static void Main(string[] args)
{
var bookDBAction = new BookDBAction();
bookDBAction.ProcessPaperbackBooks(book => Console.WriteLine(book.Title));
}
~~~

## Predicate

~~~ csharp
public class BookDBPredicate
{
	// List of all books in the database
	List<Book> list = new List<Book>();

	// initialize test data on constructor
	public BookDBPredicate()
	{
		list.Add(new Book("title1", "author1", 10, true));
		list.Add(new Book("title2", "author2", 20, false));
	}

	// Aqui tenemos el Predicate<Book> con el condicional de cada libro
	public void ProcessPaperbackBooks(Predicate<Book> processBook)
	{
		foreach (Book book in list)
		{
			// delegate call			
			if (processBook.Invoke(book))
			{
				Console.WriteLine($"    book title {book.Title}");
			}
		}
	}
}
~~~

Llamada donde ejecutamos el código y llamamos al `Predicate<Book>`
~~~ csharp
public static void Main(string[] args)
{
var bookDBPredicate = new BookDBPredicate();
bookDBPredicate.ProcessPaperbackBooks(book => !book.Paperback);
}
~~~

## Func

~~~ csharp
public class BookDBFunc
{
	// List of all books in the database
	List<Book> list = new List<Book>();

	// initialize test data on constructor
	public BookDBFunc()
	{
		list.Add(new Book("title1", "author1", 10, true));
		list.Add(new Book("title2", "author2", 20, false));
	}

	// Aqui tenemos el Func<Book, string> donde mapeamos cada libro
	public List<string> ProcessPaperbackBooks(Func<Book, string> processBook)
	{
		var fields = new List<string>();
		foreach (Book b in list)
		{
			if (b.Paperback)
			{
				// delegate call
				fields.Add(processBook(b));
			}
		}
		return fields;
	}
}
~~~

Llamada donde ejecutamos el código y llamamos al `Func<Book, string>`
~~~ csharp
public static void Main(string[] args)
{
var bookDBFunc = new BookDBFunc();
var extractedFields = bookDBFunc.ProcessPaperbackBooks(book => book.Title);
}
~~~
