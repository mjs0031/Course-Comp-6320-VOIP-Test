package Second_Project_Code;

import java.util.Random;

public class PacketDropRate
{
	
	public static boolean isPacketDropped(int x1, int y1, int x2, int y2)
	{
		double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		int probability = new Random().nextInt(100);
		if (distance <= 80)
		{
			return false;
		}
		else if (distance <= 90)
		{
			if (probability < 20)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (distance <= 100)
		{
			if (probability < 40)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (distance <= 110)
		{
			if (probability < 60)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if (distance < 120)
		{
			if (probability < 80)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return true;
		}
	}
}
