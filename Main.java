import java.math.BigInteger;
import java.util.TreeSet;


public class Main
{
	public static void main(String[] args)
	{
		/* Extract maximum distance */
		if (args.length != 1)
		{
			System.out.println("Please provide a maximum distance.");
			return;
		}
		final BigInteger delta = new BigInteger(args[0]);

		/* Set up beginning conditions */
		final BigInteger benchmark = new BigInteger("100000");
		Exponential exp = new Exponential(BigInteger.ONE, 1);
		Exponential prev;
		TreeSet<Exponential> set = new TreeSet<>();
		set.add(new Exponential(new BigInteger("2"), 2));

		/* Main loop */
		while (true)
		{
			/* Get next exponential */
			prev = exp;
			exp = set.pollFirst();

			/* Extract values for ease of use */
			BigInteger base = exp.base();
			int power = exp.power();
			BigInteger value = exp.value();

			/* See if we found a near miss */
			BigInteger miss = value.subtract(prev.value());
			BigInteger check = miss.subtract(delta);
			if ( (check.signum() < 0) && !(value.equals(prev.value())) )
				System.out.println(value + "\t= " + exp + "\t= " + prev + " + " + miss);

			/* If we extracted a square, provide the next one */
			if (power == 2)
			{
				Exponential next = new Exponential(base.add(BigInteger.ONE), 2);
				set.add(next);

				if (next.base.mod(benchmark).equals(BigInteger.ZERO))
					System.out.println("Reached " + next);
			}

			/* Increment the power and add it to the set again */
			Exponential next = new Exponential(base, power+1);
			set.add(next);
		}
	}
}
