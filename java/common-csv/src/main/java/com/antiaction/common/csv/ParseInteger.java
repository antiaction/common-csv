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

	protected boolean bAllowEmpty;

	protected ParseInteger(boolean bAllowEmpty) {
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseInteger getParser(boolean bAllowEmpty) {
		return new ParseInteger( bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Integer i = null;
		if ( data.length() > 0 ) {
			try {
				i = Integer.parseInt( data );
			}
			catch (NumberFormatException e) {
				throw new CSVParserException( "Not a valid integer!", e );
			}
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty integer string not allowed!" );
		}
		return new IntegerData( i );
	}

	public static class IntegerData implements IConvertedData {

		protected Integer i;

		public IntegerData(Integer i) {
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
