using delegates.Models.algorithms.interfaces;
using System;

namespace delegates.Models.algorithms.implementations
{
    public class Quack : IQuackBehaviour
    {
        void IQuackBehaviour.Quack()
        {
            Console.WriteLine("quacks like a duck");
        }

    }
}
