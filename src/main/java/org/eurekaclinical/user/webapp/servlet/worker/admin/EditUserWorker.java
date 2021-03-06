/*
 * #%L
 * Eureka! Clinical User Webapp
 * %%
 * Copyright (C) 2016 Emory University
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.eurekaclinical.user.webapp.servlet.worker.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eurekaclinical.common.comm.clients.ClientException;
import org.eurekaclinical.common.comm.Role;

import org.eurekaclinical.user.client.comm.User;

import org.eurekaclinical.user.webapp.clients.EurekaclinicalUserInternalClient;
import org.eurekaclinical.user.webapp.servlet.worker.ServletWorker;
/**
 *
 * @author miaoai
 */
public class EditUserWorker implements ServletWorker {

	private final EurekaclinicalUserInternalClient servicesClient;

	public EditUserWorker(EurekaclinicalUserInternalClient inServicesClient) {
		this.servicesClient = inServicesClient;
	}

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String id = req.getParameter("id");
		try {
			User me = this.servicesClient.getMe();
			User user = this.servicesClient.getUserById(Long.valueOf(id)); 
			List<Role> roles = this.servicesClient.getRoles();

			req.setAttribute("me", me);
			req.setAttribute("roles", roles);
			req.setAttribute("currentUser", user);
			req.getRequestDispatcher("/protected/edit_user.jsp").forward(req,
					resp);
		} catch (ClientException ex) {
			throw new ServletException("Error getting user information", ex);
		}
	}
}
