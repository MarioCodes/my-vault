using delegates.Models.algorithms.interfaces;
using System;

namespace delegates.Models.algorithms.implementations
{
    public class FlyWithWings : IFlyBehaviour
    {
        public void Fly()
        {
            Console.WriteLine("Flying with wings");
        }

    }
}
