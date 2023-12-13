package Day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day13/input.txt"));
		System.out.println(GetPatterns(input).stream().mapToInt(p -> 100 * GetNrBeforeSmudgeReflection(p, false) + GetNrBeforeSmudgeReflection(p, true)).sum());
	}

	private static List<List<String>> GetPatterns(List<String> input)
	{
		List<List<String>> patterns = new ArrayList<>();
		List<String> pattern = new ArrayList<>();
		for (String line : input)
		{
			if (line.isBlank())
			{
				patterns.add(pattern);
				pattern = new ArrayList<>();
			}
			else pattern.add(line);
		}
		patterns.add(pattern);
		return patterns;
	}

	private static String GetColumn(int x, List<String> pattern)
	{
		StringBuilder sb = new StringBuilder();
		for (String line : pattern) sb.append(line.charAt(x));
		return sb.toString();
	}

	private static int GetNrSmudges(String s1, String s2)
	{
		int total = 0;
		for (int i = 0 ; i < s1.length() ; i++)
		{
			if (s1.charAt(i) != s2.charAt(i)) total++;
		}
		return total;
	}

	private static int GetNrBeforeSmudgeReflection(List<String> pattern, boolean columns)
	{
		int max = columns ? pattern.get(0).length() : pattern.size();
		for (int i = 1 ; i < max ; i++)
		{
			String prev = columns ? GetColumn(i - 1, pattern) : pattern.get(i - 1);
			String current = columns ? GetColumn(i, pattern) : pattern.get(i);
			int initialSmudges = GetNrSmudges(prev, current);
			if (initialSmudges == 0 || initialSmudges == 1)
			{
				int elementsToCheck = Math.min(i - 1, max - i - 1);
				boolean matches = true;
				boolean smudgeFound = false;
				for (int el = 0 ; el < elementsToCheck ; el++)
				{
					String first = columns ? GetColumn(i - el - 2, pattern) : pattern.get(i - el - 2);
					String second = columns ? GetColumn(i + el + 1, pattern) : pattern.get(i + el + 1);
					int nrSmudges = GetNrSmudges(first, second);
					if (nrSmudges > 1 || (initialSmudges == 1 && nrSmudges == 1) || (nrSmudges == 1 && smudgeFound))
					{
						matches = false;
						break;
					}
					else if (nrSmudges == 1) smudgeFound = true;
				}
				if (matches && (initialSmudges == 1 ^ smudgeFound)) return i;
			}
		}
		return 0;
	}
}