package com.antiaction.common.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseLong implements IDataParser {

	protected boolean bAllowEmpty;

	protected ParseLong(boolean bAllowEmpty) {
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseLong getParser(boolean bAllowEmpty) {
		return new ParseLong( bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Long l = null;
		if ( data.length() > 0 ) {
			try {
				l = Long.parseLong( data );
			}
			catch (NumberFormatException e) {
				throw new CSVParserException( "Not a valid integer!", e );
			}
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty long string not allowed!" );
		}
		return new LongData( l );
	}

	public static class LongData implements IConvertedData {

		protected Long l;

		public LongData(Long i) {
			this.l = i;
		}

		public Long getData() {
			return l;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setLong( idx, l );
		}

	}

}
