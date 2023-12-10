package Day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2
{
	public static void main(String[] args) throws IOException
	{
		System.out.println(Sum());
	}

	public static int Sum() throws IOException
	{
		List<String> fileLines = Files.readAllLines(Paths.get("src/data/day3/input.txt"));

		int rows = fileLines.size();
		int columns = fileLines.get(0).length();

		List<List<List<Integer>>> numberForSymbols = new ArrayList<>();

		for (int row = 0 ; row < rows ; row++)
		{
			numberForSymbols.add(new ArrayList<>());
			for (int column = 0 ; column < columns ; column++)
			{
				numberForSymbols.get(row).add(new ArrayList<>());
			}
		}

		for (int row = 0 ; row < rows ; row++)
		{
			for (int column = 0 ; column < columns ; column++)
			{
				if (Character.isDigit(fileLines.get(row).charAt(column)))
				{
					int leftIndex = column;
					int number = 0;
					while (column < rows && Character.isDigit(fileLines.get(row).charAt(column)))
					{
						number = number * 10 + (fileLines.get(row).charAt(column++) - '0');
					}

					int rightIndex = column - 1;

					for (int checkColumn = leftIndex - 1 ; checkColumn < rightIndex + 2 ; checkColumn++)
					{
						for (int checkRow = row - 1 ; checkRow < row + 2 ; checkRow++)
						{
							if (inIndexRange(checkRow, checkColumn, rows, columns) && isSymbol(fileLines.get(checkRow).charAt(checkColumn)))
								numberForSymbols.get(checkRow).get(checkColumn).add(number);
						}
					}
				}
			}
		}

		int answer = 0;
		for (int row = 0 ; row < rows ; row++)
		{
			for (int column = 0 ; column < columns ; column++)
			{
				if (fileLines.get(row).charAt(column) == '*' && numberForSymbols.get(row).get(column).size() == 2)
				{
					answer += numberForSymbols.get(row).get(column).get(0) * numberForSymbols.get(row).get(column).get(1);
				}
			}
		}

		return answer;
	}

	private static boolean isSymbol(Character checkChar)
	{
		return !(Character.isDigit(checkChar) || checkChar == '.');
	}

	private static boolean inIndexRange(int row, int column, int rows, int columns)
	{
		return (row >= 0 && row < rows) && (column >= 0 && column < columns);
	}
}