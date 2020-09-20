class Location implements Comparable<Location>
{
    int x,y;
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public boolean equals(Object o)
    {
        return (o instanceof Location)&&((Location)o).getX()==x&&((Location)o).getY()==y;
    }

    public int hashCode()
    {
        return 31*x+127*y;
    }

    public String toString()
    {
        return "("+x+","+y+")";
    }

    public int compareTo(Location p)
    {
        if (this.y==p.getY()) {
            return Integer.compare(this.x, p.getX());
        }
        return Integer.compare(this.y,p.getY());
    }
}