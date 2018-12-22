use std::collections::HashMap;
// Sample input
// const DEPTH: u64 = 510;
// const TARGET: (u64, u64) = (10, 10);
// Actual input
const DEPTH: u64 = 10914;
const TARGET: (u64, u64) = (9, 739);
const MODULO: u64 = 20183;

pub fn run() {
  let mut memo: HashMap<(u64, u64), u64> = HashMap::new();
  let mut total_risk = 0;
  for y in 0..=TARGET.1 {
    for x in 0..=TARGET.0 {
      let level = if x == TARGET.0 && y == TARGET.1 {
        0 as u64
      } else {
        level(x, y, &mut memo) % 3
      };
      total_risk += level;
      let terrain = if x == 0 && y == 0 {
        'M'
      } else if x == TARGET.0 && y == TARGET.1 {
        'T'
      } else {
        level_to_s(level)
      };
      print!("{}", terrain);
    }
    println!("")
  }
  println!("\nPart I: {}", total_risk);
}

fn level(x: u64, y: u64, memo: &mut HashMap<(u64, u64), u64>) -> u64 {
  let index = if x == 0 && y == 0 {
    0
  } else {
    if x == 0 && y > 0 {
      y * 48271
    } else if y == 0 && x > 0 {
      x * 16807
    } else {
      let first = match memo.get(&(x - 1, y)) {
        Some(&level) => level,
        None => level(x - 1, y, memo),
      };
      let second = match memo.get(&(x, y - 1)) {
        Some(&level) => level,
        None => level(x, y - 1, memo),
      };
      first * second
    }
  };
  let level = (index + DEPTH) % MODULO;
  memo.insert((x, y), level);
  return level;
}

fn level_to_s(level: u64) -> char {
  return match level {
    0 => '.',
    1 => '=',
    2 => '|',
    _ => panic!("At the disco!")
  }
}