use std::collections::HashMap;
// Sample input
// const DEPTH: u64 = 510;
// const TARGET: (u64, u64) = (10, 10);
// Actual input
const DEPTH: u64 = 10914;
const TARGET: (u64, u64) = (9, 739);
const MODULO: u64 = 20183;

pub fn run() {
  // println!("\nPart I: {}", solve_part_i());
}

// fn solve_part_i() -> u64 {
//   let mut memo: HashMap<(u64, u64), u64> = HashMap::new();
//   let mut total_risk = 0;
//   for y in 0..=TARGET.1 {
//     for x in 0..=TARGET.0 {
//       let level = if x == TARGET.0 && y == TARGET.1 {
//         0 as u64
//       } else {
//         level(x, y, &mut memo) % 3
//       };
//       total_risk += level;
//       let terrain = if x == 0 && y == 0 {
//         'M'
//       } else if x == TARGET.0 && y == TARGET.1 {
//         'T'
//       } else {
//         level_to_s(level)
//       };
//       print!("{}", terrain);
//     }
//     println!("")
//   }
//   return total_risk;
// }

// fn solve_part_ii() -> i64 {
//   let region_to_valid_gear: HashMap<u64, Vec<u64>> = [
//     (0, vec![0, 1]),
//     (1, vec![1, 2]),
//     (2, vec![0, 2])
//   ].iter().cloned().collect();
//   let source = (0, 0);
//   let mut queue = vec![];
//   let mut distances: HashMap<(u64, u64), u64> = HashMap::new();
//   distances[source] = 0;
//   return 0;
// }

// // returns a vector of distances from (x, y) to its neighbors
// fn neighbor_distances(x: u64, y: u64) -> Vec<(u64, u64, u32)> {
//   let mut ret: Vec<(u64, u64, u32)> = Vec::new();
//   let this_level = level(x, y);
//   if y > 0 {
//     let level_top = level(x, y - 1);
//     // ret.push((x, y - 1), distances_to(this_level, level_top);
//   }
//   let right = level(x + 1, y);
//   let bottom = level(x, y + 1);
//   if x > 0 {
//     let level_left = level(x - 1, y);
//     // ret.push((x - 1, y), distances_to(this_level, level_top));
//   }
//   return ret;
// }

// // Returns a vector of possible (distance, new_gear) pairs for moving to next_region
// // using the current_gear.
// fn distances_to(
//     current_gear: u64,
//     next_region: u64,
//     region_to_valid_gear: HashMap<u64, Vec<u64>>
// ) -> Vec<(u64, u64)> {
//   let valid_gear_for_next_region: &Vec<u64> = region_to_valid_gear.get(&next_region).unwrap();
//   return if valid_gear_for_next_region.contains(&current_gear) {
//     let other_valid_gear: Vec<u64> = valid_gear_for_next_region.iter()
//       .filter(|&x| x == &current_gear)
//       .map(|&x| x)
//       .collect();
//     vec![(1, current_gear), (7, *other_valid_gear.first().unwrap())]
//   } else {
//     // need to switch gears
//     vec![]
//   }
// }

// fn level(x: u64, y: u64, memo: &mut HashMap<(u64, u64), u64>) -> u64 {
//   let index = if x == 0 && y == 0 {
//     0
//   } else {
//     if x == 0 && y > 0 {
//       y * 48271
//     } else if y == 0 && x > 0 {
//       x * 16807
//     } else {
//       let first = match memo.get(&(x - 1, y)) {
//         Some(&level) => level,
//         None => level(x - 1, y, memo),
//       };
//       let second = match memo.get(&(x, y - 1)) {
//         Some(&level) => level,
//         None => level(x, y - 1, memo),
//       };
//       first * second
//     }
//   };
//   let level = (index + DEPTH) % MODULO;
//   memo.insert((x, y), level);
//   return level;
// }

// fn level_to_s(level: u64) -> char {
//   return match level {
//     0 => '.',
//     1 => '=',
//     2 => '|',
//     _ => panic!("At the disco!")
//   }
// }