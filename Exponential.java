import java.math.BigInteger;


/*
	An immutable class to represent a number that can be represented as
	base^power, where base and power are integers greater than one.

	The value and base can be arbitrarily large, thanks to BigInteger. Power
	is still represented with an int, because 2^MAX_INT is astronomically
	large.
*/
public class Exponential implements Comparable<Exponential>
{
	private static final BigInteger largePrime1 = new BigInteger("10007");
	private static final int        largePrime2 = 10009;

	final BigInteger base;
	final int power;
	final BigInteger value;

	public Exponential(BigInteger base, int power)
	{
		this.base = base;
		this.power = power;
		this.value = base.pow(power);
	}

	/* If the values are equal, compare powers */
	public int compareTo(Exponential other)
	{
		int comp = value.compareTo(other.value);
		if (comp != 0)
			return comp;
		else
			return power - other.power;
	}

	public int hashCode()
	{
		BigInteger baseCalc = base.multiply(largePrime1);
		return baseCalc.intValue() + power*largePrime2;
	}

	public boolean equals(Object object)
	{
		if (!(object instanceof Exponential))
			return false;
		Exponential other = (Exponential) object;

		if (!base.equals(other.base))
			return false;

		if (power != other.power)
			return false;

		return true;
	}

	public String toString()
	{
		return base + "^" + power;
	}

	public BigInteger base()  { return base;  }
	public int power()        { return power; }
	public BigInteger value() { return value; }
}
