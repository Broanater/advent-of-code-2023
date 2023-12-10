package Day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Moves());
	}

	public static long Moves() throws IOException
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

		List<Map> startPoints = new ArrayList<>();

		for (Map map : mapInfo.values())
		{
			if (map.key.endsWith("A")) startPoints.add(map);
		}

		List<Integer> lcmNums = new ArrayList<>();

		for (Map map : startPoints)
		{
			int moves = 0;
			boolean atEnd = false;
			int i = 0;
			while(!atEnd)
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
				if (map.key.endsWith("Z")) atEnd = true;
				i++;
			}
			lcmNums.add(moves);
		}

		long moves = lcmNums.get(0);
		for (int i = 1 ; i < lcmNums.size() ; i++)
		{
			moves = LCM(moves, lcmNums.get(i));
		}

		return moves;
	}

	public static class Map
	{
		String key;
		String left;
		String right;

		public Map(String key,String left, String right)
		{
			this.key = key;
			this.left = left;
			this.right = right;
		}
	}

	private static long LCM(long x, long y) {
		long max = Math.max(x, y);
		long min = Math.min(x, y);
		long lcm = max;
		while (lcm % min != 0) {
			lcm += max;
		}
		return lcm;
	}
}