mod solutions;
mod utils;

use std::env;

fn main() {
  let args: Vec<String> = env::args().collect();
  if args.len() < 2 {
    println!("Please provide the day number for the solution you want to run")
  } else {
    let solution_num = &args[1];
    match solution_num.as_ref() {
      "1" => solutions::day1::run(),
      "14" => solutions::day14::run(),
      "18" => solutions::day18::run(),
      _ => println!("Solution not implemented {:?}", solution_num),
    }
  }
}