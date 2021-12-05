package com.store.ioc.reader;

import com.store.ioc.domain.BeanDefinition;
import java.util.List;

public interface BeanDefinitionReader {
    List<BeanDefinition> readBeanDefinitions();
}
