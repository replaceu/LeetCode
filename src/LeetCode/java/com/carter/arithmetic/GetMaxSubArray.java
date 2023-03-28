package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.List;

public class GetMaxSubArray {
	private static Integer getMaxDayAddDays(List<Integer> rankDayMaxList) {
		List<Integer> target = new ArrayList<>();
		for (int i = 0; i < rankDayMaxList.size() - 1; i++) {
			if (rankDayMaxList.get(i) - rankDayMaxList.get(i + 1) == 1) {
				target.add(rankDayMaxList.get(i));
			} else {
				target.add(rankDayMaxList.get(i));
				break;
			}
		}
		return target.size();
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(32);
		list.add(30);
		list.add(29);
		list.add(27);
		list.add(26);
		list.add(25);
		list.add(24);
		list.add(23);
		list.add(22);
		list.add(21);
		list.add(20);
		list.add(19);
		list.add(18);
		list.add(17);
		list.add(16);

		list.add(14);
		//list.add(12);
		//list.add(11);
		list.add(10);
		list.add(9);
		list.add(8);
		list.add(7);
		list.add(6);
		list.add(5);
		list.add(4);
		list.add(3);
		//list.add(2);
		list.add(1);

		List<Integer> retList = new ArrayList<>();
		List<Integer> maxDays = getMaxDays(list, retList, 1);
		for (int i = 0; i < maxDays.size(); i++) {
			System.out.println(maxDays.get(i));
		}
	}

	private static List<Integer> getMaxDays(List<Integer> list, List<Integer> retList, Integer ret) {
		int left = 0;
		int size = 0;
		int pre = ret;
		int right = list.size() - 1;
		while (left < right) {
			if (list.get(left) != null && list.get(left + 1) != null) {
				if (list.get(left) - list.get(left + 1) == 1) {
					left++;
					size++;
				} else {
					left++;
					size++;
					retList.add(size);
					if (size > pre) {
						retList.clear();
						retList.add(size);
					}
					break;
				}
			}
		}
		while (left == right) {
			if (list.get(left) - list.get(left - 1) == -1) {
				if (size + 1 > pre) {
					retList.clear();
					retList.add(size + 1);
				}
				break;
			} else {
				retList.add(1);
				break;
			}
		}

		if (left < right) {
			List<Integer> subList = list.subList(left, list.size());
			getMaxDays(subList, retList, retList.get(0));
		}
		return retList;
	}
}
