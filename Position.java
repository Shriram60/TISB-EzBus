public class Position{
  int x;
  int y;
  int wait;
  int no;
  public Position(int x,int y,int no){
    this.x = x;
    this.y = y;
    this.no =no;
    this.wait = 0;
  }
  public Position(int x,int y){
    this.x = x;
    this.y = y;
    this.no = 0;
    this.wait = 0;
  }
}
