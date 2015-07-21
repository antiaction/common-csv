package com.antiaction.common.csv;
/*
 * Created on 30/09/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ConvertParser {

	public static void main(String[] args) {
		String filename = "E:\\[Work]\\work-cph\\OSP-extract-070301-070401.CSV";

		File file = new File( filename );

		IDataParser[] cellparsers = new IDataParser[] {
				ParseInteger.getParser( false ),
				ParseInteger.getParser( false ),
				ParseString.getParser( false ),
				ParseString.getParser( false ),
				ParseString.getParser( false ),
				ParseInteger.getParser( false ),
				ParseString.getParser( false ),
				ParseTimestampFormat.getParser( "yyyy-MM-dd hh:mm:ss", false ),
				ParseString.getParser( false )
		};

		CSVParser cvsparser = CSVParser.load( file, ',' );

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
		}
		catch (ClassNotFoundException e) {
			System.out.println("Error: cound not find driver!");
			e.printStackTrace();
			System.exit(1);
		}
		catch (InstantiationException e) {
			System.out.println("Error: cound not instantiate driver!");
			e.printStackTrace();
			System.exit(1);
		}
		catch (IllegalAccessException e) {
			System.out.println("Error: cound not access driver!");
			e.printStackTrace();
			System.exit(1);
		}

		Connection conn = null;
		Properties connprops = null;
		//String insertSql;
		ResultSet rs = null;
		PreparedStatement insertStm = null;

		connprops = new Properties();
		connprops.setProperty( "user", "sa" );
		connprops.setProperty( "password", "sa" );
		try {
			conn = DriverManager.getConnection( "jdbc:jtds:sqlserver://localhost:1433/cpm_abo;user=sa;password=sa" );
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}

		String sql = "";
		sql += "INSERT INTO translist(nr, kundenNr, Nachname, PhBez, GerBez, BenutzerNr, Bnachname, Ztpunkt, str) ";
		sql += "VALUEs (?, ?, ? ,? ,? ,? ,?, ?, ?);";
		try {
			//insertStm = conn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS );
			insertStm = conn.prepareStatement( sql );
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		String tmpStr;
		IConvertedData cd;

		System.out.println( "..." );

		try {
			conn.setAutoCommit( true );

			Iterator iter = cvsparser.iterator();
			if ( iter != null ) {
				while ( iter.hasNext() ) {
					List array = (List)iter.next();
					/*
					for ( int i=0; i<array.size(); ++i ) {
						System.out.println( (String)array.get( i ) );
					}
					System.out.println( "--" );
					*/

					insertStm.clearParameters();

					for ( int i=0; i<array.size(); ++i ) {
						tmpStr = (String)array.get( i );
						cd = cellparsers[ i ].parseData( tmpStr );
						try {
							cd.insert( insertStm, i + 1 );
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
					}
					insertStm.executeUpdate();

					/*
					rs = insertStm.getGeneratedKeys();
					if ( rs != null && rs.next() ) {
						id = rs.getLong( 1 );
					}
					*/

					//conn.commit();

					//System.out.println( array.size() );
				}
			}
		}
		catch (CSVParserException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
