using delegates.Models.algorithms.implementations;
using delegates.Models.algorithms.interfaces;
using System;

namespace delegates.Models
{
    public class MallardDuck : Duck
    {
        public MallardDuck(IQuackBehaviour quack,
            IFlyBehaviour fly)
        {
            quackBehaviour = quack;
            flyBehaviour = fly;
        }

        public override void Display()
        {
            Console.WriteLine("Looks like a Mallard Duck");
        }
    }
}
