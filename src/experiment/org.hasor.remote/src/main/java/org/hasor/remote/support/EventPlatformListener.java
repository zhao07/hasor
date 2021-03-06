/*
 * Copyright 2008-2009 the original 赵永春(zyc@hasor.net).
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
package org.hasor.remote.support;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hasor.HasorFramework;
import org.hasor.binder.ApiBinder;
import org.hasor.context.AppContext;
import org.hasor.context.PlatformListener;
import org.hasor.remote.EventListener;
import org.hasor.remote.EventManager;
import org.hasor.remote.Listener;
import org.hasor.startup.PlatformExt;
import org.more.util.ArrayUtils;
import org.more.util.StringUtils;
/**
 * 事件服务。启动级别：Lv0
 * @version : 2013-4-8
 * @author 赵永春 (zyc@byshell.org)
 */
@PlatformExt(displayName = "EventModuleServiceListener", description = "org.platform.event软件包功能支持。", startIndex = PlatformExt.Lv_0)
public class EventPlatformListener implements PlatformListener {
    private EventManager                    eventManager  = null;
    private List<Class<? extends Listener>> eventListener = null;
    /**初始化.*/
    @Override
    public void initialize(ApiBinder event) {
        event.getGuiceBinder().bind(EventManager.class).to(DefaultEventManager.class).asEagerSingleton();
    }
    @Override
    public void initialized(AppContext appContext) {
        this.eventManager = appContext.getInstance(EventManager.class);
        if (this.eventManager instanceof ManagerLife)
            ((ManagerLife) this.eventManager).initLife(appContext);
        //1.获取
        this.eventListener = new ArrayList<Class<? extends Listener>>();
        Set<Class<?>> listenerSet = appContext.getClassSet(EventListener.class);
        if (listenerSet == null)
            return;
        for (Class<?> cls : listenerSet) {
            if (Listener.class.isAssignableFrom(cls) == false) {
                HasorFramework.warning("loadListener : not implemented EventListener of type %s.", cls);
            } else {
                HasorFramework.info("find listener %s.", cls);
                this.eventListener.add((Class<? extends Listener>) cls);
            }
        }
        this.loadListener(appContext);
        this.eventManager.throwEvent(EventManager.OnStart);
        HasorFramework.info("EventManager is started.");
    }
    //
    /*装载Listener*/
    protected void loadListener(AppContext appContext) {
        for (Class<? extends Listener> listenerType : eventListener) {
            try {
                EventListener annoListener = listenerType.getAnnotation(EventListener.class);
                Listener eventListener = (Listener) appContext.getInstance(listenerType);
                String[] vals = annoListener.value();
                if (ArrayUtils.isBlank(vals)) {
                    HasorFramework.warning("missing eventType at listener %s.", new Object[] { vals });
                    continue;
                }
                for (String eventType : vals) {
                    if (StringUtils.isBlank(eventType) == true)
                        continue;
                    HasorFramework.info("listener %s is listening on %s.", listenerType, eventType);
                    this.eventManager.addEventListener(eventType, eventListener);
                }
            } catch (Exception e) {
                HasorFramework.warning("addEventListener error.%s", e);
            }
        }
    }
    @Override
    public void destroy(AppContext appContext) {
        this.eventManager.throwEvent(EventManager.OnDestroy);
        if (this.eventManager instanceof ManagerLife)
            ((ManagerLife) this.eventManager).destroyLife(appContext);
        HasorFramework.info("EventManager is destroy.");
    }
}