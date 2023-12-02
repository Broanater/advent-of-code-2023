package DayTwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2
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

	private static ArrayList<Integer> GetNumbers() throws IOException
	{
		ArrayList<Integer> powerSums = new ArrayList<>();
		List<String> fileLines = Files.readAllLines(Paths.get("src/data/dayTwo/input.txt"));
		for (String line : fileLines)
		{
			String gameRecords = line.substring(line.indexOf(":") + 1);
			String[] sets = gameRecords.split(";");
			int lowestPossibleRed = 0;
			int lowestPossibleGreen = 0;
			int lowestPossibleBlue = 0;
			for (String set : sets)
			{
				String[] cubes = set.split(",");
				for (String cube : cubes)
				{
					cube = cube.trim();
					int cubeCount = Integer.parseInt(cube.substring(0, cube.indexOf(" ")).trim());
					String colour = cube.substring(cube.indexOf(" ") + 1);
					switch (colour)
					{
						case "red":
							if (cubeCount > lowestPossibleRed) { lowestPossibleRed = cubeCount; }
							break;
						case "green":
							if (cubeCount > lowestPossibleGreen) { lowestPossibleGreen = cubeCount; }
							break;
						case "blue":
							if (cubeCount > lowestPossibleBlue) { lowestPossibleBlue = cubeCount; }
							break;
					}
				}
			}

			int powerSum = lowestPossibleRed * lowestPossibleGreen * lowestPossibleBlue;

			powerSums.add(powerSum);
		}

		return powerSums;
	}
}