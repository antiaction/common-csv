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

	private ParseInteger() {
	}

	public static IDataParser getParser() {
		return new ParseInteger();
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		int i;
		try {
			i = Integer.parseInt( data );
		}
		catch (NumberFormatException e) {
			throw new CSVParserException( "Not a valid integer!", e );
		}
		return new IntegerData( i );
	}

	public static class IntegerData implements IConvertedData {

		protected int i;

		public IntegerData(int i) {
			this.i = i;
		}

		public Integer getData() {
			return i;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setInt( idx, i );
		}

	}

}
