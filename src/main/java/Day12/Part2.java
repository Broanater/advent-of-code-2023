package Day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Part2
{
	private static final HashMap<Line, Long> memory = new HashMap<>();

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
			sum += CountArrangements(UnfoldMap(map), UnfoldGroups(groups));
		}

		return sum;
	}

	private static long CountArrangements(String map, List<Integer> groups)
	{
		Line input = new Line(map, groups);
		if (memory.containsKey(input)) return memory.get(input);
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

		memory.put(input, arrangements);
		return arrangements;
	}

	private static String UnfoldMap(String map)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0 ; i < 4 ; i++)
		{
			sb.append(map);
			sb.append("?");
		}
		sb.append(map);
		return sb.toString();
	}

	private static List<Integer> UnfoldGroups(List<Integer> groups)
	{
		List<Integer> unfoldedGroups = new ArrayList<>();
		for (int i = 0 ; i < 5 ; i++) unfoldedGroups.addAll(groups);
		return unfoldedGroups;
	}

	private record Line(String map, List<Integer> groups) { }
}