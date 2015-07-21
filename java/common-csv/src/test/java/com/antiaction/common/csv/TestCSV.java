package com.antiaction.common.csv;
/*
 * Created on 30/08/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class TestCSV {

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
