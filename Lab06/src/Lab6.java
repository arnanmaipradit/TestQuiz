import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Lab6 {
	private static double E = 0.000001;
	
	public static void main(String[] args) throws IOException{
		InputStream in = new FileInputStream("lab6.txt");
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));
	
		String line = bf.readLine();
		line = bf.readLine();
		
		List<Double> listp = new ArrayList<Double>();
		List<Integer> listdof = new ArrayList<Integer>();
		
		while(line != null){
			String[] array = line.split(" ");
			listp.add(Double.parseDouble(array[0]));
			listdof.add(Integer.parseInt(array[1]));
			line = bf.readLine();
		}
		
		for(int i = 0; i < listp.size(); i++){
			print(setup(listp.get(i), listdof.get(i)));
		}
	}
	
	
	
	public static double setup(double p, int dof){
		double x = 0.0;
		double d = 0.5;
		int status = 0;
		
		while(true)
		{
			double temp = 0.0;
			temp = calculate(x, dof);
			double diff = 0.0;
			diff = Math.abs(temp - p);
			if(diff <= E)
			{
				return x;
			}
			else
			{
				if(temp > p)
				{
					if(status == -1){
						d /= 2.0;
					}
					
					x = x - d;
					status = 1;
				}
				else if(temp < p)
				{
					if(status == 1){
						d /= 2.0;	
					}
					
					x = x + d;
					status = -1;
				}
			}
		}
	}
	
	public static double calculate(double x, int dof){
		double p = 0.0;
		
		double num_seg = 10;
		double start = simpson(x, dof, num_seg);
		double finish = 0;
		
		while(true){
			num_seg *= 2;
			finish = simpson(x, dof, num_seg);
			
			if(Math.abs(finish - start) <= E){
				break;
			}
			else{
				start = finish;
			}
		}	
		
		p = finish;
			System.out.println("P = "+p);
		return p;
	}
	
	public static double simpson(double x, double dof, double num_seg){
		double w = x / num_seg;
		double number = 0.0;
		
		for(int i = 0; i <= num_seg; i++){
			if(i == 0 || i == num_seg){
				number += distribution(i * w, dof);
			}
			else if(i % 2 == 0){
				number += 2 * distribution(i * w, dof);
			}
			else{
				number += 4 * distribution(i * w, dof);
			}
		}
		
		double simpson = (number * w) / 3;
		System.out.println("Simpson = "+simpson);
		return simpson;
	}
	
	public static double distribution(double x, double dof){
		
		double one = gamma((dof + 1) / 2);
		double two = Math.pow(dof * Math.PI, 0.5);
		double three = gamma(dof / 2);
		double four = 1 + (Math.pow(x, 2) / dof);
		double five = - ((dof + 1) / 2);
		
		double Dis = (one / (two * three)) * Math.pow(four, five);
		System.out.println("Dis = "+Dis);
		return Dis;
	}
	
	public static double gamma(double x){
		double gamma = 1;
		
		while(0.5 < x){
			x -= 1;
			
			if(x != 0){
				gamma = gamma*x;
			}
		}
		
		if(x == 0.5){
			System.out.println("Gamma = "+gamma);
			return gamma * Math.sqrt(Math.PI);
			
		}
		else{
			
			System.out.println("Gamma = "+gamma);
			return gamma;
		}
		
	}
		public static void print(double x){
			System.out.println("x = " + x);
		}
	
}