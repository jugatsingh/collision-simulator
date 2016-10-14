import java.awt.Color;

public class Particle{
	private static final double INFINITY = Double.POSITIVE_INFINITY;
	private double rx,ry;  // position
	private double vx,vy;  // velocity
	private final double radius;
	private final double mass;
	private int count;   // no of collisions
	private Color color;

	Particle(){
		rx = StdRandom.uniform(0.0, 1.0);
        ry = StdRandom.uniform(0.0, 1.0);
        vx = StdRandom.uniform(-.005, 0.005);
        vy = StdRandom.uniform(-.005, 0.005);
        radius = 0.01;
        mass = 0.5;
        color = Color.BLACK;
	}

	Particle(double a, double b, double c, double d, double r,double m,Color c2){
		rx=a;
		ry=b;
		vx=c;
		vy=d;
		radius=r;
		mass=m;
		color=c2;
	}

	public void move(double dt){
		rx+=vx*dt;											
		ry+=vy*dt;
	}

	public void draw(){
		StdDraw.setPenColor(color);
		StdDraw.filledCircle(rx,ry,radius);
	}

	public int count(){
		return count;
	}

	// predict collision

	public double timeToHit(Particle that){
		if (this == that) return INFINITY;
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;
        if (dvdr > 0) return INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
        double sigma = this.radius + that.radius;
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        // if (drdr < sigma*sigma) StdOut.println("overlapping particles");
        if (d < 0) return INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
	}

	public double timeToHitVerticalWall(){
		if (vx > 0) return (1.0 - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;  
        else return INFINITY;}

	public double timeToHitHorizontalWall(){
		if (vy > 0) return (1.0 - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else return INFINITY;
	}

	// resolving collision

	public void bounceOff(Particle that){
		double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;             // dv dot dr
        double dist = this.radius + that.radius;   // distance between particle centers at collison

        // normal force F, and in x and y directions
        double F = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);
        double fx = F * dx / dist;
        double fy = F * dy / dist;

        // update velocities according to normal force
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }
	
	public void bounceOffVerticalWall(){
		vx = -vx;
        count++;
	}
	public void bounceOffHorizontalWall(){
		vy = -vy;
        count++;
	}

	public double kineticEnergy() {
        return 0.5 * mass * (vx*vx + vy*vy);
    }
}