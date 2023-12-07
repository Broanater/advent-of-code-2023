package DaySeven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Winnings());
	}

	public static int Winnings() throws IOException
	{
		int winnings = 0;
		List<String> input = Files.readAllLines(Paths.get("src/data/daySeven/input.txt"));
		HashMap<Character, Integer> cards = new HashMap<>();
		cards.put('A', 13);
		cards.put('K', 12);
		cards.put('Q', 11);
		cards.put('J', 10);
		cards.put('T', 9);
		cards.put('9', 8);
		cards.put('8', 7);
		cards.put('7', 6);
		cards.put('6', 5);
		cards.put('5', 4);
		cards.put('4', 3);
		cards.put('3', 2);
		cards.put('2', 1);

		List<Hand> hands = new ArrayList<>();
		for (String line : input)
		{
			List<Integer> matches = new ArrayList<>();
			String[] data = line.split("\\s+");
			int bid = Integer.parseInt(data[1]);
			char[] hand = data[0].toCharArray();
			for (int i = 0 ; i < hand.length ; i++)
			{
				char checkChar = hand[i];
				if (checkChar == '~') { continue; }
				int checkCount = 1;
				for (int j = i + 1 ; j < hand.length ; j++)
				{
					if (checkChar == hand[j])
					{
						checkCount++;
						hand[j] = '~';
					}
				}

				if (checkCount >= 2)
				{
					matches.add(checkCount);
				}
			}

			// Determine card strength
			matches.sort(Comparator.comparingInt(a -> a));
			Collections.reverse(matches);
			int handStrength = 0;
			if (!matches.isEmpty())
			{
				int matchNum = matches.get(0);
				switch (matchNum)
				{
					case 5:
						handStrength = 6;
						break;
					case 4:
						handStrength = 5;
						break;
					case 3:
						if (matches.size() >= 2)
						{
							int matchNum2 = matches.get(1);
							if (matchNum2 == 2)
							{
								handStrength = 4;
								break;
							}
						}
						handStrength = 3;
						break;
					case 2:
						if (matches.size() >= 2)
						{
							if (matches.get(1) == 2)
							{
								handStrength = 2;
								break;
							}
						}
						handStrength = 1;
						break;
				}
			}

			hands.add(new Hand(handStrength, data[0], bid));
		}

		hands.sort((a, b) -> {
			int res = a.strength - b.strength;
			if (res != 0)
			{
				return res;
			}
			for (int i = 0 ; i < a.cards.length() ; i++)
			{
				char[] aCards = a.cards.toCharArray();
				char[] bCards = b.cards.toCharArray();
				char aCard = aCards[i];
				char bCard = bCards[i];
				if (aCard == bCard)
					continue;
				return cards.get(aCard) - cards.get(bCard);
			}
			return a.cards.compareTo(b.cards);
		});

		for (int i = 0 ; i < hands.size() ; i++)
		{
			Hand hand = hands.get(i);
			int rank = i + 1;
			winnings += (hand.bid * rank);
		}

		return winnings;
	}

	public static class Hand
	{
		/*
			0 : High card
			1 : 1 pair
			2 : 2 pair
			3 : 3 of a kind
			4 : Full house
			5 : 4 of a kind
			6 : 5 of a kind
		 */
		int strength;
		String cards;
		int bid;

		public Hand(int strength, String cards, int bid)
		{
			this.strength = strength;
			this.cards = cards;
			this.bid = bid;
		}
	}
}