package DaySeven;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		int winnings = Winnings();
		System.out.println(winnings);
		System.out.println(winnings == 245794069);
	}

	public static int Winnings() throws IOException
	{
		int winnings = 0;
		List<String> input = Files.readAllLines(Paths.get("src/data/daySeven/input.txt"));
		String cards = "J23456789TQKA";

		List<Hand> hands = new ArrayList<>();
		for (String line : input)
		{
			int[] counts = new int[cards.length()];
			String[] data = line.split("\\s+");
			int bid = Integer.parseInt(data[1]);
			char[] hand = data[0].toCharArray();
			for (char c : hand)
			{
				counts[cards.indexOf(c)]++;
			}

			int handStrength = DetermineStrength(counts);
			for (int i = 0 ; i < counts[cards.indexOf('J')] ; i ++)
			{
				handStrength = ApplyWildcard(handStrength);
			}

			hands.add(new Hand(handStrength, data[0], bid));
		}

		hands.sort((a, b) -> {
			int res = a.strength - b.strength;
			if (res != 0) return res;
			for (int i = 0 ; i < 5 ; i++)
			{
				char aCard = a.cards.toCharArray()[i];
				char bCard = b.cards.toCharArray()[i];
				if (aCard == bCard) continue;
				return cards.indexOf(aCard) - cards.indexOf(bCard);
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

	private static int DetermineStrength(int[] counts)
	{
		int pairs = 0;
		int sets = 0;
		for (int match : counts)
		{
			// 5 of a kind
			if (match == 5) return 6;
			// 4 of a kind
			if (match == 4) return 5;
			if (match == 3) sets++;
			if (match == 2) pairs++;
		}

		// Full house
		if (pairs == 1 && sets == 1) return 4;
		// 3 of a kind
		if (sets == 1) return 3;
		// 2 pairs
		if (pairs == 2) return 2;
		// 1 pair
		if (pairs == 1) return 1;

		return 0;
	}

	private static int ApplyWildcard(int strength)
	{
		return switch (strength)
		{
			case 0 -> 1;
			case 1 -> 3;
			case 2 -> 4;
			case 3, 4 -> 5;
			case 5 -> 6;
			default -> strength;
		};
	}
}