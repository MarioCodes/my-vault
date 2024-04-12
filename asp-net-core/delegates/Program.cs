using delegates.Models;
using System;

namespace delegates
{
    public class Program
    {
        public static void Main(string[] args)
        {
            // Console.WriteLine("Custom delegate example");
            // var bookDB = new BookDBCustomDelegate();
            // bookDB.ProcessPaperbackBooks(book => Console.WriteLine($"   {book.Title}"));

            /*
            Console.WriteLine("Action<Book> example");
            Console.WriteLine("action takes a book as input and returns void");
            var bookDB = new BookDBAction();
            bookDB.ProcessPaperbackBooks(book => Console.WriteLine($"   {book.Title}"));
            */

            Console.WriteLine("Predicate<Book> example");
            Console.WriteLine("predicate takes a book as input and returns a boolean");
            var bookDB = new BookDBPredicate();
            bookDB.ProcessPaperbackBooks(book => !book.Paperback);
        }


    }
}
