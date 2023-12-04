package DayTwo;

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

	private static ArrayList<Integer> GetNumbers () throws IOException
	{
		ArrayList<Integer> ids = new ArrayList<>();
		List<String> fileLines = Files.readAllLines(Paths.get("src/data/dayTwo/input.txt"));
		for (String line : fileLines)
		{
			int gameId = Integer.parseInt(line.substring(4, line.indexOf(":")).trim());
			String gameRecords = line.substring(line.indexOf(":") + 1);
			String[] sets = gameRecords.split(";");
			boolean gameAllowed = false;
			for (String set : sets)
			{
				String[] cubes = set.split(",");
				boolean redAllowed = true;
				boolean greenAllowed = true;
				boolean blueAllowed = true;
				for (String cube : cubes)
				{
					cube = cube.trim();
					int cubeCount = Integer.parseInt(cube.substring(0, cube.indexOf(" ")).trim());
					String colour = cube.substring(cube.indexOf(" ") + 1);
					switch (colour)
					{
						case "red":
							if (cubeCount > 12) { redAllowed = false; }
							break;
						case "green":
							if (cubeCount > 13) { greenAllowed = false; }
							break;
						case "blue":
							if (cubeCount > 14) { blueAllowed = false; }
							break;
					}
				}
				if (redAllowed && greenAllowed && blueAllowed) { gameAllowed = true; }
				else
				{
					gameAllowed = false;
					break;
				}
			}
			if (gameAllowed) { ids.add(gameId); }
		}

		return ids;
	}
}