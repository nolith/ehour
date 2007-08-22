/**
 * Created on May 12, 2007
 * Created by Thies Edeling
 * Copyright (C) 2005, 2006 te-con, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * thies@te-con.nl
 * TE-CON
 * Legmeerstraat 4-2h, 1058ND, AMSTERDAM, The Netherlands
 *
 */

package net.rrm.ehour.ui.page.admin.assignment;

import java.util.List;

import net.rrm.ehour.ui.page.admin.BaseAdminPage;
import net.rrm.ehour.ui.panel.admin.user.form.dto.UserBackingBean;
import net.rrm.ehour.ui.panel.entryselector.EntrySelectorFilter;
import net.rrm.ehour.ui.panel.entryselector.EntrySelectorPanel;
import net.rrm.ehour.ui.util.CommonStaticData;
import net.rrm.ehour.user.domain.User;
import net.rrm.ehour.user.domain.UserRole;
import net.rrm.ehour.user.service.UserService;
import net.rrm.ehour.util.EhourConstants;

import org.apache.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;


/**
 * Project assignments page
 **/

public class AssignmentAdmin extends BaseAdminPage
{
	private static final long serialVersionUID = 566527529422873370L;
	private final String			USER_SELECTOR_ID = "userSelector";
	
	@SpringBean
	private	UserService				userService;
	private EntrySelectorFilter		currentFilter;
	private	transient 	Logger		logger = Logger.getLogger(AssignmentAdmin.class);
	private ListView				userListView;
	
	/**
	 * Default constructor
	 */
	public AssignmentAdmin()
	{
		super(new ResourceModel("admin.assignment.title"), null);
		
		List<User>	users;
		users = getUsers();
		
		Fragment userListHolder = getUserListHolder(users);
		
		add(new EntrySelectorPanel(USER_SELECTOR_ID,
				new ResourceModel("admin.assignment.title"),
				userListHolder,
				getLocalizer().getString("admin.assignment.filter", this) + "..."));
	}
	
	/**
	 * Handle Ajax request
	 * @param target
	 * @param type of ajax req
	 */
	@Override
	public void ajaxRequestReceived(AjaxRequestTarget target, int type, Object param)
	{
		switch (type)
		{
			case CommonStaticData.AJAX_ENTRYSELECTOR_FILTER_CHANGE:
			{
				currentFilter = (EntrySelectorFilter)param;
	
				List<User> users = getUsers();
				userListView.setList(users);
				break;
			}
		}
	}	
	
	/**
	 * Get a the userListHolder fragment containing the listView
	 * @param users
	 * @return
	 */
	private Fragment getUserListHolder(List<User> users)
	{
		Fragment fragment = new Fragment("itemListHolder", "itemListHolder", AssignmentAdmin.this);
		
		userListView = new ListView("itemList", users)
		{
			@Override
			protected void populateItem(ListItem item)
			{
				final User		user = (User)item.getModelObject();
				final Integer	userId = user.getUserId();
				
				AjaxLink	link = new AjaxLink("itemLink")
				{
					@Override
					public void onClick(AjaxRequestTarget target)
					{
//						setEditBackingBean(new UserBackingBean(userService.getUserAndCheckDeletability(userId)));
//						switchTabOnAjaxTarget(target, 1);
					}
				};
				
				item.add(link);
				link.add(new Label("linkLabel", user.getLastName() + ", " + user.getFirstName() + (user.isActive() ? "" : "*")));				
			}
		};
		
		fragment.add(userListView);
		
		return fragment;
	}	
	
	/**
	 * Get the users from the backend
	 * @return
	 */
	private List<User> getUsers()
	{
		List<User>	users;
		
		if (currentFilter == null)
		{
			users = userService.getUsers(new UserRole(EhourConstants.ROLE_CONSULTANT));
		}
		else
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Filtering on " + currentFilter.getCleanFilterInput());
			}
			
			users = userService.getUsersByNameMatch(currentFilter.getCleanFilterInput(), true, new UserRole(EhourConstants.ROLE_CONSULTANT));
		}
		
		return users;
	}	
}
