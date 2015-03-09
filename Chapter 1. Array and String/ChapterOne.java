package chapterOne;
import java.util.*;
import java.io.*;

public class ChapterOne {

	/*
	 * 第一个题目屏蔽了许多边界情况（是否无解？是否多解？是否输入为0等等），最直接想到的就是两轮循环，brute force的方式解决。
	 * 这个解法当然可以过，估计面试官让你预热后，直接就会问你复杂度的分析，然后再追问，如果输入很大的数组，是否该方法还合适？
	 * 虽然平方的复杂度尚可，但brute force几乎不可能成为一个面试题目中最终的解决方案。*/
	public static int[] twoSum(int[] numbers, int target) {//为了能在main函数中直接测试，static使得方法从属于一个类，而不是对象
		int[] index = new int[2];//自定义类型必须初始化；数组因大小未知，也属于自定义类型
		for (int i = 0; i < numbers.length; i++)
		{
			for(int j = i + 1; j < numbers.length; j++)//很典型的brute force,如果OJ不过，肯定会有时间限制的说法。
			{
				if(numbers[i]+numbers[j] == target)
				{
					index[0] = i+1;
					index[1] = j+1;
				}
			}
		}
		return index;
	        
	}
	
	public static int[] twoSum2(int[] numbers, int target) {
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < numbers.length; i++) {
			if (map.containsKey(target - numbers[i])) {
				return new int[] { map.get(target - numbers[i])+1, i+1};
			}
			map.put(numbers[i], i);
		}
		throw new IllegalArgumentException("No answer!");
	}
	
	//20150308
	public static boolean validNumber(String s) {
		boolean isnumber = false;
		int i = 0, n = s.length();
		while (i < n && Character.isWhitespace(s.charAt(i))) i++;
		if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) i++;
		while (i < n && Character.isDigit(s.charAt(i))) {
			i++;
			isnumber = true;
		}
		if (i < n && (s.charAt(i) == '.')) {
			i++;
			//不是每一个while都能运行，所以这里也要对isnumber赋值
			while (i < n && Character.isDigit(s.charAt(i))) {
				i++;
				isnumber = true;
			}
		}
		if (i < n && (s.charAt(i) == 'e')) {
			if (!isnumber) return false;
			i++;
			//if (i<n && s.charAt(i))
			isnumber = false;
			//不是每一个while都能运行，所以这里也要对isnumber赋值
			if (i < n && (s.charAt(i) == '+' || s.charAt(i) == '-')) i++;
			while (i < n && Character.isDigit(s.charAt(i))) {
				i++;
				isnumber = true;
			}
		}
		while (i < n && Character.isWhitespace(s.charAt(i))) i++;
		return isnumber && (i == n);
	}
	public static int bsearch(int[] numbers, int start, int end, int num) {
		if (start >= end){
			return -1;
		}
		int index = (start + end)/2;
		if (num == numbers[index]){
			return index;
		}
		else if (num < numbers[index]){
			return bsearch(numbers, start, index, num);
		}
		else{
			return bsearch(numbers, index, end, num);
		}
	}
	//常用算法，别问为什么。
	public static int bsearch2(int[] numbers, int start, int num) {
		int i = start;
		int j = numbers.length;
		while (i < j) {
			int mid = (i + j) / 2;
			if (numbers[mid] == num) {
				return mid;
			}
			else if (numbers[mid] < num) {
				i = mid + 1;
				//mid = (mid + j) / 2;
			}
			else {
				j = mid;
			//	mid = (i + mid) / 2;
			}
			System.out.println(i+" "+j);
		}
		throw new IllegalArgumentException("No answer!");

	}
	/*
	 * 当说数组是sorted的时候，通常要用到这一信息，这意味着，会用到二叉查找
	 * 而二叉查找，通常会意味着递归*/
	public static int[] twoSumsorted(int[] numbers, int target) {
		for (int i = 0; i < numbers.length; i++) {
			if(bsearch(numbers, 0, numbers.length - 1, target - numbers[i]) != -1) {
				return new int[] {i+1, bsearch(numbers, 0, numbers.length - 1, target - numbers[i]) + 1};
			}
		}
		throw new IllegalArgumentException("No answer!");
	}
	/*
	 * 我们就规定左的往右，右的往左；
	 * 为什么可以？i j < target; i不可能左移,i-1势必出现在i j前，而不满足target，才会加上来
	 * i j> target，j不可能右移，j+1出现在之前，不满足才减下来*/
	public static int[] twoSumsorted2(int[] numbers, int target) {
		int i = 0;
		int j = numbers.length;
		while (i<j) {
			if (numbers[i] + numbers[j] == target) {
				return new int[] {i+1, j+1};
			}
			else if (numbers[i] + numbers[j] < target) {
				i++;
			}
			else {
				j--;
			}
		}
		throw new IllegalArgumentException("No answer!");
	}
	
	//见识了静态类以及静态类方法的作用。类似的Integer.估计也有不少
	//这种比较一半都是i<j没有全部遍历的道理
	
	public static boolean isPalindrome(String s) {
		int i = 0, j = s.length() - 1;
		while(i < j) {
			while ((i < j) && (!Character.isLetterOrDigit(s.charAt(i)))) {
				i++;
			}
			while ((i < j) && (!Character.isLetterOrDigit(s.charAt(j)))) {
				j--;
			}
			if (Character.toLowerCase(s.charAt(i))!= Character.toLowerCase(s.charAt(j))) {
				return false;
			}
			i++;//while循环别忘了自己加
			j--;
		}
		return true;
	}
	//居然if的顺序也有关系！一般，特殊情况排除的写在前面，没有可能越界异常的时候，再到实质if
	public static int strstr(String haystack, String needle) {
		for (int i = 0;; i++) {
			for (int j = 0;; j++) {
				if (j == needle.length()) {
					return i;
				}
				//注意不可能再有答案，在计算语言中的表示！
				if (i + j == haystack.length()) {
					return -1;//原来边界条件作在这里
				}
				if (Character.toLowerCase(needle.charAt(j)) != Character.toLowerCase(haystack.charAt(i + j))) {
					break;
				}			
			}
		}
	}
	
	@SuppressWarnings("null")
	public static String reverse (String s) {
		//String[] temp = s.split(s, ' ');
		StringBuilder neat = new StringBuilder();//null不行！！！！
		for (int i = 0; i < s.length(); i++) {
			while (i < s.length() && s.charAt(i) == ' ') {
				i++;
			}
			for (int j = 0; ; j++) {
				if (i + j < s.length()) {
					if (s.charAt(i + j) == ' ') {
						i = i + j;
						neat.append(' ');
						break;
					}
					else {
						neat.append(s.charAt(i + j));
					}
				}
				else {
					i = i + j;
					break;
				}				
			}
		}
		
		//String temp = neat.toString();
		String[] temp = neat.toString().split(" ");
		StringBuilder neat2 = new StringBuilder();
		for(int i = temp.length - 1; i >= 0; i--) {
			if (i != 0){
				neat2.append(temp[i]+" ");
			}
			else {
				neat2.append(temp[i]);
			}
		}
		return neat2.toString();
		//return neat.toString();
	}
	//
	
	
	public static int atoi(String str) {
		int testvalue = Integer.MAX_VALUE/10;
		int i = 0;
		while (i < str.length() && Character.isWhitespace(str.charAt(i))) i++;// 比 == ‘ ’专业
		int sign = 1;
		if (i < str.length() && str.charAt(i) == '-') {//这种有条件地从左往右溜达一遍 通常都要带着边界限制
			sign = -1;
			i++;
		}
		else if (i < str.length() && str.charAt(i) == '+') {
			i++;
		}
		
		int num = 0;
		while (i < str.length() && Character.isDigit(str.charAt(i))) { //一个API
			int digit = Character.getNumericValue(str.charAt(i)); //又是一个API
			if (num > testvalue || num == testvalue && digit >= 8) {//本题核心 overflow的control！
				return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
			}
			else {
				num = num * 10 + digit;
				i++;
			}
				
		}
		
		return sign*num;
		
	}
	
	public static int thelongest (String s) {
		int length = 0;
		int i = 0, j = 1;
		if (s.length() == 0 || s.length() == 1) return s.length();
		
		Map<Character,Integer> map = new HashMap<Character,Integer>();
		map.put(s.charAt(i), i);
		
		while (j < s.length()) {
			if (map.containsKey(s.charAt(j))) {
				length = (length > j - i) ? length: (j - i);
				i = map.get(s.charAt(j));
				i++;
				j = i + 1;
				map.clear();
				map.put(s.charAt(i), i);
			}
			else {
				map.put(s.charAt(j), j);
				length++;
				j++;
			}
		}
		return length;
	}
	//20150309 有难度！对charmap体会不深！对256ascii駡体会不深。
	public static int thelongest2 (String s) {
		int[] charmap = new int[256];//记录出现charat(j)的最后位置
		Arrays.fill(charmap, -1);//静态函数！
		int i = 0, len = 0;
		for (int j = 0; j < s.length(); j++) {
			if (charmap[s.charAt(j)] >= i) {
				i = charmap[s.charAt(j)] + 1;
			}
			charmap[s.charAt(j)] = j;
			len = Math.max(j - i + 1, len);//静态函数！
		}
		return len;
	}
	public static int longesttwodistinct (String s) {
		boolean[] track = new boolean[256];
		int i = 0, len = 0, templen = 0, distinct = 0;
		for (int j = 0; j < s.length(); j++) {
			if (!track[s.charAt(j)]) {
				distinct++;
				if (distinct > 2) {
					//templen = 0;
					len = Math.max(len, j - i + 1);
					i = j;
					distinct = 0;
					Arrays.fill(track, false);
				}
			}
		}
		return len;
	}
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
			//int[] a = {1,2,3,4,5,6,7,8,9,10,12,13,15,17};
			//int target = 6;
			//int[] res = twoSum(a,target);
			String haystack = "1";
			String s = "ecebc"; 
					//println(Character.toLowerCase(a.charAt(0)) != Character.toLowerCase(b.charAt(0)) );
		
			
	//		String[] temp = s.split(" ");
			boolean[] exist = new boolean[256];
			
			System.out.println(longesttwodistinct(s));
			
		
		}

}
