package com.carter.arithmetic;

public class GetMaxAvgList {
    public static void main(String[] args) {
        double[] arr = {2,2,3};
        String result = getMaxAvgSubList(3, 2, arr);
        System.out.println(result);
    }

    public static String getMaxAvgSubList(int n,int l,double[] numbers){
        double minAvg = Integer.MAX_VALUE;
        double maxAvg = Integer.MIN_VALUE;
        for (double num : numbers) {
            minAvg = Math.min(num,minAvg);
            maxAvg = Math.max(num,maxAvg);
        }
        double diff = maxAvg/Math.pow(10,10);

        int[] ans = new int[2];
        while (maxAvg-minAvg>=diff){
            double midAvg = (minAvg + maxAvg) / 2;
            if (check(n,l,numbers,midAvg,ans)){
                minAvg = midAvg;
            }else {
                maxAvg = midAvg;
            }
        }
        return ans[0]+" "+ans[1];
    }

    private static boolean check(int n, int l, double[] numbers, double avg, int[] ans) {
        double fact =1;
        for (int i = 0; i < l; i++) {
            fact*= numbers[i] / avg;
        }
        if (fact >= 1) {
            ans[0] = 0;
            ans[1] = l;
            return true;
        }
        double preFact = 1;
        double minPreFact = Integer.MAX_VALUE;
        int minPreFactEnd = 0;
        for (int i = l; i < n; i++) {
            fact *= numbers[i] / avg;
            preFact *= numbers[i - l] / avg;
            if (preFact < minPreFact) {
                minPreFact = preFact;
                minPreFactEnd = i - l;
            }
            if (fact / minPreFact >= 1) {
                ans[0] = minPreFactEnd + 1;
                ans[1] = i - minPreFactEnd;
                return true;
            }
        }
        return false;
    }
}
