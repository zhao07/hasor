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
package org.dev.toos.constcode.ui.handler;
import org.dev.toos.constcode.ui.view.ConstCodeView;
import org.eclipse.jface.action.Action;
/**
 * 
 * @version : 2013-2-2
 * @author 赵永春 (zyc@byshell.org)
 */
public class AbstractHandler extends Action {
    private ConstCodeView uiView = null; //常量管理器视图，View
    //
    //
    public AbstractHandler(String toolTipText, ConstCodeView uiView) {
        this.uiView = uiView;
        this.setToolTipText(toolTipText);
    }
    /**将模型的数据更新到视图上。*/
    public void updataView() {
        this.uiView.updataView();
    }
    /**获取视图对象*/
    public ConstCodeView getUiView() {
        return uiView;
    }
}