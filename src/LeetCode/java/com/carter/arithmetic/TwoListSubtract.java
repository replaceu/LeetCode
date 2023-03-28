package com.carter.arithmetic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 该算法旨在揭示两个数组的数据相减，类似于窗口函数
 */

public class TwoListSubtract {
	public static void main(String[] args) {
		List<IntegralPointLog> upPointList = new ArrayList<>();
		List<IntegralPointLog> delPointList = new ArrayList<>();

		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "40", "101", new Date(2021, 9, 27)));
		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "60", "102", new Date(2021, 9, 29)));

		//upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "100", "103", new Date(2022, 1, 27)));
		//upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "200", "104", new Date(2021, 7, 20)));

		//delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-90", "201", new Date(2021, 11, 12)));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-50", "203", new Date(2022, 10, 13)));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-5", "202", new Date(2022, 10, 28)));

		upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "100", "103", new Date(2021, 11, 27)));
		//upPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "200", "104", new Date(2021, 7, 20)));

		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-20", "201", new Date(2021, 10, 12)));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-50", "203", new Date(2021, 10, 13)));
		delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-5", "202", new Date(2021, 11, 13)));

		//delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-70", "203"));
		//delPointList.add(new IntegralPointLog(UUID.randomUUID().toString(), "-100", "204", new Date(2022, 11, 20)));

		Date today = new Date(2022, 12, 1);
		//getListSubtract(upPointList, delPointList, today);
	}

	private static void getListSubtract(List<IntegralPointLog> upPointList, List<IntegralPointLog> delPointList) {
		Date today = new Date(2022, 11, 30);

		List<IntegralPointLog> toInsertList = new ArrayList<>();
		int remainValue = 0;
		int i = 0;
		int j = 0;

		//todo:得先把数据洗一下
		upPointList = cleanIntegralPointList(upPointList);
		upPointList = upPointList.stream().sorted(Comparator.comparing(IntegralPointLog::getDate)).collect(Collectors.toList());
		delPointList = cleanIntegralPointList(delPointList);
		delPointList = delPointList.stream().sorted(Comparator.comparing(IntegralPointLog::getDate)).collect(Collectors.toList());

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
				Date startDate = upPointList.get(i).getDate();
				Date endDate = new Date(startDate.getYear() + 1, startDate.getMonth(), startDate.getDate());
				boolean notExpired = checkDataExpired(delPointList.get(j).getDate(), startDate, endDate);
				if (notExpired) {
					upPointList.get(i).setRemainVal(String.valueOf(remainValue));
					delPointList.get(j).setRemainVal(String.valueOf(0));
					j++;
				} else {
					upPointList.get(i).setRemainVal(String.valueOf(0));

					if (endDate.before(today)) {

						if (endDate.getTime() < today.getTime()) {

							IntegralPointLog toInsetLog = new IntegralPointLog();
							toInsetLog.setRemainVal(String.valueOf(0));
							toInsetLog.setId(UUID.randomUUID().toString());
							toInsetLog.setChangeVal(String.valueOf(-computeUpValue));
							toInsetLog.setDate(endDate);
							toInsertList.add(toInsetLog);
						}
						i++;
					}
				}
			} else {
				Date startDate = upPointList.get(i).getDate();

				Date endDate = new Date(startDate.getYear() + 1, startDate.getMonth(), startDate.getDate());
				boolean notExpired = checkDataExpired(delPointList.get(j).getDate(), startDate, endDate);
				if (notExpired) {
					upPointList.get(i).setRemainVal(String.valueOf(0));
					delPointList.get(j).setRemainVal(String.valueOf(remainValue));
					i++;
				} else {
					upPointList.get(i).setRemainVal(String.valueOf(0));

					if (endDate.before(today)) {

						if (endDate.getTime() < today.getTime()) {

							IntegralPointLog toInsetLog = new IntegralPointLog();
							toInsetLog.setRemainVal(String.valueOf(0));
							toInsetLog.setId(UUID.randomUUID().toString());
							toInsetLog.setChangeVal(String.valueOf(-computeUpValue));
							toInsetLog.setDate(endDate);
							toInsertList.add(toInsetLog);
						}
						i++;
					}

				}
			}

			for (int k = 0; k < upPointList.size(); k++) {

				if (upPointList.get(k).getRemainVal() == null) {
					upPointList.get(k).setRemainVal(upPointList.get(k).getChangeVal());
				}
				System.out.println("id:" + upPointList.get(k).getId() + " |" + "changeVal:" + upPointList.get(k).getChangeVal() + " |" + "remainVal:" + upPointList.get(k).getRemainVal() + " |" + "date:" + upPointList.get(k).getDate().getYear() + "-" + upPointList.get(k).getDate().getMonth() + "-" + upPointList.get(k).getDate().getDate());

			}
			for (int k = 0; k < delPointList.size(); k++) {
				if (delPointList.get(k).getRemainVal() == null) {
					delPointList.get(k).setRemainVal(delPointList.get(k).getChangeVal());
				}
				System.out.println("id:" + delPointList.get(k).getId() + " |" + "changeVal:" + delPointList.get(k).getChangeVal() + " |" + "remainVal:" + delPointList.get(k).getRemainVal() + " |" + "date:" + delPointList.get(k).getDate().getYear() + "-" + delPointList.get(k).getDate().getMonth() + "-" + delPointList.get(k).getDate().getDate());

			}

			for (IntegralPointLog insert : toInsertList) {
				System.out.println("新增：" + "id:" + insert.getId() + " |" + "changeVal:" + insert.getChangeVal() + " |" + "remainVal:" + insert.getRemainVal() + "|" + "date:" + insert.getDate().getYear() + "-" + insert.getDate().getMonth() + "-" + insert.getDate().getDate());
			}
		}
	}

	private static boolean checkDataExpired(Date date, Date startDate, Date endDate) {
		if (date.after(startDate) && date.before(endDate)) { return true; }
		return false;
	}

	private static List<IntegralPointLog> cleanIntegralPointList(List<IntegralPointLog> delPointList) {
		for (int k = 0; k < delPointList.size(); k++) {
			if (k < delPointList.size() - 1) {
				if (delPointList.get(k).getBeforeVal().equals(delPointList.get(k + 1).getBeforeVal())) {
					delPointList.get(k).setRemainVal("error");
				}
			} else {
				if (delPointList.get(k).getBeforeVal().equals(delPointList.get(k - 1).getBeforeVal())) {
					delPointList.get(k - 1).setRemainVal("error");
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

	Date date;

	public IntegralPointLog() {
	}

	public IntegralPointLog(String id, String changeVal, String beforeVal, Date date) {
		this.id = id;
		this.changeVal = changeVal;
		this.beforeVal = beforeVal;
		this.date = date;

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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