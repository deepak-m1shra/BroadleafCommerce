/*-
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2022 Broadleaf Commerce
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
package org.broadleafcommerce.common.notification.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.notification.service.type.Notification;
import org.broadleafcommerce.common.notification.service.type.SMSNotification;
import org.springframework.stereotype.Service;

/**
 * @author Nick Crum ncrum
 */
@Service("blSMSNotificationService")
public class DefaultSMSNotificationServiceImpl implements NotificationService {

    protected final Log LOG = LogFactory.getLog(DefaultSMSNotificationServiceImpl.class);

    @Override
    public boolean canHandle(Class<? extends Notification> clazz) {
        return SMSNotification.class.isAssignableFrom(clazz);
    }

    @Override
    public void sendNotification(Notification notification) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Attempt to send sms notification of type " + notification.getType().getFriendlyType());
        }
    }
}
