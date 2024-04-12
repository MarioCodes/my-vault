using delegates.Models.algorithms.implementations;
using System;

namespace delegates.Models
{
    public class RedheadDuck : Duck
    {
        public RedheadDuck()
        {
            quackBehaviour = new Quack();
            flyBehaviour = new FlyWithWings();
        }

        public override void Display()
        {
            Console.WriteLine("Looks like a Redhead Duck");
        }
    }
}
