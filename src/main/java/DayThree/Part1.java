package DayThree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1
{
	public static void main (String[] args) throws IOException
	{
		System.out.println(Sum());
	}

	public static int Sum () throws IOException
	{
		List<String> fileLines = Files.readAllLines(Paths.get("src/data/dayThree/input.txt"));

		int rows = fileLines.size();
		int columns = fileLines.get(0).length();
		int answer = 0;

		for (int row = 0 ; row < rows ; row++)
		{
			for (int column = 0 ; column < columns ; column++)
			{
				if (Character.isDigit(fileLines.get(row).charAt(column)))
				{
					int leftIndex = column;
					int num = 0;
					while (column < columns && Character.isDigit(fileLines.get(row).charAt(column)))
					{
						num = num * 10 + fileLines.get(row).charAt(column++) - '0';
					}
					int rightIndex = column - 1;

					for (int checkIndexColumn = leftIndex - 1 ; checkIndexColumn < rightIndex + 2 ; checkIndexColumn++)
					{
						for (int checkIndexRow = row - 1 ; checkIndexRow < row + 2 ; checkIndexRow++)
						{
							if (inIndexRange(checkIndexRow, checkIndexColumn, rows, columns) && isSymbol(fileLines.get(checkIndexRow).charAt(checkIndexColumn)))
							{
								answer += num;
							}
						}
					}
				}
			}
		}

		return answer;
	}

	private static boolean isSymbol (Character checkChar)
	{
		return ! (Character.isDigit(checkChar) || checkChar == '.');
	}

	private static boolean inIndexRange(int row, int column, int rows, int columns)
	{
		return (row >= 0 && row < rows) && (column >= 0 && column < columns);
	}
}