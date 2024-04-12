using System.Collections.Generic;

namespace delegates.Models
{
    // Declare a custom delegate type for processing a book
    public delegate void ProcessBookCallback(Book book);

    public class BookDBCustomDelegate
    {
        // List of all books in the database:
        List<Book> list = new List<Book>();

        public BookDBCustomDelegate()
        {
            // initialize test data
            list.Add(new Book("title1", "author1", 10, true));
            list.Add(new Book("title2", "author2", 20, true));
            list.Add(new Book("title3", "author3", 30, false));
            list.Add(new Book("title4", "author4", 40, true));
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
