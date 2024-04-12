using System;
using System.Collections.Generic;

namespace delegates.Models
{
    public class BookDBFunc
    {
        // List of all books in the database:
        List<Book> list = new List<Book>();

        public BookDBFunc()
        {
            // initialize test data
            list.Add(new Book("title1", "author1", 10, true));
            list.Add(new Book("title2", "author2", 20, true));
            list.Add(new Book("title3", "author3", 30, false));
            list.Add(new Book("title4", "author4", 40, true));
        }

        // Call a passed-in delegate on each paperback book to process it:
        // Func has any class we want as input and outputs any other class we want
        public List<string> ProcessPaperbackBooks(Func<Book, string> processBook)
        {
            var fields = new List<string>();
            foreach (Book b in list)
            {
                if (b.Paperback)
                {
                    // Calling the delegate: I want it to return the desired field
                    fields.Add(processBook(b));
                }
            }
            return fields;
        }
    }
}
