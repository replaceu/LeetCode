package com.carter.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TwoListSubtract {
	public static void main(String[] args) {
		List<IntegralPointLogDo> upPointLogList = new ArrayList<>();
		List<IntegralPointLogDo> delPointLogList = new ArrayList<>();

		upPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "50", "101"));
		upPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "10", "102"));
		upPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "100", "104"));
		upPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "200", "104"));

		delPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "-30", "201"));
		delPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "-40", "202"));
		delPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "-70", "203"));
		//delPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "-70", "203"));
		delPointLogList.add(new IntegralPointLogDo(UUID.randomUUID().toString(), "-100", "204"));
		getListSubtract(upPointLogList, delPointLogList);
	}

	private static void getListSubtract(List<IntegralPointLogDo> upPointLogList, List<IntegralPointLogDo> delPointLogList) {
		int remainValue = 0;
		int i = 0;
		int j = 0;

		//todo:得先把数据洗一下
		upPointLogList = cleanIntegralPointList(upPointLogList);
		delPointLogList = cleanIntegralPointList(delPointLogList);

		while (i < upPointLogList.size() && j < delPointLogList.size()) {
			if (upPointLogList.get(i).getRemainVal() != null && upPointLogList.get(i).getRemainVal().equals("error")) {
				i++;
				continue;
			}
			if (delPointLogList.get(j).getRemainVal() != null && delPointLogList.get(j).getRemainVal().equals("error")) {
				j++;
				continue;
			}

			Integer computeUpValue = upPointLogList.get(i).getRemainVal() == null ? Integer.valueOf(upPointLogList.get(i).getChangeVal()) : Integer.valueOf(upPointLogList.get(i).getRemainVal());
			Integer computeDelValue = delPointLogList.get(j).getRemainVal() == null ? Integer.valueOf(delPointLogList.get(j).getChangeVal()) : Integer.valueOf(delPointLogList.get(j).getRemainVal());

			remainValue = computeUpValue + computeDelValue;
			if (remainValue >= 0) {
				upPointLogList.get(i).setRemainVal(String.valueOf(remainValue));
				delPointLogList.get(j).setRemainVal(String.valueOf(0));
				j++;

			} else {
				upPointLogList.get(i).setRemainVal(String.valueOf(0));
				delPointLogList.get(j).setRemainVal(String.valueOf(remainValue));
				i++;
			}
		}

		for (int k = 0; k < upPointLogList.size(); k++) {

			if (upPointLogList.get(k).getRemainVal() == null) {
				upPointLogList.get(k).setBeforeVal(upPointLogList.get(k).getChangeVal());
			}
			System.out.println("changeVal:" + upPointLogList.get(k).getChangeVal() + "|" + "remainVal:" + upPointLogList.get(k).getRemainVal());

		}
		for (int k = 0; k < delPointLogList.size(); k++) {
			if (delPointLogList.get(k).getRemainVal() == null) {
				delPointLogList.get(k).setRemainVal(delPointLogList.get(k).getChangeVal());
			}
			System.out.println("changeVal:" + delPointLogList.get(k).getChangeVal() + "|" + "remainVal:" + delPointLogList.get(k).getRemainVal());

		}
	}

	private static List<IntegralPointLogDo> cleanIntegralPointList(List<IntegralPointLogDo> delPointLogList) {
		for (int k = 0; k < delPointLogList.size(); k++) {
			if (k < delPointLogList.size() - 1) {
				if (delPointLogList.get(k).getBeforeVal().equals(delPointLogList.get(k + 1).getBeforeVal())) {
					delPointLogList.get(k).setRemainVal("error");
				}
			} else {
				if (delPointLogList.get(k).getBeforeVal().equals(delPointLogList.get(k - 1).getBeforeVal())) {
					delPointLogList.get(k-1).setRemainVal("error");
				}
			}
		}
		return delPointLogList;
	}

}

class IntegralPointLogDo {
	String id;

	String changeVal;

	String remainVal;

	String beforeVal;

	public IntegralPointLogDo(String id, String changeVal, String beforeVal) {
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