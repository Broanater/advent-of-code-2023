package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Part1
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Moves());
	}

	public static int Moves() throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day8/input.txt"));

		String instructions = input.get(0);

		HashMap<String, Map> mapInfo = new HashMap<>();
		for (int i = 2 ; i < input.size() ; i++)
		{
			String line = input.get(i);
			String key = line.substring(0, line.indexOf("=")).trim();
			String leftAndRight = line.substring(line.indexOf("=") + 1).trim().replace("(", "").replace(")", "");
			String[] nums = leftAndRight.split(",");
			String left = nums[0].trim();
			String right = nums[1].trim();
			mapInfo.put(key, new Map(key, left, right));
		}

		Map map = mapInfo.get("AAA");

		int moves = 0;
		boolean reachedEnd = false;
		int i = 0;
		while (!reachedEnd)
		{
			if (i >= instructions.length()) i = 0;
			moves++;
			char direction = instructions.charAt(i);
			map = switch (direction)
			{
				case 'L' -> mapInfo.get(map.left);
				case 'R' -> mapInfo.get(map.right);
				default -> map;
			};
			if (map.key.equals("ZZZ")) reachedEnd = true;
			i++;
		}

		return moves;
	}

	public static class Map
	{
		String key;
		String left;
		String right;

		public Map(String key, String left, String right)
		{
			this.key = key;
			this.left = left;
			this.right = right;
		}
	}
}