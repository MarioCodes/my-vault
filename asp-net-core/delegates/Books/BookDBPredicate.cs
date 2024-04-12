using System;
using System.Collections.Generic;

namespace delegates.Models
{
    public class BookDBPredicate
    {
        // List of all books in the database:
        List<Book> list = new List<Book>();

        public BookDBPredicate()
        {
            // initialize test data
            list.Add(new Book("title1", "author1", 10, true));
            list.Add(new Book("title2", "author2", 20, true));
            list.Add(new Book("title3", "author3", 30, false));
            list.Add(new Book("title4", "author4", 40, true));
        }

        // Call a passed-in delegate on each paperback book to process it:
        // Predicate has a class as input and outputs bool
        public void ProcessPaperbackBooks(Predicate<Book> processBook)
        {
            foreach (Book book in list)
            {
                if (processBook.Invoke(book))
                {
                    // Calling the delegate:
                    Console.WriteLine($"    book title {book.Title}");
                }
            }
        }
    }
}
