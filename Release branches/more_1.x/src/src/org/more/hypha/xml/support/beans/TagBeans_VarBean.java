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
package org.more.hypha.xml.support.beans;
import java.util.HashMap;
import java.util.Map;
import org.more.FormatException;
import org.more.core.xml.XmlStackDecorator;
import org.more.core.xml.stream.EndElementEvent;
import org.more.hypha.define.beans.VariableBeanDefine;
import org.more.hypha.xml.context.XmlDefineResource;
/**
 * ���ڽ���/beans/varBean��ǩ
 * @version 2010-9-16
 * @author ������ (zyc@byshell.org)
 */
public class TagBeans_VarBean extends TagBeans_AbstractBeanDefine<VariableBeanDefine> {
    /**����{@link TagBeans_VarBean}����*/
    public TagBeans_VarBean(XmlDefineResource configuration) {
        super(configuration);
    }
    /**����VariableBeanDefine���Ͷ���*/
    protected VariableBeanDefine createDefine(XmlStackDecorator context) {
        return new VariableBeanDefine();
    }
    /**����ֵBean������*/
    public enum PropertyKey {
        value, type
    };
    /**����������xml�����Զ�Ӧ��ϵ��*/
    protected Map<Enum<?>, String> getPropertyMappings() {
        HashMap<Enum<?>, String> propertys = new HashMap<Enum<?>, String>();
        propertys.put(PropertyKey.value, "value");
        propertys.put(PropertyKey.type, "type");
        return propertys;
    }
    public void endElement(XmlStackDecorator context, String xpath, EndElementEvent event) {
        VariableBeanDefine define = this.getDefine(context);
        if (define.getType() == null)
            throw new FormatException("����VariableBeanDefine����Bean�����޷����������͡�");
        super.endElement(context, xpath, event);
    }
}