package com.prueba.citasweb.models.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormaterUtil {
	
	Calendar calendar;
	
	public DateFormaterUtil() {
		this.calendar = Calendar.getInstance();
	}
	
	public String dateToString(Date fecha) {
		
		SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
		return formateador.format(fecha);
	
	}
	
	public int getHourOfDate(Date fecha) {
		this.calendar.setTime(fecha);
		return this.calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	
}
