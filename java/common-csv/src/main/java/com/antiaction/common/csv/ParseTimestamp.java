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

// "EEE MMM d hh:mm:ss yyyy"
// "yyyy-MM-dd hh:mm:ss"
public class ParseTimestamp implements IDataParser {

	protected SimpleDateFormat dateFormat;

	private ParseTimestamp(String dateFormatStr) {
		dateFormat = new SimpleDateFormat( dateFormatStr );
	}

	public static IDataParser getParser(String dateFormatStr) {
		return new ParseTimestamp( dateFormatStr );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Date date;
		try {
			date = dateFormat.parse( data );
		}
		catch (ParseException e) {
			throw new CSVParserException( "Not a valid date format!", e );
		}
		return new DateTimeData( new Timestamp( date.getTime() ) );
	}

	public static class DateTimeData implements IConvertedData {

		protected Timestamp ts;

		public DateTimeData(Timestamp ts) {
			this.ts = ts;
		}

		public Object getData() {
			return ts;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setTimestamp( idx, ts );
		}

	}

}
