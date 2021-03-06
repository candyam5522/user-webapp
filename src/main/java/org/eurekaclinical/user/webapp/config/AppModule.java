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
package org.eurekaclinical.user.webapp.config;

/**
 *
 * @author miaoai
 */
import com.google.inject.AbstractModule;

import org.eurekaclinical.common.comm.clients.RouterTable;
import org.eurekaclinical.user.client.EurekaclinicalUserClient;
import org.eurekaclinical.user.webapp.clients.ServiceClientRouterTable;

import org.eurekaclinical.scribeupext.provider.GitHubProvider;
import org.eurekaclinical.scribeupext.provider.GlobusProvider;
import org.eurekaclinical.scribeupext.provider.Google2Provider;
import org.eurekaclinical.scribeupext.provider.SSLTwitterProvider;
import org.eurekaclinical.standardapis.props.CasEurekaClinicalProperties;

import org.eurekaclinical.user.webapp.clients.EurekaclinicalUserInternalClient;
import org.eurekaclinical.user.webapp.provider.ScribeExtGitHubProvider;
import org.eurekaclinical.user.webapp.provider.ScribeExtGlobusProvider;
import org.eurekaclinical.user.webapp.provider.ScribeExtGoogleProvider;
import org.eurekaclinical.user.webapp.provider.ScribeExtTwitterProvider;
import org.eurekaclinical.user.webapp.provider.EurekaclinicalUserInternalClientProvider;
/**
 * Configure all the web related binding for Guice and Jersey.
 *
 * @author miaoai
 */
public class AppModule extends AbstractModule  {
	private final UserWebappProperties userWebappProperties;    
        private final EurekaclinicalUserClient proxyClient;
        
	/**
	 * Inject userServiceUrl to EurekaclinicalUserClient
	 */        
	AppModule(UserWebappProperties userWebappProperties) {
		this.userWebappProperties = userWebappProperties;    
                this.proxyClient =  new EurekaclinicalUserClient(this.userWebappProperties.getUserServiceUrl());
	} 
    
	@Override
	protected void configure() {
		bind(RouterTable.class).to(ServiceClientRouterTable.class);
		bind(EurekaclinicalUserClient.class).toInstance(this.proxyClient);   

		bind(EurekaclinicalUserInternalClient.class).toProvider(EurekaclinicalUserInternalClientProvider.class);

                
		bind(UserWebappProperties.class).toInstance(this.userWebappProperties);
                bind(CasEurekaClinicalProperties.class).toInstance(this.userWebappProperties);

		bind(GitHubProvider.class).toProvider(ScribeExtGitHubProvider.class);
		bind(GlobusProvider.class).toProvider(ScribeExtGlobusProvider.class);
		bind(Google2Provider.class).toProvider(ScribeExtGoogleProvider.class);
		bind(SSLTwitterProvider.class).toProvider(ScribeExtTwitterProvider.class);            
        }    
}
