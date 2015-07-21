package com.antiaction.common.csv;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ParseDecimalFormat implements IDataParser {

	protected DecimalFormat decimalFormat;

	protected boolean bAllowEmpty;

	protected ParseDecimalFormat(Locale locale, boolean bAllowEmpty) {
		this.decimalFormat = (DecimalFormat)NumberFormat.getInstance( locale );
		this.decimalFormat.setParseBigDecimal( true );
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseDecimalFormat getParser(Locale locale, boolean bAllowEmpty) {
		return new ParseDecimalFormat( locale, bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		BigDecimal bd = null;
		if ( data.length() > 0 ) {
			try {
				bd = (BigDecimal) decimalFormat.parse( data );
			}
			catch (ParseException e) {
				throw new CSVParserException( "Not a valid decimal format!", e );
			}
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty decimal string not allowed!" );
		}
		return new BigDecimalData( bd );
	}

	public static class BigDecimalData implements IConvertedData {

		protected BigDecimal bd;

		public BigDecimalData(BigDecimal bd) {
			this.bd = bd;
		}

		public BigDecimal getData() {
			return bd;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setBigDecimal( idx, bd );
		}

	}

}
