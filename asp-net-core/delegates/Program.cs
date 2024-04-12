using delegates.Models;
using System;

namespace delegates
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var bookDB = new BookDB();

            // Initialize the database with some books:
            AddBooks(bookDB);

            bookDB.ProcessPaperbackBooks(book => Console.WriteLine($"   {book.Title}"));
        }

        private static void AddBooks(BookDB bookDB)
        {
            bookDB.AddBook("title1", "author1", 10, true);
            bookDB.AddBook("title2", "author2", 20, true);
            bookDB.AddBook("title3", "author3", 30, false);
            bookDB.AddBook("title4", "author4", 40, true);
        }
    }
}
