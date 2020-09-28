package replicaA;

import org.omg.CORBA.ORB;


class ORBThread extends Thread
{
	private ORB orb;

	protected ORBThread(ORB pOrb)
	{
		orb = pOrb;
	}

	@Override
	public void run()
	{
		orb.run();
	}
}
