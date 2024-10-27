package jspectrumanalyzer.core;

public class EMA
{
	public static double calculate(double currentValue, double previousEMA, double order)
	{
		double k = 2 / (order + 1);
		double result = currentValue * k + previousEMA * (1 - k);

		return (result);
	}

	public static double calculateTimeDependent(double currentValue, double previousEMA, long timeDiffFromPreviousValueMillis, double orderInMillis)
	{
		double order = timeDiffFromPreviousValueMillis <= 0 ? 1 : orderInMillis / timeDiffFromPreviousValueMillis;
		double k = 2 / (order + 1);
		double result;

		result = currentValue * k + previousEMA * (1 - k);

		return (result);
	}

	private double	ema	= 0;

	private int		order;

	public EMA(int order)
	{
		this.order = order;
	}

	public double addNewValue(double value)
	{
		return (ema = calculate(value, ema, order));
	}

	public double getEma()
	{
		return ema;
	}
}
