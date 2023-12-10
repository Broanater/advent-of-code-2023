package Day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Races());
	}

	private static int Races() throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day6/input.txt"));

		String[] times = input.get(0).split("\\s+");
		String[] distances = input.get(1).split("\\s+");

		StringBuilder totalTimeStr = new StringBuilder();
		StringBuilder totalDistanceStr = new StringBuilder();
		for (int counter = 0 ; counter < times.length ; counter++)
		{
			String timeStr = times[counter];
			String distanceStr = distances[counter];
			if (timeStr.contains(":")) continue;
			totalTimeStr.append(timeStr);
			totalDistanceStr.append(distanceStr);
		}

		long totalTime = Long.parseLong(totalTimeStr.toString());
		long totalDistance = Long.parseLong(totalDistanceStr.toString());
		int wins = 0;
		for (int millisecs = 0 ; millisecs < totalTime ; millisecs++)
		{
			long runTime = totalTime - millisecs;
			long travelDistance = millisecs * runTime;
			if (travelDistance >= totalDistance) wins++;
		}

		return wins;
	}
}