package DayFour;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Part2
{
	public static void main (String[] args) throws IOException
	{
		System.out.println(Sum());
	}

	public static int Sum () throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/dayFour/input.txt"));
		HashMap<Integer, Integer> cardCopies = new HashMap<>();

		for (int cardNum = 0 ; cardNum < input.size() ; cardNum++)
		{
			cardCopies.put(cardNum, 1);
		}

		for (int card = 0 ; card < input.size() ; card++)
		{
			int copyCounter = 0;
			int cardCopyAmount = cardCopies.get(card);
			String line = input.get(card);
			String cutLine = line.substring(line.indexOf(':') + 1);
			String winningCards = cutLine.substring(1, cutLine.indexOf("|") - 1);
			String yourCards = cutLine.substring(cutLine.indexOf("|") + 2);
			String[] winningNumbers = winningCards.split(" ");
			String[] yourNumbers = yourCards.split(" ");
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
						copyCounter++;
						cardCopies.put(card + copyCounter, cardCopies.get(card + copyCounter) + (cardCopyAmount));
					}
				}
			}
		}


		int cards = 0;
		for (int copies : cardCopies.values())
		{
			cards += copies;
		}

		return cards;
	}
}