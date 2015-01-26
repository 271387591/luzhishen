package com.ozstrategy.webapp.security;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;


public class ValidatorExtensionPostProcessor implements BeanFactoryPostProcessor {
    //~ Instance fields --------------------------------------------------------------------------------------------------

    private List validationConfigLocations;
    private String validatorFactoryBeanName = "validatorFactory";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) {
        if (configurableListableBeanFactory.containsBean(validatorFactoryBeanName)) {
            BeanDefinition validatorFactoryBeanDefinition = configurableListableBeanFactory.getBeanDefinition(
                    validatorFactoryBeanName);
            MutablePropertyValues propertyValues = validatorFactoryBeanDefinition.getPropertyValues();
            PropertyValue propertyValue = propertyValues.getPropertyValue(
                    "validationConfigLocations");

            // value is expected to be a list.
            List existingValidationConfigLocations = (List) propertyValue.getValue();
            existingValidationConfigLocations.addAll(validationConfigLocations);
        }
    }

    public void setValidationConfigLocations(List validationConfigLocations) {
        this.validationConfigLocations = validationConfigLocations;
    }

    public void setValidatorFactoryBeanName(String validatorFactoryBeanName) {
        this.validatorFactoryBeanName = validatorFactoryBeanName;
    }
} 
