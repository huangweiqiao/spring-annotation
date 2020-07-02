package com.hwq.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 创建一个工厂bean,
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    //返回一个Color对象会添加到容器中
    @Override
    public Color getObject() throws Exception {
        return new Color();
    }

    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    /**
     *
     * @return true:表示单例，false：表示多例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
