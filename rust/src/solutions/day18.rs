use crate::utils::read_file_as_string;

struct Change {
  x: usize,
  y: usize,
  val: char,
}

pub fn run() {
  let input = read_file_as_string("18.txt");
  let mut lines = input.split("\n")
    .into_iter()
    .filter(|&s| !s.is_empty())
    .map(|s| s.chars().collect())
    .collect::<Vec<Vec<char>>>();
  for _i in 0..10 {
    let mut changes: Vec<Change> = vec![];
    for y in 0..lines.len() {
      let chars: &Vec<char> = &lines[y];
      for x in 0..chars.len() {
        let adjacent = adjacent_acres_to(x, y, &lines);
        let c = chars[x];
        let new_val = match c {
          '.' => if count_matches(&adjacent, '|') >= 3 { '|' } else { '.' },
          '#' => if count_matches(&adjacent, '#') > 0 && count_matches(&adjacent, '|') > 0 { '#' } else { '.' },
          '|' => if count_matches(&adjacent, '#') >= 3 { '#' } else { '|' },
          _ => panic!("Invalid input {:?}", c)
        };
        changes.push(Change { x: x, y: y, val: new_val });
      }
    }
    for c in changes.iter() {
      lines[c.y][c.x] = c.val;
    }
  }
  let mut total_wood = 0;
  let mut total_lumber = 0;
  for line in &lines {
    total_wood += count_matches(&line, '|');
    total_lumber += count_matches(&line, '#');
  }
  println!("Part I: {}", total_wood * total_lumber);
}

fn count_matches(arr: &Vec<char>, chr: char) -> usize {
  return arr.iter().filter(|&s| *s == chr).count();
}

fn adjacent_acres_to<'a>(x: usize, y: usize, lines: &'a Vec<Vec<char>>) -> Vec<char> {
  let mut ret: Vec<char> = vec![];
  let y0 = (y as i32 - 1).max(0) as usize;
  let y1 = (lines.len() - 1).min(y + 1);
  let x0 = (x as i32 - 1).max(0) as usize;
  let x1 = (lines[0].len() - 1).min(x + 1);
  // println!("{}..{}, {}..{}", y0, y1, x0, x1);
  for _y in y0..=y1 {
    let chars: &Vec<char> = &lines[_y];
    for _x in x0..=x1 {
      if _x == x && _y == y {
        continue;
      } else {
        ret.push(chars[_x]);
      }
    }
  }
  return ret;
}
