package com.antiaction.common.csv;
/*
 * Created on 07/02/2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseHexString implements IDataParser {

	protected boolean bAllowEmpty;

	protected ParseHexString(boolean bAllowEmpty) {
		this.bAllowEmpty = bAllowEmpty;
	}

	public static ParseHexString getParser(boolean bAllowEmpty) {
		return new ParseHexString( bAllowEmpty );
	}

	public IConvertedData parseData(String data) throws CSVParserException {
		byte[] bytes = null;
		if ( data.length() > 0 ) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int i = 0;
			if ( data.startsWith( "0x" ) ) {
				i += 2;
			}
			byte b;
			char c;
			while ( i < (data.length() - 1) ) {
				c = data.charAt( i );
				if ( c >= '0' && c <= '9' ) {
					b = (byte)((c - '0') << 4);
				}
				else {
					Character.toLowerCase( c );
					if ( c >= 'a' && c <= 'f' ) {
						b = (byte)((c - 'a' + 10) << 4);
					}
					else {
						throw new CSVParserException( "Not a valid hexadecial string!" );
					}
				}
				++i;
				c = data.charAt( i );
				if ( c >= '0' && c <= '9' ) {
					b |= (byte)(c - '0');
				}
				else {
					Character.toLowerCase( c );
					if ( c >= 'a' && c <= 'f' ) {
						b |= (byte)(c - 'a' + 10);
					}
					else {
						throw new CSVParserException( "Not a valid hexadecial string!" );
					}
				}
				++i;
				out.write( b );
			}
			if ( i < data.length() ) {
				throw new CSVParserException( "Not a valid hexadecial string!" );
			}
			bytes = out.toByteArray();
		}
		else if ( !bAllowEmpty ) {
			throw new CSVParserException( "Empty hexadecimal string not allowed!" );
		}
		return new BytesData( bytes );
	}

	public static class BytesData implements IConvertedData {

		protected byte[] bytes;

		public BytesData(byte[] bytes) {
			this.bytes = bytes;
		}

		public byte[] getData() {
			return bytes;
		}

		public void insert(PreparedStatement stm, int idx) throws SQLException {
			stm.setBytes( idx, bytes );
		}

	}

}
