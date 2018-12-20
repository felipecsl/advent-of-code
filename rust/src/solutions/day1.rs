use crate::utils::read_file_as_string;
use std::error::Error;

pub fn run() {
  let input = read_file_as_string("1.txt");
  let freq = input.split("\n")
    .into_iter()
    .filter(|&s| !s.is_empty())
    .map(|s| s.parse::<i32>())
    .map(|num| match &num {
        Err(why) => panic!("couldn't convert {:?}: {}", num, why.description()),
        Ok(num_int) => num_int.clone()
      }
    )
    .fold(0, |total, next| total + next);
  println!("Part I: {}", freq);
}
