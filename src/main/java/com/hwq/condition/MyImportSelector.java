package com.hwq.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    /**
     *
     * @param annotationMetadata 当前标注 @Import注解的类的所有注解信息
     * @return 返回值，就是要导入到容器中的组件类的全类名，该方法可以返回空数组但不要返回null,因为返回了null会导致空指针异常
     */
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.hwq.bean.Red","com.hwq.bean.Blue"};
    }
}
