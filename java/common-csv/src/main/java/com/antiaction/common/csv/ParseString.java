package com.antiaction.common.csv;
/*
 * Created on 18/10/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseString implements IDataParser {

	protected boolean bAllowEmpty;

	protected ParseString(boolean bAllowEmpty) {
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseString getParser(boolean bAllowEmpty) {
		return new ParseString( bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		if ( data.length() > 0 ) {
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty string not allowed!" );
		}
		return new StringData( data );
	}

	public static class StringData implements IConvertedData {

		protected String str;

		public StringData(String str) {
			this.str = str;
		}

		public String getData() {
			return str;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setString( idx, str );
		}

	}

}
