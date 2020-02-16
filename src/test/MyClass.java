package test;

public class MyClass {
	int l, s, e;private int m = 400;private int n = 50;public MyClass(int L, int S, int E){this.l = L;this.s = S;this.e = E;}public void validate(/*not used int L, int S, int E*/){while(this.l > 0){if(this.s + this.e > m){System.out.println("Fehler 1");}else if (this.e > this.n){System.out.println("Fehler 2");}else{this.s += this.e;}this.l --;}}
	
	public String toString(){
		return "MyClass:"+"l="+l+"|s="+s+"|e="+e;
	}
	
	public static void main(String[] args) {
		MyClass myClass1= new MyClass(-1, 0, 0);
		myClass1.validate();
		MyClass myClass2= new MyClass(10, 400, 100);
		myClass2.validate();
		MyClass myClass3= new MyClass(10, 300, 30);
		myClass3.validate();
		MyClass myClass4= new MyClass(10, 300, 60);
		myClass4.validate();

		System.out.println(myClass1);
		System.out.println(myClass2);
		System.out.println(myClass3);
		System.out.println(myClass4);
	}
}