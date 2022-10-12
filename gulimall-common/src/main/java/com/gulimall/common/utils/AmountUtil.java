package com.gulimall.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtil {
    public static void main(String[] args) {
        System.out.println(exchangeAmount1divideAmount2RateInAmount3Up("3", "100", "88"));
    }

    public static String convertFenToYuan(String fen) {
        BigDecimal bd = new BigDecimal(fen);
        return bd.movePointLeft(2).setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public static String convertYuanToFen(String yuan) {
        BigDecimal bd = new BigDecimal(yuan);
        return bd.setScale(2, RoundingMode.HALF_UP).movePointRight(2).toPlainString();
    }

    /**
     * 比较金额大小
     * @param amount1
     * @param amount2
     * @return -1 amount1小于amount2
     * @return 0 amount1等于amount2
     * @return 1 amount1大于amount2
     */
    public static int compareAmount(String amount1, String amount2) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        return bd1.compareTo(bd2);
    }

    /**
     * 是否满足满减条件
     * @param amount1 原价
     * @param amount2 满多少价格
     * @return true or false
     */
    public static boolean canDeduction(String amount1, String amount2) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        return bd2.compareTo(bd1) >= 0 ? true : false;
    }

    /**
     * 减法 -
     * @param amount1
     * @param amount2
     * @return
     */
    public static String subtractFenRetFen(String amount1, String amount2) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        return bd1.subtract(bd2).toPlainString();
    }

    /**
     * 加法 +
     * @param amount1
     * @param amount2
     * @return
     */
    public static String addFenRetFen(String amount1, String amount2) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        return bd1.add(bd2).toPlainString();
    }

    /**
     * 乘法四舍五入无小数 *
     * @param amount1
     * @param amount2
     * @return
     */
    public static String multiplyFenRetFen(String amount1, String amount2) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        return bd1.multiply(bd2).setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 除法四舍五入无小数 /
     * @param amount1
     * @param amount2
     * @return
     */
    public static String divideFenRetFen(String amount1, String amount2) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        return bd1.divide(bd2).setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 除法两位小数乘以第三个参数四舍五入无小数返回分 /
     * @param amount1
     * @param amount2
     * @param amount3
     * @return amount1/amount2*amount3
     */
    public static String exchangeAmount1divideAmount2RateInAmount3(String amount1, String amount2, String amount3) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        BigDecimal bd3 = new BigDecimal(amount3);
        if (bd2.compareTo(bd3) == 0) { return bd1.toPlainString(); }
        return bd1.divide(bd2, 65535, RoundingMode.HALF_UP).multiply(bd3).setScale(0, RoundingMode.HALF_UP).toPlainString();
    }

    /**
     * 除法两位小数乘以第三个参数向上取整无小数返回分 /
     * @param amount1
     * @param amount2
     * @param amount3
     * @return amount1/amount2*amount3
     */
    public static String exchangeAmount1divideAmount2RateInAmount3Up(String amount1, String amount2, String amount3) {
        BigDecimal bd1 = new BigDecimal(amount1);
        BigDecimal bd2 = new BigDecimal(amount2);
        BigDecimal bd3 = new BigDecimal(amount3);
        if (bd2.compareTo(bd3) == 0) { return bd1.toPlainString(); }
        return bd1.divide(bd2, 65535, RoundingMode.UP).multiply(bd3).setScale(0, RoundingMode.UP).toPlainString();
    }
}
