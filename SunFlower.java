class SunFlower extends Plants
{
	private int column_no;

	SunFlower(int _column_no){
		super("SunFlower.gif",50,50);
		column_no = _column_no;
	}

	public int getColumn_no() { return column_no; }
}