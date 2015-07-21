package com.antiaction.common.csv;
/*
 * Created on 18/10/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public interface IDataParser {

	public IConvertedData parseData(String data) throws CSVParserException;

}
