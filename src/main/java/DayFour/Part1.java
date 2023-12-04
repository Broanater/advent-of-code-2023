package DayFour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1
{
	public static void main (String[] args) throws IOException
	{
		System.out.println(Sum());
	}

	public static int Sum() throws IOException
	{
		int answer = 0;
		List<String> input = Files.readAllLines(Paths.get("src/data/dayFour/input.txt"));

		for (String line : input)
		{
			String cutLine = line.substring(line.indexOf(':') + 1);
			String winningCards = cutLine.substring(1, cutLine.indexOf("|") - 1);
			String yourCards = cutLine.substring(cutLine.indexOf("|") + 2);
			String[] winningNumbers = winningCards.split(" ");
			String[] yourNumbers = yourCards.split(" ");
			int points = 0;
			for (String numStr : yourNumbers)
			{
				if (numStr.isEmpty()) { continue; }
				int num = Integer.parseInt(numStr.trim());
				for (String winningNumStr : winningNumbers)
				{
					if (winningNumStr.isEmpty()) { continue; }
					int winningNum = Integer.parseInt(winningNumStr.trim());
					if (num == winningNum)
					{
						if (points == 0) { points++; continue; }
						points *= 2;
					}
				}
			}
			answer += points;
		}

		return answer;
	}
}