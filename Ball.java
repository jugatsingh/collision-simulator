public class Ball{
	private double rx,ry;  // position
	private double vx,vy;  // velocity
	private final double radius;

	Ball(){
		this(Math.random(),Math.random(),Math.random(),Math.random(),0.01);
	}

	Ball(double a, double b, double c, double d, double r){
		rx=a;
		ry=b;
		vx=c;
		vy=d;
		radius=r;
	}

	public void move(double dt){
		if((rx+vx*dt<radius)||(rx+vx*dt>1.0-radius)) {vx=-vx;}   // check for collision with walls  
		if((ry+vy*dt<radius)||(ry+vy*dt>1.0-radius)) {vy=-vy;}  
		rx+=vx*dt;											
		ry+=vy*dt;
	}

	public void draw(){
		StdDraw.filledCircle(rx,ry,radius);
	}
}