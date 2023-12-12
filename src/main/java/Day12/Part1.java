package Day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Part1
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(HotSpringMap());
	}

	private static long HotSpringMap() throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day12/input.txt"));

		long sum = 0;
		for (String line : input)
		{
			String map = line.substring(0, line.indexOf(" "));
			List<Integer> groups = Arrays.stream(line.substring(line.indexOf(" ") + 1).split(",")).map(Integer :: valueOf).toList();
			sum += CountArrangements(map, groups);
		}

		return sum;
	}

	private static long CountArrangements(String map, List<Integer> groups)
	{
		if (map.isEmpty()) return groups.isEmpty() ? 1 : 0;

		long arrangements = 0;
		char firstChar = map.charAt(0);
		if (firstChar == '.') arrangements = CountArrangements(map.substring(1), groups);
		else if (firstChar == '?') arrangements = CountArrangements("." + map.substring(1), groups) + CountArrangements("#" + map.substring(1), groups);
		else
		{
			if (!groups.isEmpty())
			{
				int nrDamaged = groups.get(0);
				if (nrDamaged <= map.length() && map.chars().limit(nrDamaged).allMatch(c -> c == '#' || c == '?')) {
					List<Integer> newGroups = groups.subList(1, groups.size());
					if (nrDamaged == map.length()) arrangements = newGroups.isEmpty() ? 1 : 0;
					else if (map.charAt(nrDamaged) == '.') arrangements = CountArrangements(map.substring(nrDamaged + 1), newGroups);
					else if (map.charAt(nrDamaged) == '?') arrangements = CountArrangements("." + map.substring(nrDamaged + 1), newGroups);
				}
			}
		}

		return arrangements;
	}
}