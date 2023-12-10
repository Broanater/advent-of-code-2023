package Day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Path());
	}

	private static long Path() throws IOException
	{

		List<String> input = Files.readAllLines(Paths.get("src/data/day10/input.txt"));
		Pipe[][] map = new Pipe[input.get(0).length()][input.size()];

		int startingRow = 0;
		int startingColumn = 0;
		for (int row = 0 ; row < input.size() ; row++)
		{
			String line = input.get(row);
			for (int column = 0 ; column < line.length() ; column++)
			{
				char pipe = line.charAt(column);
				if (pipe == 'S')
				{
					startingColumn = column;
					startingRow = row;
				}
				map[column][row] = new Pipe(column, row, pipe);
			}
		}

		map[startingColumn][startingRow].SetStartingConnections(map);
		Walk(map, startingColumn, startingRow);
		int maxDistance = 0;
		for (int row = 0 ; row < map.length ; row++)
		{
			for (int column = 0 ; column < map[row].length ; column++)
			{
				Pipe pipe = map[column][row];
				if (pipe.pipe != '.' && pipe.distance > maxDistance) maxDistance = pipe.distance;
			}
		}

		return maxDistance;
	}

	public static void Walk(Pipe[][] map, int startingColumn, int startingRow)
	{
		Pipe start = Pipe.GetPipeAt(map, startingColumn, startingRow);
		List<Pipe> pipes = new ArrayList<>();
		pipes.add(start);
		while (!pipes.isEmpty())
		{
			Pipe pipe = pipes.remove(0);
			if (pipe.discovered) continue;
			pipe.discovered = true;

			Pipe north = Pipe.GetPipeAt(map, pipe.column, pipe.row - 1);
			Pipe east = Pipe.GetPipeAt(map, pipe.column + 1, pipe.row);
			Pipe south = Pipe.GetPipeAt(map, pipe.column, pipe.row + 1);
			Pipe west = Pipe.GetPipeAt(map, pipe.column - 1, pipe.row);
			if (north != null && (pipe.directions & 8) == 8 && !north.discovered)
			{
				north.distance = pipe.distance + 1;
				pipes.add(north);
			}
			if (east != null && (pipe.directions & 4) == 4 && !east.discovered)
			{
				east.distance = pipe.distance + 1;
				pipes.add(east);
			}
			if (south != null && (pipe.directions & 2) == 2 && !south.discovered)
			{
				south.distance = pipe.distance + 1;
				pipes.add(south);
			}
			if (west != null && (pipe.directions & 1) == 1 && !west.discovered)
			{
				west.distance = pipe.distance + 1;
				pipes.add(west);
			}
		}
	}

	public static class Pipe
	{
		int column, row;
		char pipe;
		int distance = 0;
		int directions;
		boolean discovered;

		public Pipe(int column, int row, char pipe)
		{
			this.column = column;
			this.row = row;
			this.pipe = pipe;
			this.directions = switch (this.pipe)
			{
				case '|' -> 10;
				case '-' -> 5;
				case 'L' -> 12;
				case 'J' -> 9;
				case '7' -> 3;
				case 'F' -> 6;
				default -> this.directions;
			};
		}

		public void SetStartingConnections(Pipe[][] map)
		{
			if (pipe == 'S')
			{
				Pipe pNorth = GetPipeAt(map, column, row - 1);
				Pipe pSouth = GetPipeAt(map, column, row + 1);
				Pipe pWest = GetPipeAt(map, column - 1, row);
				Pipe pEast = GetPipeAt(map, column + 1, row);
				if (pNorth != null && (pNorth.pipe == '|' || pNorth.pipe == '7' || pNorth.pipe == 'F'))
					this.directions |= 8;
				if (pSouth != null && (pSouth.pipe == '|' || pSouth.pipe == 'L' || pSouth.pipe == 'J'))
					this.directions |= 2;
				if (pWest != null && (pWest.pipe == '-' || pWest.pipe == 'L' || pWest.pipe == 'F'))
					this.directions |= 1;
				if (pEast != null && (pEast.pipe == '-' || pEast.pipe == 'J' || pEast.pipe == '7'))
					this.directions |= 4;
			}
		}

		public static Pipe GetPipeAt(Pipe[][] map, int column, int row)
		{
			if (column >= 0 && row >= 0 && column < map[0].length && row < map.length) return map[column][row];
			return null;
		}
	}
}