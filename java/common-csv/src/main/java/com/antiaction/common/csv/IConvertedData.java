package com.antiaction.common.csv;
/*
 * Created on 19/10/2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IConvertedData {

	public void insert(PreparedStatement stm) throws SQLException;

}
