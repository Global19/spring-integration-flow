/*
 * Copyright 2002-2011 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.integration.flow.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.xml.IntegrationNamespaceUtils;
import org.springframework.integration.flow.Flow;
import org.springframework.integration.flow.config.FlowUtils;
import org.w3c.dom.Element;

/**
 * 
 * @author David Turanski
 * 
 */
public class FlowParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		BeanDefinitionBuilder flowBuilder = BeanDefinitionBuilder.genericBeanDefinition(Flow.class);
		String id = element.getAttribute("id");
		BeanDefinitionBuilder flowOutputChannelBuilder = BeanDefinitionBuilder
				.genericBeanDefinition(PublishSubscribeChannel.class);
		String beanName = FlowUtils.registerBeanDefinition(flowOutputChannelBuilder.getBeanDefinition(),
				parserContext.getRegistry());
		flowBuilder.addPropertyReference("flowOutputChannel", beanName);
		
		IntegrationNamespaceUtils.setValueIfAttributeDefined(flowBuilder, element, "referenced-bean-locations");
		IntegrationNamespaceUtils.setReferenceIfAttributeDefined(flowBuilder, element, "properties");
		IntegrationNamespaceUtils.setValueIfAttributeDefined(flowBuilder, element, "help");
		
		BeanDefinition beanDefinition = flowBuilder.getBeanDefinition();
		parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

		return beanDefinition;
	}
}
