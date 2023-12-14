package Day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Part1
{
	public static void main(String[] args) throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day14/input.txt"));
		Instant start = Instant.now();
		long result = GetLoad(input);
		Instant end = Instant.now();
		System.out.println("Part 1: " + result);
		System.out.println("Time: " + Duration.between(start, end).toMillis() + "ms");
	}

	private static long GetLoad(List<String> input)
	{
		long load = 0;

		char[][] map = new char[input.get(0).length()][input.size()];
		PopulateRocks(input, map);
		TiltNorth(map, input.size(), input.get(0).length());

		for (int row = 0 ; row < input.size() ; row++)
		{
			for (int column = 0 ; column < input.get(row).length() ; column++)
			{
				if (map[column][row] == 'O') load += input.size() - row;
			}
		}

		return load;
	}

	private static void PopulateRocks(List<String> input, char[][] map)
	{
		for (int row = 0 ; row < input.size() ; row++)
		{
			for (int column = 0 ; column < input.get(row).length() ; column++)
			{
				map[column][row] = input.get(row).charAt(column);
			}
		}
	}

	private static void TiltNorth(char[][] map, int rows, int columns)
	{
		for (int row = 0 ; row < rows ; row++)
		{
			for (int column = 0 ; column < columns ; column++)
			{
				if (map[column][row] == 'O')
				{
					int newRow = row;
					while (newRow - 1 >= 0)
					{
						if (map[column][newRow - 1] == '#' || map[column][newRow - 1] == 'O') break;
						map[column][newRow - 1] = 'O';
						map[column][newRow] = '.';
						newRow--;
					}

				}
			}
		}
	}
}