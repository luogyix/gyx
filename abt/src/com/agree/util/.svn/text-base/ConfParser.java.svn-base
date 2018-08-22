/**
 * 
 */
package com.agree.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * 解析配置密文，加载进spring容器
 * @ClassName: ConfParser 
 * @company agree   
 * @author haoruibing
 * @date 2012-12-4  
 *
 */
public class ConfParser extends PropertyPlaceholderConfigurer  
{  
    @Override  
    protected void processProperties(  
            ConfigurableListableBeanFactory beanFactory, Properties props)  
            throws BeansException {  
          String UserName = props.getProperty("jdbc.username");  
        if (UserName != null ) {  
            try {
				props.setProperty("jdbc.username", DES.getDesString(UserName));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}  
        }  
        String password = props.getProperty("jdbc.password");  
        if (password != null ) {  
            try {
				props.setProperty("jdbc.password", DES.getDesString(password));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}  
        }  
        super.processProperties(beanFactory, props);  
  
    }  
}  
