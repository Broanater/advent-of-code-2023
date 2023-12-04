package DayOne;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1
{
	public static void main (String[] args) throws IOException
	{
		System.out.println(Sum());
	}

	public static int Sum () throws IOException
	{
		int total = 0;
		for (int num : GetNumbers())
		{
			total += num;
		}
		return total;
	}

	private static ArrayList<Integer> GetNumbers() throws IOException {
		ArrayList<Integer> numbers = new ArrayList<>();
		List<String> fileLines = Files.readAllLines(Paths.get("src/data/dayOne/input.txt"));

		for (String line : fileLines)
		{
			String numbersStr = CharMatcher.inRange('0', '9').retainFrom(line);
			char[] chars = numbersStr.toCharArray();
			String numStr;
			if (chars.length == 1)
			{
				numStr = chars[0] + String.valueOf(chars[0]);
			}
			else
			{
				numStr = chars[0] + String.valueOf(chars[chars.length - 1]);
			}


			numbers.add(Integer.parseInt(numStr));
		}

		return numbers;
	}
}