pub fn run() {
  let input = 652601;
  println!("Part I: {}", solve_part_i(input, vec![3, 7], 0, 1));
  println!("Part II: {}", solve_part_ii(input, vec![3, 7], 0, 1));
}

fn solve_part_i(min_recipes: usize, mut recipes: Vec<u32>, mut e1_pos: usize, mut e2_pos: usize) -> String {
  // println!("{:?}, {}, {}", recipes, e1_pos, e2_pos);
  while recipes.len() < min_recipes + 10 {
    let sum = (recipes[e1_pos] + recipes[e2_pos]).to_string();
    let new_recipes = sum.chars()
      .map(|c| char_to_int(c))
      .collect::<Vec<u32>>();
    recipes.extend(new_recipes.iter().cloned());
    e1_pos = (e1_pos + 1 + recipes[e1_pos] as usize) % recipes.len();
    e2_pos = (e2_pos + 1 + recipes[e2_pos] as usize) % recipes.len();
  }
  return recipes.iter()
      .skip(min_recipes)
      .take(10)
      .map(|n| std::char::from_digit(*n, 10).unwrap())
      .collect::<String>();
}

fn solve_part_ii(min_recipes: usize, mut recipes: Vec<u32>, mut e1_pos: usize, mut e2_pos: usize) -> String {
  while recipes.len() < 50000000 {
    let sum = (recipes[e1_pos] + recipes[e2_pos]).to_string();
    let new_recipes = sum.chars()
      .map(|c| char_to_int(c))
      .collect::<Vec<u32>>();
    recipes.extend(new_recipes.iter().cloned());
    e1_pos = (e1_pos + 1 + recipes[e1_pos] as usize) % recipes.len();
    e2_pos = (e2_pos + 1 + recipes[e2_pos] as usize) % recipes.len();
  }
  return recipes.iter()
      .map(|n| std::char::from_digit(*n, 10).unwrap())
      .collect::<String>()
      .find(&min_recipes.to_string())
      .unwrap()
      .to_string();
}

fn char_to_int(c: char) -> u32 {
  return (c as u32) - 48;
}