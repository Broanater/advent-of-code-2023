package Day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Path());
	}

	private static long Path() throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day11/input.txt"));
		List<Galaxy> galaxies = new ArrayList<>();

		for (int row = 0 ; row < input.size() ; row++)
		{
			for (int column = 0 ; column < input.get(0).length() ; column++)
			{
				if (input.get(row).charAt(column) == '#') galaxies.add(new Galaxy(column, row));
			}
		}

		for (int i = input.get(0).length() - 1 ; i >= 0 ; i--)
		{
			var empty = true;
			for (Galaxy galaxy : galaxies)
			{
				if (galaxy.row == i)
				{
					empty = false;
					break;
				}
			}
			if (!empty) continue;
			for (Galaxy galaxy : galaxies)
			{
				if (galaxy.row >= i) galaxy.row += 999999;
			}
		}

		for (int i = input.size() - 1 ; i >= 0 ; i--)
		{
			var empty = true;
			for (Galaxy galaxy : galaxies)
			{
				if (galaxy.column == i)
				{
					empty = false;
					break;
				}
			}
			if (!empty) continue;
			for (Galaxy galaxy : galaxies)
			{
				if (galaxy.column >= i) galaxy.column += 999999;
			}
		}

		long sum = 0;
		for (int i = 0 ; i < galaxies.size() ; i++)
		{
			for (int x = i + 1 ; x < galaxies.size() ; x++)
			{
				Galaxy galaxy1 = galaxies.get(i);
				Galaxy galaxy2 = galaxies.get(x);
				sum += Math.abs(galaxy1.column - galaxy2.column) + Math.abs(galaxy1.row - galaxy2.row);
			}
		}

		return sum;
	}

	private static class Galaxy
	{
		int column;
		int row;

		public Galaxy(int column, int row)
		{
			this.column = column;
			this.row = row;
		}
	}
}