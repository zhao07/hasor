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
package org.more.webui.render.select;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.more.util.BeanUtil;
import org.more.webui.components.UISelectInput;
import org.more.webui.context.ViewContext;
import org.more.webui.tag.TemplateBody;
import freemarker.template.TemplateException;
/**
 * ��ѡ�������齨��Ⱦ��һ��checkѡ���
 * <br><b>�ͻ���ģ��</b>��UICheckManySelectInput��UICheckManySelectInput.js��
 * @version : 2012-5-18
 * @author ������ (zyc@byshell.org)
 */
public class CheckManySelectInputRender<T extends UISelectInput> extends AbstractSelectInputRender<T> {
    @Override
    public String getClientType() {
        return "UICheckManySelectInput";
    }
    @Override
    public String tagName(ViewContext viewContext, T component) {
        return "ul";
    }
    @Override
    public Map<String, Object> tagAttributes(ViewContext viewContext, T component) {
        Map<String, Object> attr = super.tagAttributes(viewContext, component);
        attr.put("renderType", this.getRenderType(component).name());
        return attr;
    }
    @Override
    public void render(ViewContext viewContext, T component, TemplateBody arg3, Writer writer) throws IOException, TemplateException {
        List<?> listData = component.getValueList();
        String keyField = component.getKeyField();
        String varField = component.getVarField();
        Object[] selectVar = component.getSelectValues();
        int index = 0;
        if (listData != null)
            for (Object obj : listData) {
                if (obj == null)
                    continue;
                Object keyValue = null;
                Object varValue = null;
                if (obj instanceof Map) {
                    Map mapData = (Map) obj;
                    keyValue = mapData.get(keyField);
                    varValue = mapData.get(varField);
                } else {
                    keyValue = BeanUtil.readPropertyOrField(obj, keyField);
                    varValue = BeanUtil.readPropertyOrField(obj, varField);
                }
                //���
                String checkedStr = "no";
                String checkedMark = "";
                String titleMark = (this.getRenderType(component) == RenderType.onlyTitle) ? " style='display:none;'" : "";
                for (Object selItem : selectVar)
                    if (keyValue != null && keyValue.equals(selItem) == true) {
                        checkedStr = "";
                        checkedMark = "checked='checked'";
                    } else {
                        checkedStr = "no";
                        checkedMark = "";
                    }
                writer.write("<li index='" + index + "' class='" + checkedStr + "checked'>");
                writer.write("  <a href='javascript:void(0)'>");
                writer.write("    <label>");
                writer.write("      <em></em>");
                writer.write("      <input type='checkbox' forComID='" + component.getComponentID() + "' name='" + component.getName() + "' value='" + keyValue + "' oriData='' " + checkedMark + " " + titleMark + "/>");
                writer.write("      <span>" + varValue + "</span>");
                writer.write("    </label>");
                writer.write("  </a>");
                writer.write("</li> ");
                index++;
            }
    }
    /*----------------------------------------------------------------*/
    public static enum RenderType {
        /**ѡ���ǰ�����ѡ���ı�����ѡ��򣩡�*/
        normal,
        /**ѡ���ֻ�����ı���������ѡ��򣩡�*/
        onlyTitle,
    }
    protected RenderType getRenderType(T component) {
        return RenderType.normal;
    }
}