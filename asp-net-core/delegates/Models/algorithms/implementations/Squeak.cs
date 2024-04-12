using delegates.Models.algorithms.interfaces;
using System;

namespace delegates.Models.algorithms.implementations
{
    public class Squeak : IQuackBehaviour
    {
        public void Quack()
        {
            Console.WriteLine("Squeaks like a rubber duck");
        }

    }
}
