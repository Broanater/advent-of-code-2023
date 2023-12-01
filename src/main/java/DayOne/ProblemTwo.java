package DayOne;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProblemTwo
{
	public static int Sum ()
	{
		int total = 0;
		try {
			for (int num : GetNumbers()) {
				total += num;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	private static ArrayList<Integer> GetNumbers () throws IOException
	{
		ArrayList<Integer> numbers = new ArrayList<>();
		List<String> fileLines = Files.readAllLines(Paths.get("src/data/dayOne/input.txt"));
		for (String line : fileLines) {
			ArrayList<Integer> numberIndexes = new ArrayList<>();
			String[] searchNumbers = {
					"one",
					"two",
					"three",
					"four",
					"five",
					"six",
					"seven",
					"eight",
					"nine"
			};
			for (String searchNumStr : searchNumbers) {
				String searchStr = line;
				int cutIndexes = 0;
				while (true) {
					if (searchStr.contains(searchNumStr)) {
						numberIndexes.add(searchStr.indexOf(searchNumStr) + cutIndexes);
						int substringTotal = searchStr.indexOf(searchNumStr) + searchNumStr.length();
						cutIndexes += substringTotal;
						searchStr = searchStr.substring(substringTotal);
					}
					else
					{
						break;
					}
				}
			}

			char[] chars = line.toCharArray();
			for (int i = 0 ; i < chars.length ; i++) {
				try {
					Integer.parseInt(String.valueOf(chars[i]));
					numberIndexes.add(i);
				}
				catch (Exception ignored) { }
			}

			int smallestIndex = 999999999;
			int largestIndex = 0;
			for (int index : numberIndexes) {
				if (index < smallestIndex) { smallestIndex = index; }
				if (index > largestIndex) { largestIndex = index;  }
			}

			int firstNum = 0;
			try {
				firstNum = Integer.parseInt(String.valueOf(chars[smallestIndex]));
			}
			catch (Exception e) {
				String newLine = line.substring(smallestIndex);
				if (newLine.startsWith("one")) { firstNum = 1; }
				if (newLine.startsWith("two")) { firstNum = 2; }
				if (newLine.startsWith("three")) { firstNum = 3; }
				if (newLine.startsWith("four")) { firstNum = 4; }
				if (newLine.startsWith("five")) { firstNum = 5; }
				if (newLine.startsWith("six")) { firstNum = 6; }
				if (newLine.startsWith("seven")) { firstNum = 7; }
				if (newLine.startsWith("eight")) { firstNum = 8; }
				if (newLine.startsWith("nine")) { firstNum = 9; }
			}

			int lastNum = 0;
			try {
				lastNum = Integer.parseInt(String.valueOf(chars[largestIndex]));
			}
			catch (Exception e) {
				String newLine = line.substring(largestIndex);
				if (newLine.startsWith("one")) { lastNum = 1; }
				if (newLine.startsWith("two")) { lastNum = 2; }
				if (newLine.startsWith("three")) { lastNum = 3; }
				if (newLine.startsWith("four")) { lastNum = 4; }
				if (newLine.startsWith("four")) { lastNum = 4; }
				if (newLine.startsWith("five")) { lastNum = 5; }
				if (newLine.startsWith("six")) { lastNum = 6; }
				if (newLine.startsWith("seven")) { lastNum = 7; }
				if (newLine.startsWith("eight")) { lastNum = 8; }
				if (newLine.startsWith("nine")) { lastNum = 9; }
			}

			String numStr = String.valueOf(firstNum) + String.valueOf(lastNum);
			numbers.add(Integer.parseInt(numStr));
		}

		return numbers;
	}
}