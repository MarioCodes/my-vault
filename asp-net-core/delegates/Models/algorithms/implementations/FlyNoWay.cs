using delegates.Models.algorithms.interfaces;
using System;

namespace delegates.Models.algorithms.implementations
{
    public class FlyNoWay : IFlyBehaviour
    {
        public void Fly()
        {
            Console.WriteLine("this duck cannot fly");
        }

    }
}
