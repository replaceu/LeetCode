package com.carter.structure;

import java.util.ArrayList;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的最长子串的长度
 */
public class LengthOfLongestSubstring {
	public void lengthOfLongestSubstring(String s) {
		String[] split = s.split("");
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (int i = 0; i < split.length; i++) {
			for (int j = i + 1; j < split.length; j++) {
				if (split[i].equals(split[j])) {
					int result = j - i;
					arrayList.add(result);
					System.out.println(arrayList);
				}

			}
		}

	}

	public static void main(String[] args) {
		LengthOfLongestSubstring lengthOfLongest = new LengthOfLongestSubstring();
		lengthOfLongest.lengthOfLongestSubstring("abcdaefgbhjklohhhhgrda");
		//System.out.println(list.size());
		//System.out.println(result);

	}

}
