package com.antiaction.common.csv;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ParseBoolean implements IDataParser {

	protected Map boolStrMap;

	protected boolean bAllowEmpty;

	protected ParseBoolean(Map boolStrMap, boolean bAllowEmpty) {
		this.boolStrMap = new HashMap();
		this.bAllowEmpty = bAllowEmpty;
		Iterator iter = boolStrMap.entrySet().iterator();
		Entry entry;
		while ( iter.hasNext() ) {
			entry = (Entry)iter.next();
			this.boolStrMap.put( ((String)entry.getKey()).toLowerCase(), entry.getValue() );
		}
	}

	public static ParseBoolean getParser(Map boolStrMap, boolean bAllowEmpty) {
		return new ParseBoolean( boolStrMap, bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		Boolean b = null;
		if ( data.length() > 0 ) {
			b = (Boolean)boolStrMap.get( data.toLowerCase() );
			if ( b == null ) {
				throw new CSVParserException( "Not a valid boolean string!" );
			}
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty boolean string not allowed!" );
		}
		return new BooleanData( b );
	}

	public static class BooleanData implements IConvertedData {

		protected Boolean b;

		public BooleanData(Boolean b) {
			this.b = b;
		}

		public Boolean getData() {
			return b;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setBoolean( idx, b );
		}

	}

}
