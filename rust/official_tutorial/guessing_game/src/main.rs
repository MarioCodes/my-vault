use std::cmp::Ordering;
use std::io;

use rand::Rng;

fn main() {
    println!("Guess the number.");
    let secret_number: u32 = rand::thread_rng().gen_range(1, 101);
    println!("Random number is {}", secret_number);

    loop {
        println!("Input your number: ");

        let mut guess: String = String::new();

        io::stdin()
            .read_line(&mut guess)
            .expect("oh no!");

        let guess: u32 = match guess.trim().parse() {
            Ok(num) => num,
            Err(_) => continue,
        };

        println!("Your number is: {}", guess);

        // comparation
        match guess.cmp(&secret_number) {
            Ordering::Less => println!("too small!"),
            Ordering::Equal => {
                println!("Yeah!");
                break;
            }
            Ordering::Greater => println!("too big!"),
        }
    }
}
