import java.util.*;
public class Grid
{
  Random rn = new Random();
  int size=5;
  ArrayList<Position> stations = new ArrayList<Position>();
  ArrayList<Bus> buses = new ArrayList<Bus>();
  ArrayList<PickupPoint> UnresolvedPickup = new ArrayList<Position>();
  ArrayList<PickupPoint> pickup = new ArrayList<Position>();
  public  Grid(){
    while(stations.size()<5)
    {
    int x= rn.nextInt(4) + 1;
    int y= rn.nextInt(4) + 1;
    //add only if not repeated
    stations.add(new Position(x,y));
    }
  }
  public void add()
  {
    int s = rn.nextInt(4) + 1;
    int[] goals = new int[stations.size()];
    int num = 0;
    while(Math.random()>0.5){
      int r = rn.nextInt(stations.size());
      while(r==s){
        r = rn.nextInt(stations.size());
      }
      goals[r]++;
      num++;
    }
    //st.get(s).no = num;
    //pickup.add(st.get(s));
    query(st.get(s));
  }

  public void query(Position p)
  {
    for(int i = 0;i<buses.size();i++)
    {
      Bus a = buses.get(i);
      if(a.addPickup(p))
      {
        return;
      }
    }
    UnresolvedPickup.add(p);
  }
  public int distance(int x1,int y1,int x2,int y2)
  {
    return Math.abs(x1-x2)+Math.abs(y1-y2);
  }

  public void searchBus()
  {
    for(Bus b:buses){
      boolean found = false;
      if(b.stopped){
        for(Position p:pickup)
        {
          if(b.x == p.x && b.y == p.y)
          {

          }
        }
      }
    }
  }
  public void busPickup(){

  }
  class PickupPoint{
    int x;
    int y;
    int[] goals
    public PickupPoint(int x,int y,int[] goals){
      this.x = x;
      this.y = y;
      this.goals =goals;

    }
  }
}
