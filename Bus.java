import java.util.ArrayList;
public class Bus
{
  int x;
  int y;
  int max_cap = 10;
  int current_cap = 0;
  boolean stopped = false;
  Pathfinder path = new Pathfinder();
  ArrayList<PickupPoint> pickup = new ArrayList<PickupPoint>();
  PickupPoint current;

  //Constructor for class Bus
  public Bus(int x1, int y1)
  {
    x = x1;
    y = y1;
  }
  //Function to reduce capacity of the bus after 'dropping'
  public void drop(int num)
  {
    current_cap -= num;
  }

  //Function to update the pathfinder and 'move'
  public void update()
  {
    Position p = path.update(x, y);
    if(p.no == -1)
    {
      return;
    }
    Move(p.x, p.y);
  }

  //Function to add a new Pickup Point
  public boolean addPickup(PickupPoint p)
  {
    if(p.num <= max_cap - current_cap)
    {
      
      addGoal(new Position(p.x, p.y, p.num));
      pickup.add(p);
      return true;
    }
    return false;
  }

  //Function add a new Position to the Pathfinder
  public void addGoal(Position p)
  {
    path.addPosition(p);
  }

  //Function to 'Pickup' from the Goal, reducing capacity of the bus, setting new goals(people who just boarded)
  public void busPickup(ArrayList<Position>stations)
  {
    current_cap += current.num;
    pickup.remove(current);
    for(int i = 0; i < current.goals.length; i++)
    {
      int n = current.goals[i];
      if(n != 0)
      {
        Position p2 = stations.get(i);
        p2.no = n;
        addGoal(p2);
      }
    }
  }

  //Function to 'move' the bus by x and y co-ordinates as required
  public void Move(int xoA, int yoA)
  {
    stopped = false;
    if(x > xoA)
    {
      x--;
    }
    else if(x < xoA)
    {
      x++;
    }
    else if(y > yoA)
    {
      y--;
    }
    else if(y < yoA)
    {
      y++;
    }
    else
    {
      for(PickupPoint pick:pickup)
      {
        if(pick.x == x && pick.y == y)
        {
          pickup.remove(pick);  
          stopped = true;
          current = pick;
          path.removed = 0;
        }
        else if(path.queue.size() == 0)
        {
          return;
        }
        else
        {
          drop(path.removed);
          path.removed = 0;
        }
      }
    }
  }
}
