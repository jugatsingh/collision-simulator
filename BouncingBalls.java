public class BouncingBalls{
	public static void main(String args[]){
		int balls=Integer.parseInt(args[0]);
		Ball[] b=new Ball[balls];
		for(int i=0;i<balls;i++)
			b[i]=new Ball();

		while(true){
			StdDraw.clear();
			for(int i=0;i<balls;i++){
					b[i].move(0.02);
					b[i].draw();
			}
			StdDraw.show(20);
		}
	}
}