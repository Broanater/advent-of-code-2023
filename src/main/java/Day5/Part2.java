package Day5;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		BigDecimal answer = BigDecimal.valueOf(Seeds());
		System.out.println(answer);
	}

	public static double Seeds() throws IOException
	{
		List<String> input = Files.readAllLines(Paths.get("src/data/day5/input.txt"));
		double lowestLocation = Double.MAX_VALUE;

		List<Range> seedRanges = new ArrayList<>();
		String seedsStr = input.get(0).substring(input.get(0).indexOf(":") + 1).trim();
		String[] seedsStrArr = seedsStr.split(" ");
		for (int i = 0 ; i < seedsStrArr.length ; i++)
		{
			double seed = Double.parseDouble(seedsStrArr[i]);
			i++;
			double range = Double.parseDouble(seedsStrArr[i]);
			seedRanges.add(new Range(seed, seed + range));
		}

		for (Range seedRange : seedRanges)
		{
			for (double seed = seedRange.min ; seed < seedRange.max ; seed++)
			{
				// Seed to soil
				double soil = -1;
				for (int counter = input.indexOf("seed-to-soil map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (seed >= mapNum && seed < (mapNum + range))
					{
						double dif = seed - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						soil = startNum + dif;
					}
				}
				if (soil == -1) soil = seed;

				// Soil to fertilizer
				double fertilizer = -1;
				for (int counter = input.indexOf("soil-to-fertilizer map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (soil >= mapNum && soil < (mapNum + range))
					{
						double dif = soil - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						fertilizer = startNum + dif;
					}
				}
				if (fertilizer == -1) fertilizer = soil;

				// Fertilizer to water
				double water = -1;
				for (int counter = input.indexOf("fertilizer-to-water map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (fertilizer >= mapNum && fertilizer < (mapNum + range))
					{
						double dif = fertilizer - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						water = startNum + dif;
					}
				}
				if (water == -1) water = fertilizer;

				// Water to light
				double light = -1;
				for (int counter = input.indexOf("water-to-light map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (water >= mapNum && water < (mapNum + range))
					{
						double dif = water - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						light = startNum + dif;
					}
				}
				if (light == -1) light = water;

				// Light to temperature
				double temperature = -1;
				for (int counter = input.indexOf("light-to-temperature map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (light >= mapNum && light < (mapNum + range))
					{
						double dif = light - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						temperature = startNum + dif;
					}
				}
				if (temperature == -1) temperature = light;

				// Temperature to humidity
				double humidity = -1;
				for (int counter = input.indexOf("temperature-to-humidity map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (temperature >= mapNum && temperature < (mapNum + range))
					{
						double dif = temperature - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						humidity = startNum + dif;
					}
				}
				if (humidity == -1) humidity = temperature;

				// Humidity to location
				double location = -1;
				for (int counter = input.indexOf("humidity-to-location map:") + 1 ; counter < input.size() ; counter++)
				{
					String line = input.get(counter);
					if (line.isEmpty()) break;
					String[] numbers = line.split(" ");
					double mapNum = Double.parseDouble(numbers[1]);
					double range = Double.parseDouble(numbers[2]);
					if (humidity >= mapNum && humidity < (mapNum + range))
					{
						double dif = humidity - mapNum;
						double startNum = Double.parseDouble(numbers[0]);
						location = startNum + dif;
					}
				}
				if (location == -1) location = humidity;

				lowestLocation = Math.min(lowestLocation, location);
			}
		}


		return lowestLocation;
	}

	public static class Range
	{
		public double min;
		public double max;

		public Range(double min, double max)
		{
			this.min = min;
			this.max = max;
		}
	}
}