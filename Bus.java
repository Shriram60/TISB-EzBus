public class Bus{
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
public static void main(String[] args){
  Bus obj = new Bus(1,1);
  obj.addGoal(new Position(2,1,1));
  obj.addGoal(new Position(1,2,1));
  obj.update();
  obj.update();
  obj.update();
  obj.update();
}
public void update(){
  Position p = path.update(x,y);
  if(p.no == -1){
    return;
  }
  Move(p.x,p.y);
  System.out.println(x+","+y);
}
public boolean addPickup(Position p){
  if(p.no<=max_cap-current_cap){
    addGoal(p);
    return true;
  }
  return false;
}
public void addGoal(Position p){
  path.addP(p);
}
public void Move(int xoA, int yoA){
  stopped = false;
  if(x > xoA){
    x--;
  }
  else if(x < xoA){
    x++;
  }
  else if(y > yoA){
    y--;
  }
  else if(y < yoA){
    y++;
  }else{
    stopped = true;
  }
}
}
