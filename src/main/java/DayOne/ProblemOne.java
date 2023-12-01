package DayOne;

import com.google.common.base.CharMatcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProblemOne
{
	public static int Sum()
	{
		int total = 0;
		try {
			for (int num : GetNumbers()) {
				total+=num;
			}
		}catch (Exception e) {
			e.printStackTrace();
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
				numStr = String.valueOf(chars[0]) + String.valueOf(chars[0]);
			}
			else
			{
				numStr = String.valueOf(chars[0]) + String.valueOf(chars[chars.length - 1]);
			}


			numbers.add(Integer.parseInt(numStr));
		}

		return numbers;
	}
}