class GridObject implements Serializable
{
	double xpos, ypos, xdelta, ydelta;

	GridObject (double i1, double i2, double i3, double i4)
	{
		xpos = i1;
		ypos = i2;
		xdelta = i3;
		ydelta = i4;
	}
}