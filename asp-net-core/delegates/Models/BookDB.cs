using System.Collections;
using System.Collections.Generic;

namespace delegates.Models
{
    // Declare a delegate type for processing a book:
    public delegate void ProcessBookCallback(Book book);

    public class BookDB
    {
        // List of all books in the database:
        List<Book> list = new List<Book>();

        // Add a book to the database:
        public void AddBook(string title, string author, decimal price, bool paperBack)
        {
            list.Add(new Book(title, author, price, paperBack));
        }

        // Call a passed-in delegate on each paperback book to process it:
        public void ProcessPaperbackBooks(ProcessBookCallback processBook)
        {
            foreach (Book b in list)
            {
                if (b.Paperback)
                {
                    // Calling the delegate:
                    processBook(b);
                }
            }
        }
    }
}
