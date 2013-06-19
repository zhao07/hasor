/*
 * Copyright 2008-2009 the original author or authors.
 *
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
 */
package org.platform.view.decorate.support;
import java.io.IOException;
import javax.servlet.ServletException;
import org.platform.view.decorate.DecorateFilterChain;
import org.platform.view.decorate.DecorateServletRequest;
import org.platform.view.decorate.DecorateServletResponse;
/**
 * 
 * @version : 2013-4-13
 * @author ������ (zyc@byshell.org)
 */
class FilterChainInvocation implements DecorateFilterChain {
    private final DecorateFilterDefine[] filterDefinitions;
    private int                          index = -1;
    public FilterChainInvocation(DecorateFilterDefine[] filterDefinitions) {
        this.filterDefinitions = filterDefinitions;
    }
    public void doDecorate(DecorateServletRequest servletRequest, DecorateServletResponse servletResponse) throws IOException, ServletException {
        index++;
        //dispatch down the chain while there are more filters
        if (index < filterDefinitions.length) {
            filterDefinitions[index].doDecorate(servletRequest, servletResponse, this);
        }
    }
}