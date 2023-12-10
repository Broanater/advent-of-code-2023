package Day10;

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

		for (int row = 0 ; row < input.size() ; row++)
		{
			for (int column = 0 ; column < map[row].length ; column++)
			{
				Pipe pipe = map[column][row];
				if (!pipe.discovered) map[column][row] = new Pipe(column, row, '.');
			}
		}

		Pipe[][] enlarged = Enlarge(map);
		boolean[][] outside = new boolean[enlarged[0].length][enlarged.length];
		MapOutside(enlarged, outside);


		int area = 0;
		for (int row = 0 ; row < outside.length ; row++)
		{
			for (int column = 0 ; column < outside[0].length ; column++)
			{
				if (!(outside[column][row] && enlarged[column][row].pipe == '.'))
				{
					Pipe pipe = map[(column - 1) / 2][(row - 1) / 2];
					if (pipe.pipe == '.' && column % 2 == 1 && row % 2 == 1) area++;
				}
			}
		}

		return area;
	}

	public static void MapOutside(Pipe[][] map, boolean[][] outside)
	{
		Pipe startingPipe = Pipe.GetPipeAt(map, 0, 0);
		List<Pipe> pipes = new ArrayList<>();
		if (startingPipe != null)
		{
			pipes.add(startingPipe);
			while (!pipes.isEmpty())
			{
				Pipe pipe = pipes.remove(0);
				if (pipe.discovered) continue;
				pipe.discovered = true;
				outside[pipe.column][pipe.row] = true;

				Pipe north = Pipe.GetPipeAt(map, pipe.column, pipe.row - 1);
				Pipe east = Pipe.GetPipeAt(map, pipe.column + 1, pipe.row);
				Pipe south = Pipe.GetPipeAt(map, pipe.column, pipe.row + 1);
				Pipe west = Pipe.GetPipeAt(map, pipe.column - 1, pipe.row);
				if (north != null && north.pipe == '.' && !north.discovered) pipes.add(north);
				if (east != null && east.pipe == '.' && !east.discovered) pipes.add(east);
				if (south != null && south.pipe == '.' && !south.discovered) pipes.add(south);
				if (west != null && west.pipe == '.' && !west.discovered) pipes.add(west);
			}
		}
	}

	public static Pipe[][] Enlarge(Pipe[][] map)
	{
		Pipe[][] newMap = new Pipe[map.length * 2 + 1][map[0].length * 2 + 1];
		for (int row = 0 ; row < map.length ; row++)
		{
			for (int column = 0 ; column < map[row].length ; column++)
			{
				int enlargedRow = row * 2 + 1;
				int enlargedColumn = column * 2 + 1;
				if (map[column][row].pipe != '.')
				{
					newMap[enlargedColumn][enlargedRow] = new Pipe(enlargedColumn, enlargedRow, map[column][row].pipe);
					switch (map[column][row].pipe)
					{
						case '|':
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn, enlargedRow - 1, '|'));
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn, enlargedRow + 1, '|'));
							break;
						case '-':
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn - 1, enlargedRow, '-'));
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn + 1, enlargedRow, '-'));
							break;
						case 'L':
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn, enlargedRow - 1, '|'));
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn + 1, enlargedRow, '-'));
							break;
						case 'J':
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn, enlargedRow - 1, '|'));
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn - 1, enlargedRow, '-'));
							break;
						case '7':
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn, enlargedRow + 1, '|'));
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn - 1, enlargedRow, '-'));
							break;
						case 'F':
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn, enlargedRow + 1, '|'));
							Pipe.SetPipeAt(newMap, new Pipe(enlargedColumn + 1, enlargedRow, '-'));
							break;
					}
				}
			}
		}

		for (int x = 0 ; x < newMap.length ; x++)
		{
			for (int y = 0 ; y < newMap[x].length ; y++)
			{
				if (newMap[x][y] == null) newMap[x][y] = new Pipe(x, y, '.');
			}
		}
		return newMap;
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
				if (pNorth != null && (pNorth.pipe == '|' || pNorth.pipe == '7' || pNorth.pipe == 'F')) this.directions |= 8;
				if (pSouth != null && (pSouth.pipe == '|' || pSouth.pipe == 'L' || pSouth.pipe == 'J')) this.directions |= 2;
				if (pWest != null && (pWest.pipe == '-' || pWest.pipe == 'L' || pWest.pipe == 'F')) this.directions |= 1;
				if (pEast != null && (pEast.pipe == '-' || pEast.pipe == 'J' || pEast.pipe == '7')) this.directions |= 4;
			}
		}

		public static Pipe GetPipeAt(Pipe[][] map, int column, int row)
		{
			if (column >= 0 && row >= 0 && column < map[0].length && row < map.length) return map[column][row];
			return null;
		}

		public static void SetPipeAt(Pipe[][] map, Pipe pipe)
		{
			if (pipe.column >= 0 && pipe.row >= 0 && pipe.column < map.length && pipe.row < map[0].length) map[pipe.column][pipe.row] = pipe;
		}
	}
}