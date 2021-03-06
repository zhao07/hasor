/*
 * Copyright 2008-2009 the original ������(zyc@hasor.net).
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
package net.hasor.plugins.result;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import net.hasor.core.ApiBinder;
import net.hasor.core.AppContext;
import net.hasor.core.AppContextAware;
import net.hasor.plugins.controller.interceptor.ControllerInterceptor;
import net.hasor.plugins.controller.interceptor.ControllerInvocation;
/**
 * 
 * @version : 2013-8-11
 * @author ������ (zyc@hasor.net)
 */
class ResultCaller_Controller extends ControllerInterceptor implements AppContextAware {
    private AppContext                          appContext = null;
    private Map<Class<?>, Class<ResultProcess>> defineMap  = null;
    //
    public ResultCaller_Controller(ApiBinder apiBinder, Map<Class<?>, Class<ResultProcess>> defineMap) {
        this.defineMap = defineMap;
        apiBinder.registerAware(this);
    }
    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }
    /***/
    public Object invoke(ControllerInvocation invocation) throws Throwable {
        Method javaMethod = invocation.getControllerMethod();
        Annotation[] annos = javaMethod.getAnnotations();
        if (annos == null)
            return invocation.proceed();
        //
        for (Annotation anno : annos) {
            Class<?> resultType = anno.annotationType();
            if (resultType != null) {
                Class<ResultProcess> processType = this.defineMap.get(resultType);
                if (processType != null) {
                    Object returnData = invocation.proceed();
                    ResultProcess process = this.appContext.getInstance(processType);
                    process.process(invocation.getRequest(), invocation.getResponse(), anno, returnData);
                    return returnData;
                }
            }
        }
        return invocation.proceed();
    }
}