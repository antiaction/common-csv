package com.antiaction.common.csv;
/*
 * Created on 30/08/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVParser {

	private File csvfile = null;

	private char delimiter;

	public static CSVParser load(File csvfile, char delimiter) {
		if ( !csvfile.exists() ) {
			return null;
		}
		CSVParser parser = new CSVParser();
		parser.csvfile = csvfile;
		parser.delimiter = delimiter;
		return parser;
	}

	public Iterator iterator() {
		Iterator iter = null;
		try {
			RandomAccessFile ram = new RandomAccessFile( csvfile, "r" );
			iter = new LineIterator( ram );
		}
		catch (FileNotFoundException e) {
		}
		return iter;
	}

	class LineIterator implements Iterator {

		final static int S_START = 0;
		final static int S_QUOTE = 1;
		final static int S_DELIMITER = 2;
		final static int S_STRING = 3;

		RandomAccessFile ram;

		public LineIterator(RandomAccessFile ram) {
			this.ram = ram;
		}

		String currStr = null;

		public boolean hasNext() {
			if ( currStr == null ) {
				try {
					if ( ram != null ) {
						currStr = ram.readLine();
						if ( currStr == null ) {
							ram.close();
						}
					}
				}
				catch (IOException e) {
				}
			}
			return currStr != null;
		}

		public Object next() {
			if ( currStr == null ) {
				try {
					if ( ram != null ) {
						currStr = ram.readLine();
						if ( currStr == null ) {
							ram.close();
						}
					}
				}
				catch (IOException e) {
				}
			}
			if ( currStr != null ) {
				List array = new ArrayList();

				int idx = 0;
				int sidx = 0;
				char c;
				char qc = 0;
				boolean b = true;
				int state = S_START;
				if ( currStr.length() == 0 ) {
					b = false;
				}
				while ( b ) {
					if ( idx < currStr.length() ) {
						c = currStr.charAt( idx );
						switch ( state ) {
						case S_START:
							switch ( c ) {
							case '"':
								qc = c;
								sidx = ++idx;
								state = S_QUOTE;
								break;
							case '\'':
								qc = c;
								sidx = ++idx;
								state = S_QUOTE;
								break;
							default:
								if ( Character.isWhitespace( c ) ) {
									sidx = ++idx;
								}
								else {
									sidx = idx++;
									state = S_STRING;
								}
								break;
							}
							break;
						case S_QUOTE:
							if ( c != qc ) {
								++idx;
							}
							else {
								array.add( currStr.substring( sidx, idx ).trim() );
								sidx = ++idx;
								state = S_DELIMITER;
							}
							break;
						case S_DELIMITER:
							if ( c != delimiter ) {
								sidx = ++idx;
							}
							else {
								sidx = ++idx;
								state = S_START;
							}
							break;
						case S_STRING:
							if ( c != delimiter ) {
								++idx;
							}
							else {
								array.add( currStr.substring( sidx, idx ).trim() );
								++idx;
								state = S_START;
							}
							break;
						}
					}
					else {
						if ( sidx < idx ) {
							array.add( currStr.substring( sidx, idx ).trim() );
						}
						else {
							array.add( "" );
						}
						b = false;
					}
				}

				currStr = null;
				return array;
			}
			throw new IllegalStateException();
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		String filename = "E:\\[Work]\\work-EAS\\prebooking\\PB-070829.CSV";

		File file = new File( filename );

		CSVParser cvsparser = CSVParser.load( file, ',' );

		Iterator iter = cvsparser.iterator();
		if ( iter != null ) {
			while ( iter.hasNext() ) {
				List array = (List)iter.next();
				for ( int i=0; i<array.size(); ++i ) {
					System.out.println( (String)array.get( i ) );
				}
				System.out.println( "--" );
			}
		}
	}

}
