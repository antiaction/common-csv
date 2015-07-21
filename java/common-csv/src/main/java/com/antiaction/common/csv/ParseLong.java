package com.antiaction.common.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseLong implements IDataParser {

	private ParseLong() {
	}

	public static IDataParser getParser() {
		return new ParseLong();
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		long l;
		try {
			l = Long.parseLong( data );
		}
		catch (NumberFormatException e) {
			throw new CSVParserException( "Not a valid integer!", e );
		}
		return new LongData( l );
	}

	public static class LongData implements IConvertedData {

		protected long l;

		public LongData(long i) {
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
