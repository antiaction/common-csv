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

	private int idx;

	private ParseString(int idx) {
		this.idx = idx;
	}

	public static IDataParser getParser(int idx) {
		return new ParseString( idx );
	}

	public IConvertedData parseData(String data) {
		return new StringData( data );
	}

	public class StringData implements IConvertedData {

		private String str;

		public StringData(String str) {
			this.str = str;
		}

		public void insert(PreparedStatement stm) throws SQLException {
			stm.setString( idx, str );
		}

	}

}
