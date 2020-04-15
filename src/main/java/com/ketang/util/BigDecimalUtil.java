package com.ketang.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
	
	/**
	 * #相加  保留2位 小数 四舍五入
	 * @param v1
	 * @param v2
	 * @return
	 */
	 public static BigDecimal add(BigDecimal v1,BigDecimal v2){
	        return v1.add(v2).setScale(2, BigDecimal.ROUND_HALF_UP);  
	 }
	 
	 /**
	  * #相乘 保留2位 小数 四舍五入
	  * @param v1
	  * @param v2
	  */
	 public static BigDecimal multiply(BigDecimal v1,BigDecimal v2){
	        return v1.multiply(v2).setScale(2, BigDecimal.ROUND_HALF_UP);  
	 }
	 /**
	  * #相乘 保留2位 小数 四舍五入
	  * @param v1
	  * @param v2
	  */
	 public static BigDecimal multiply(BigDecimal v1,Integer  v2){
	        return v1.multiply(new BigDecimal(v2)).setScale(2, BigDecimal.ROUND_HALF_UP);  
	 }
	 
	 
	 
	 
}
