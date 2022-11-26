package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TwoListSubtract {
	public static void main(String[] args) {
		List<IntegralPointLog> upPointList = new ArrayList<>();
		List<IntegralPointLog> delPointList = new ArrayList<>();

		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "50", "101"));
		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "10", "102"));
		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "100", "104"));
		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "200", "104"));

		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-30", "201"));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-40", "202"));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-70", "203"));
		//delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-70", "203"));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-100", "204"));
		getListSubtract(upPointList, delPointList);
	}

	private static void getListSubtract(List<IntegralPointLog> upPointList, List<IntegralPointLog> delPointList) {
		int remainValue = 0;
		int i = 0;
		int j = 0;

		//todo:得先把数据洗一下
		upPointList = cleanIntegralPointList(upPointList);
		delPointList = cleanIntegralPointList(delPointList);

		while (i < upPointList.size() && j < delPointList.size()) {
			if (upPointList.get(i).getRemainVal() != null && upPointList.get(i).getRemainVal().equals("error")) {
				i++;
				continue;
			}
			if (delPointList.get(j).getRemainVal() != null && delPointList.get(j).getRemainVal().equals("error")) {
				j++;
				continue;
			}

			Integer computeUpValue = upPointList.get(i).getRemainVal() == null ? Integer.valueOf(upPointList.get(i).getChangeVal()) : Integer.valueOf(upPointList.get(i).getRemainVal());
			Integer computeDelValue = delPointList.get(j).getRemainVal() == null ? Integer.valueOf(delPointList.get(j).getChangeVal()) : Integer.valueOf(delPointList.get(j).getRemainVal());

			remainValue = computeUpValue + computeDelValue;
			if (remainValue >= 0) {
				upPointList.get(i).setRemainVal(String.valueOf(remainValue));
				delPointList.get(j).setRemainVal(String.valueOf(0));
				j++;

			} else {
				upPointList.get(i).setRemainVal(String.valueOf(0));
				delPointList.get(j).setRemainVal(String.valueOf(remainValue));
				i++;
			}
		}

		for (int k = 0; k < upPointList.size(); k++) {

			if (upPointList.get(k).getRemainVal() == null) {
				upPointList.get(k).setBeforeVal(upPointList.get(k).getChangeVal());
			}
			System.out.println("changeVal:" + upPointList.get(k).getChangeVal() + "|" + "remainVal:" + upPointList.get(k).getRemainVal());

		}
		for (int k = 0; k < delPointList.size(); k++) {
			if (delPointList.get(k).getRemainVal() == null) {
				delPointList.get(k).setRemainVal(delPointList.get(k).getChangeVal());
			}
			System.out.println("changeVal:" + delPointList.get(k).getChangeVal() + "|" + "remainVal:" + delPointList.get(k).getRemainVal());

		}
	}

	private static List<IntegralPointLog> cleanIntegralPointList(List<IntegralPointLog> delPointList) {
		for (int k = 0; k < delPointList.size(); k++) {
			if (k < delPointList.size() - 1) {
				if (delPointList.get(k).getBeforeVal().equals(delPointList.get(k + 1).getBeforeVal())) {
					delPointList.get(k).setRemainVal("error");
				}
			} else {
				if (delPointList.get(k).getBeforeVal().equals(delPointList.get(k - 1).getBeforeVal())) {
					delPointList.get(k-1).setRemainVal("error");
				}
			}
		}
		return delPointList;
	}

}

class IntegralPointLog {
	String id;

	String changeVal;

	String remainVal;

	String beforeVal;

	public IntegralPointLog(String id, String changeVal, String beforeVal) {
		this.id = id;
		this.changeVal = changeVal;
		this.beforeVal = beforeVal;

	}

	public String getBeforeVal() {
		return beforeVal;
	}

	public void setBeforeVal(String beforeVal) {
		this.beforeVal = beforeVal == null ? null : beforeVal.trim();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getChangeVal() {
		return changeVal;
	}

	public void setChangeVal(String changeVal) {
		this.changeVal = changeVal == null ? null : changeVal.trim();
	}

	public String getRemainVal() {
		return remainVal;
	}

	public void setRemainVal(String remainVal) {
		this.remainVal = remainVal == null ? null : remainVal.trim();
	}
}