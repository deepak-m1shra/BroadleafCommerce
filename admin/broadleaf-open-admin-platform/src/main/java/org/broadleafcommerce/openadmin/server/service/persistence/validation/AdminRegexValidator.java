/*-
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2023 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 * 
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package org.broadleafcommerce.openadmin.server.service.persistence.validation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.config.service.SystemPropertiesService;
import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Map;

@Component("blAdminRegexValidator")
public class AdminRegexValidator extends ValidationConfigurationBasedPropertyValidator {

    protected static final Log LOG = LogFactory.getLog(AdminRegexValidator.class);

    @Resource("blSystemPropertiesService")
    protected SystemPropertiesService propertiesService;

    @Override
    public boolean validateInternal(Entity entity,
                                    Serializable instance,
                                    Map<String, FieldMetadata> entityFieldMetadata,
                                    Map<String, String> validationConfiguration,
                                    BasicFieldMetadata propertyMetadata,
                                    String propertyName,
                                    String value) {
        String regexExpression = validationConfiguration.get("regex");
        if (propertiesService == null && StringUtils.isEmpty(regexExpression)){
            LOG.warn("regex validator for field "+propertyName+" is not applied because dependency is not injected, most probably because you used validationImplementation = " +
                    "\"FULLY_QUALIFIED_CLASSNAME\" instead of validationImplementation = \"SPRING_BEAN_NAME\", and regex was not specified");
            return true;
        }
        if (StringUtils.isEmpty(validationConfiguration.get("regexPropertyName")) || StringUtils.isEmpty(propertiesService.resolveSystemProperty(validationConfiguration.get("regexPropertyName")))) {
            LOG.warn("RegEx is not defined");
        }

        try {
            String regex = propertiesService.resolveSystemProperty(validationConfiguration.get("regexPropertyName"));
            return value != null && value.matches(regex);
        } catch (Exception e){
            LOG.error(e);
            return false;
        }
    }
}
