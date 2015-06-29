package com.antiaction.common.csv;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Created on 18/10/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class ParseInteger implements IDataParser {

	private int idx;

	private ParseInteger(int idx) {
		this.idx = idx;
	}

	public static IDataParser getParser(int idx) {
		return new ParseInteger( idx );
	}

	public IConvertedData parseData(String data) {
		IConvertedData cd = null;
		int i;
		try {
			i = Integer.parseInt( data );
			cd = new IntegerData( i );
		}
		catch (NumberFormatException e) {
		}
		return cd;
	}

	public class IntegerData implements IConvertedData {

		private int i;

		public IntegerData(int i) {
			this.i = i;
		}

		public void insert(PreparedStatement stm) throws SQLException {
			stm.setInt( idx, i );
		}

	}

}
