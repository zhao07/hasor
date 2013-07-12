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
package org.hasor.context;
/**
 * 生命周期
 * @version : 2013-7-11
 * @author 赵永春 (zyc@byshell.org)
 */
public interface Lifecycle {
    /**启动*/
    public void start();
    /**停止*/
    public void stop();
    /**是否处于运行状态*/
    public boolean isRunning();
}