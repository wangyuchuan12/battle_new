package com.wyc.common.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


@Service
public class SpringBootConfig implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            InputStream is = null;
            try {
                is = new FileInputStream("application.properties");
            }catch (Exception e){
                try {
                    File file = new ClassPathResource("application.properties").getFile();
                    is = new FileInputStream(file);
                }catch (Exception e2){
                    is =  new FileInputStream("/Users/macos/Desktop/application.properties");
                }
            }
            Properties properties = new Properties();
            properties.load(is);
            PropertiesPropertySource propertySource = new PropertiesPropertySource("dynamic", properties);
            environment.getPropertySources().addLast(propertySource);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
