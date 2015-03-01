/*
ID: oops
LANG: JAVA
TASK: test
*/

import java.io.*;
import java.util.*;


public class TEST1 {
	
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
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
			int[] a = {3,2,4};
			int target = 6;
			int[] res = twoSum(a,target);
			System.out.println(res[1]);
			
		
		}

}
