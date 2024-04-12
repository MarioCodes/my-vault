using System;

namespace delegates
{
    public class Program
    {
        public delegate void MyDelegate(string message);

        public static void MethodWithCallback(MyDelegate callback)
        {
            callback("method abstracted from the call");
        }

        public static void Main(string[] args)
        {
            MyDelegate handler = delegate (string message) { Console.WriteLine(message); };
            MethodWithCallback(handler);
        }

    }
}
