using delegates.Models.algorithms.interfaces;
using System;

namespace delegates.Models.algorithms.implementations
{
    public class MuteQuack : IQuackBehaviour
    {
        public void Quack()
        {
            Console.WriteLine("<<Silence>>");
        }

    }
}
