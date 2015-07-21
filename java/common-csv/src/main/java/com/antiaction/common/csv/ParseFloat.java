package com.antiaction.common.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseFloat implements IDataParser {

	protected boolean bAllowEmpty;

	protected ParseFloat(boolean bAllowEmpty) {
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseFloat getParser(boolean bAllowEmpty) {
		return new ParseFloat( bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Float f = null;
		if ( data.length() > 0 ) {
			try {
				f = Float.parseFloat( data );
			}
			catch (NumberFormatException e) {
				throw new CSVParserException( "Not a valid float!", e );
			}
		}
		else if ( !bAllowEmpty) {
			throw new CSVParserException( "Empty float string not allowed!" );
		}
		return new FloatData( f );
	}

	public static class FloatData implements IConvertedData {

		protected Float f;

		public FloatData(Float f) {
			this.f = f;
		}

		public Float getData() {
			return f;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setFloat( idx, f );
		}

	}

}
