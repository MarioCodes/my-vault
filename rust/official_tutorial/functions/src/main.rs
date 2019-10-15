fn main() {
    let x = 5;

    let y = {
        let x = 6;
        x + 2
    };

    println!("The value of x is {} and y is {}", x, y);
}
