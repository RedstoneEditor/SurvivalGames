package sg.objects;

public class Spawn {
	public double X;
	public double Y;
	public double Z;

	public Spawn(double X, double Y, double Z) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		makeCenteredLocation();
	}

	/** Set the location to the very center of the block instead of the corner */
	private void makeCenteredLocation() {
		this.X = Double.parseDouble(Double.toString(X).split(".")[0] + ".5");
		this.Y = Double.parseDouble(Double.toString(Y).split(".")[0] + ".5");
		this.Z = Double.parseDouble(Double.toString(Z).split(".")[0] + ".5");
	}

}
