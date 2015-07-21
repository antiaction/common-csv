package com.antiaction.common.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseDouble implements IDataParser {

	protected boolean bAllowEmpty;

	protected ParseDouble(boolean bAllowEmpty) {
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseDouble getParser(boolean bAllowEmpty) {
		return new ParseDouble( bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Double d = null;
		if ( data.length() > 0 ) {
			try {
				d = Double.parseDouble( data );
			}
			catch (NumberFormatException e) {
				throw new CSVParserException( "Not a valid double!", e );
			}
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty double string not allowed!" );
		}
		return new DoubleData( d );
	}

	public static class DoubleData implements IConvertedData {

		protected Double d;

		public DoubleData(Double d) {
			this.d = d;
		}

		public Double getData() {
			return d;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setDouble( idx, d );
		}

	}
}
