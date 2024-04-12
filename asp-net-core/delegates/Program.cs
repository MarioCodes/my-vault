using delegates.Models;
using System;

namespace delegates
{
    public class Program
    {
        public static void Main(string[] args)
        {
            // example 1
            Console.WriteLine("Custom delegate example");
            Console.WriteLine("we can create and save as properties our own delegates, although it's also possible to use Delegates through anonymous classes and lambdas");
            var bookDBCustomDelegate = new BookDBCustomDelegate();
            bookDBCustomDelegate.ProcessPaperbackBooks(book => Console.WriteLine($"   {book.Title}"));
            Console.WriteLine();

            // example 2
            Console.WriteLine("Action<Book> example");
            Console.WriteLine("action takes a book as input and returns void");
            var bookDBAction = new BookDBAction();
            bookDBAction.ProcessPaperbackBooks(book => Console.WriteLine($"   {book.Title}"));
            Console.WriteLine();

            // example 3
            Console.WriteLine("Predicate<Book> example");
            Console.WriteLine("predicate takes a book as input and returns a boolean");
            var bookDBPredicate = new BookDBPredicate();
            bookDBPredicate.ProcessPaperbackBooks(book => !book.Paperback);
            Console.WriteLine();

            // example 4
            Console.WriteLine("Func<Book, string> example");
            Console.WriteLine("func takes any class and returns any other we want");
            var bookDBFunc = new BookDBFunc();
            var extractedFields = bookDBFunc.ProcessPaperbackBooks(book => book.Title);
            extractedFields.ForEach(field => Console.WriteLine($"    extracted field: {field}"));
            Console.WriteLine();

        }


    }
}
