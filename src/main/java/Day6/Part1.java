package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Races());
	}

	private static int Races() throws IOException
	{
		int answer = 1;
		List<String> input = Files.readAllLines(Paths.get("src/data/day6/input.txt"));

		String[] times = input.get(0).split("\\s+");
		String[] distances = input.get(1).split("\\s+");

		for (int counter = 0 ; counter < times.length ; counter++)
		{
			String timeStr = times[counter];
			String distanceStr = distances[counter];
			if (timeStr.contains(":")) continue;
			int time = Integer.parseInt(timeStr);
			int distance = Integer.parseInt(distanceStr);
			int wins = 0;
			for (int millisecs = 0 ; millisecs < time ; millisecs++)
			{
				int runTime = time - millisecs;
				int travelDistance = millisecs * runTime;
				if (travelDistance > distance) wins++;
			}

			answer *= wins;
		}

		return answer;
	}
}