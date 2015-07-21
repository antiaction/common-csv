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
public class ParseTimestampFormat implements IDataParser {

	protected SimpleDateFormat dateFormat;

	protected boolean bAllowEmpty;

	protected ParseTimestampFormat(String dateFormatStr, boolean bAllowEmpty) {
		this.dateFormat = new SimpleDateFormat( dateFormatStr );
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseTimestampFormat getParser(String dateFormatStr, boolean bAllowEmpty) {
		return new ParseTimestampFormat( dateFormatStr, bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Date date;
		Timestamp ts = null;
		if ( data.length() > 0 ) {
			try {
				date = dateFormat.parse( data );
				ts = new Timestamp( date.getTime() );
			}
			catch (ParseException e) {
				throw new CSVParserException( "Not a valid date format!", e );
			}
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty timestamp string not allowed!" );
		}
		return new DateTimeData( ts );
	}

	public static class DateTimeData implements IConvertedData {

		protected Timestamp ts;

		public DateTimeData(Timestamp ts) {
			this.ts = ts;
		}

		public Timestamp getData() {
			return ts;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setTimestamp( idx, ts );
		}

	}

}
