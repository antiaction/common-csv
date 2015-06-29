package com.antiaction.common.csv;
/*
 * Created on 29/11/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDatetime implements IDataParser {

	private int idx;

	private ParseDatetime(int idx) {
		this.idx = idx;
	}

	public static IDataParser getParser(int idx) {
		return new ParseDatetime( idx );
	}

	public IConvertedData parseData(String data) {
		IConvertedData cd = null;
		//SimpleDateFormat formatter = new java.text.SimpleDateFormat("EEE MMM d hh:mm:ss yyyy");
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date;
		Timestamp ts;
		try {
			date = formatter.parse( data );
			ts = new Timestamp( date.getTime() );
			cd = new DateTimeData( ts );
		}
		catch (ParseException e) {
		}
		return cd;
	}

	public class DateTimeData implements IConvertedData {

		Timestamp ts;

		public DateTimeData(Timestamp ts) {
			this.ts = ts;
		}

		public void insert(PreparedStatement stm) throws SQLException {
			stm.setTimestamp( idx, ts );
		}

	}

}
