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

	private ParseString() {
	}

	public static IDataParser getParser() {
		return new ParseString();
	}

	public IConvertedData parseData(String data) {
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
