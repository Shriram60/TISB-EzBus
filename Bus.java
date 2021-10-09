public class Bus
{
  int x;
  int y;
  int max_cap = 10;
  int current_cap = 0;
  boolean stopped = false;
  Pathfinder path = new Pathfinder();

  public Bus(int x1, int y1)
  {
    x = x1;
    y = y1;
  }
  public void drop()
  {
    current_cap-=path.removed;
    path.removed = 0;
    System.out.println(current_cap);
    System.out.println(path.queue.size());
  }
  public void update()
  {
    Position p = path.update(x,y);
    if(p.no == -1)
    {
      return;
    }
    Move(p.x,p.y);
  }
  public boolean addPickup(Position p)
  {
    if(p.no<=max_cap-current_cap)
    {
      addGoal(p);
      return true;
    }
    return false;
  }
  public void addGoal(Position p)
  {
    path.addP(p);
  }
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
    }else
    {
      stopped = true;
    }
  }
}
