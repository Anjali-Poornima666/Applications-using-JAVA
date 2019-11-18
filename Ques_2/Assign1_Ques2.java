import java.io.*;
import java.util.*;

class ABCcompany{

	private static double minWage = 400.00;
	private static int maxHours = 60;
	private static double maximumBasePay = minWage;
	private double basePay;
	private int hours;

	ABCcompany(){}
	ABCcompany(double basePay,int hours){
		this.basePay = basePay;
		this.hours = hours;
	} 
	public void check(double basePay,int hours){

		if(this.basePay < this.minWage){
			System.out.println("Minimun Base Pay is Rs.400 per hour!!!!");
			
		}
		if(this.hours > this.maxHours){
			System.out.println("Maximum hours an employee can work is 60 hours!!!!");
			
		}
		
	}
	public double getBasePay(){
		return this.basePay;
	}
	public int getHours(){
		return this.hours;
	}
	public void setBasePay(double basePay){
		this.basePay = basePay;
	}
	public void setHours(int hrs){
		this.hours = hrs;
	}

	public double totalPay(){
		if(this.hours<=40)
			return this.hours * this.basePay;
		else
			return 40*this.basePay + (this.hours - 40)*1.5*this.basePay;
	}
	public double getMaximumBasePay(){
		return this.maximumBasePay;
	}
	public void setMaximumBasePay(double p){
		this.maximumBasePay = p;
	}

}

class DiwaliBonus extends ABCcompany{



	public void checkDiwaliBonus(ABCcompany[] emp,int n){

		ArrayList<ABCcompany> empWithBonus = new ArrayList<ABCcompany>(); 
		for (int i=0;i<n;i++){
			if (emp[i].getBasePay() < super.getMaximumBasePay()*0.7){
				empWithBonus.add(emp[i]);
				System.out.println("The bonus of Employee "+(i+1)+" is "+this.bonus(emp[i]));
			}
			else
				System.out.println("Employee "+(i+1)+" didn't get bonus");
		}
		
	}

	public double bonus(ABCcompany emp){
		double pay = emp.totalPay();
        double bonus = pay*0.2;
        return pay+bonus;
	}
}
public class Assign1_Ques2{
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the number of employees: ");
		int n = s.nextInt();
		System.out.println("Enter the base pay and hours worked of each employee as 'basePay HoursWorked' ");
		ABCcompany employees[] = new ABCcompany[n];
		int hrs;
		double pay;

		for (int i=0;i<n;i++){
			pay = s.nextDouble();
			hrs = s.nextInt();
			ABCcompany emp = new ABCcompany(pay,hrs);
			if(emp.getMaximumBasePay() < pay)
				emp.setMaximumBasePay(pay) ;
			emp.check(pay,hrs);
			employees[i] = emp;
			//System.out.println("Your entry is considered!!!!");
				//System.out.println("Employee "+ (j+1) + "  " + employees[j].getBasePay() + "  " + employees[j].getHours());
				
			
		}
		System.out.println("The entered employee are: \nEmployee	Base Pay   Hours worked");
		for (int i=0;i<n;i++){
			System.out.println("Employee "+ (i+1) + " 	" + employees[i].getBasePay() + "	 " + employees[i].getHours());
		}
		System.out.println("----------------------------------------------------------");
		System.out.println("The Total pay of employees before Diwali Bonus \nEmployee	Base Pay   Hours worked 	Total Pay");
		for (int i=0;i<n;i++){
			System.out.println("Employee "+ (i+1) + " 	" + employees[i].getBasePay() + "		 " + employees[i].getHours() + " 		" + employees[i].totalPay());
		}
		ABCcompany ref = new ABCcompany(0,0);
		System.out.println("----------------------------------------------------------");
		System.out.println("The Maximum BasePay is "+ ref.getMaximumBasePay());

		DiwaliBonus diwali = new DiwaliBonus();
		diwali.checkDiwaliBonus(employees,n);

	}
}