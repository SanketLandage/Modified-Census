package CensusProfiling.Mod;

public class Test {
	static int ElementAndIndices(int[] arr) {
		int cnt=0;
		if(arr.length == 0) {
			return -1;
		}
		else {
			for(int i = 0 ; i < arr.length ; i++) {
				if(arr[i] == i) {
					cnt++;
				}
			}
			return cnt;
		}
	}
	
	static String ReverseString(String s) {
		if(s.isEmpty()) {
			return null;
		}
		return s;
	}
	
	public static void main(String[] args) {
		int[] arr = {10,1,12,3,5,8,9,7,12,23};
		Test t = new Test();
		System.out.println(t.ElementAndIndices(arr));
		String s ="";
		System.out.println(t.ReverseString(s));
	}
}
