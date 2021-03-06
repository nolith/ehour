/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package net.rrm.ehour.ui.common.report.excel;

import net.rrm.ehour.ui.common.report.excel.CellStyleElement.*;

/**
 * Created on Mar 25, 2009, 5:13:17 PM
 * @author Thies Edeling (thies@te-con.nl) 
 *
 */
public enum StaticCellStyle implements CellStyle {
	BOLD(new BoldFont()),
	NORMAL(new NormalFont()),
	DATE(new DateValue()),
	DIGIT(new DigitValue()),
	BORDER_THIN(new BorderThin()),
	BORDER_SOUTH(new BorderSouth()),
	BORDER_SOUTH_THIN(new BorderSouthThin()),
	BORDER_NORTH_THIN(new BorderNorthThin()),
	BORDER_NORTH(new BorderNorth()),
	HEADER(new Header());
	
	private CellStylePopulator cellStylePopulator;
	
	StaticCellStyle(CellStylePopulator cellStylePopulator)
	{
		this.cellStylePopulator = cellStylePopulator;
	}
	
	@Override
    public CellStylePopulator getCellStylePopulator()
	{
		return cellStylePopulator;
	}
}
