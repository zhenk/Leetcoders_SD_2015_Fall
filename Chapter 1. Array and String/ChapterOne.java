package chapterOne;
import java.util.*;
import java.io.*;

import javax.swing.plaf.ListUI;


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
	
	public static int expandfromcentral (String s, int start, int end) {
		//boolean change = false;
		while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
			start--;
			end++;
		//	change = true;
		}
		//从中间到两边扩，这个-1，极不寻常！
			return end - start - 1;
		
	}
	
	public static String longestPalindrome (String s) {
		//int maxlen = 0;
		int start = 0, end = 0;
		for (int i = 0; i < s.length(); i++) {
			int len1 = expandfromcentral(s,i,i);
			int len2 = expandfromcentral(s,i,i+1);
			int len = Math.max(len1, len2);
			//maxlen = Math.max(len, maxlen);
			if (len > end - start) {
				start = i - (len - 1) / 2;
				end = i + len / 2; //这两行赞！包含了单双两种情况，不论是12345还是123456都可以！
			}
		}
		//System.out.println(start+" "+end);
		//substring子方法不包括endindex，好神奇耶!
		return s.substring(start, end + 1);
	}
	
	
	public static int reverse (int num) {
		int ret = 0;
		while (num != 0) {
			if (Math.abs(ret) > Integer.MAX_VALUE / 10) return 0;
			ret = ret * 10 + num % 10;
			num = num / 10;
		}
		return ret;
	}
	// the following two methods are for plus one problem. 20150310
	// recursive methods are generally fine!
	// think through all the cases and represent the logic into if-else and for-while loops.
	public static int[] manipulate(int[] arr, int j) {
		arr[j] = 0;
		if ((j - 1) >= 0 && arr[j - 1] != 9) {
			arr[j - 1]++;
			return arr;
		}
		else if ((j - 1) >= 0) {
			return manipulate(arr, j - 1);
		}
		else {
			int[] newarr = new int[arr.length + 1];
			for (int i = 0; i < arr.length; i++) {
				newarr[i + 1] = 0;
			}
			newarr[0] = 1;
			return newarr;
		}
	}
	//
	public static int[] plusone (int[] arr) {
		if (arr[arr.length - 1] != 9) {
			arr[arr.length - 1]++;
			return arr;
		}
		else {
			return manipulate(arr,arr.length - 1);
		}	
	}
	
	public static boolean ispalindromenum (int x) {
		if (x < 0) return false;
		Integer newx = new Integer(x);
		String s = newx.toString();
		int start = 0, end = 0;
		if (s.length() % 2 != 0) {
			int mid = s.length() / 2;
		    start = mid - 1;
		    end = mid + 1;
			while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
				start--;
				end++;
			}
		//	return end - start - 1 == s.length() ? true : false;
		}
		else {
		    start = (s.length() - 1) / 2;
			end = s.length() / 2;
			while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
				start--;
				end++;
			}
		}
		return end - start - 1 == s.length() ? true : false;
	}
	//20150310 对链表不熟练 就地链表操作 需要额外的Node
	//经常容易出现的食物就是null的Node操作，相当于越界，while（本身是否空）{Node往下移动！}
	public static ListNode Merge (ListNode L1, ListNode L2) {
		 ListNode dummyhead2 = new ListNode(0);
	        ListNode dummyhead = dummyhead2;
			while (L1 != null && L2 != null) {
				if (L1.val > L2.val) {
					dummyhead.next = L2;
					L2 = L2.next;
				}
				else {
					dummyhead.next = L1;
					L1 = L1.next;
				}
				dummyhead = dummyhead.next;
			}
			if (L2 != null) {
				dummyhead.next = L2;
			}
			if (L1 != null) {
				dummyhead.next = L1;
			}
			return dummyhead2.next;
	}
	
	public static ListNode addtwonum (ListNode l1, ListNode l2) {
		ListNode dummyhead = new ListNode(0), p = l1, q = l2, m = dummyhead;
		int carry = 0, val = 0;
		while(p != null || q != null) {
			int x = (p != null) ? p.val : 0;
			int y = (q != null) ? q.val : 0;
			val = x + y + carry;
			carry = val / 10;
			ListNode newnode = new ListNode (val % 10);
			m.next = newnode;
			m = m.next;
		    if (p != null) p = p.next;
			if (q != null) q = q.next;		
		}
		if (carry > 0) {
			ListNode c = new ListNode(carry);
			m.next = c;
		}
		return dummyhead.next;
	}
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
			//int[] a = {1,2,3,4,5,6,7,8,9,10,12,13,15,17};
			//int target = 6;
			//int[] res = twoSum(a,target);
			String haystack = "1";
			String s = "ccc"; 
					//println(Character.toLowerCase(a.charAt(0)) != Character.toLowerCase(b.charAt(0)) );
		
			String ss = "abcdefg";
	//		String[] temp = s.split(" ");
			boolean[] exist = new boolean[256];
			int[] num = {9,9,9};
			int[] newnum = plusone(num);
			;
			int x = 123212;
			// how to convert int[] into string? use the method as follows.
			System.out.println(ispalindromenum(x));
			
		
		}

}
