package DayNine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2
{
	public static void main (String[] args) throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/dayNine/input.txt"));
		System.out.println(GetSequences(input).stream().mapToInt(Part2 :: GetPreviousNumber).sum());
	}

	private static List<List<Integer>> GetSequences (List<String> input)
	{
		List<List<Integer>> sequences = new ArrayList<>();
		Pattern nrPattern = Pattern.compile("-?\\d+");

		for (String line : input)
		{
			List<Integer> sequence = new ArrayList<>();
			Matcher matcher = nrPattern.matcher(line);
			while (matcher.find())
			{
				sequence.add(Integer.valueOf(matcher.group()));
			}
			sequences.add(sequence);
		}
		return sequences;
	}

	private static List<Integer> GetDifferences (List<Integer> sequence)
	{
		List<Integer> differences = new ArrayList<>();
		for (int i = 0 ; i < sequence.size() - 1 ; i++)
		{
			differences.add(sequence.get(i + 1) - sequence.get(i));
		}
		return differences;
	}

	private static int GetNextNumber (List<Integer> sequence)
	{
		List<Integer> differences = GetDifferences(sequence);
		if (differences.stream().anyMatch(num -> num != 0))
		{
			return sequence.get(sequence.size() - 1) + GetNextNumber(differences);
		}
		else
		{
			return sequence.get(sequence.size() - 1);
		}
	}

	private static int GetPreviousNumber (List<Integer> sequence)
	{
		Collections.reverse(sequence);
		return GetNextNumber(sequence);
	}
}