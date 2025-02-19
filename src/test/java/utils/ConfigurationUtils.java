package utils;

import java.util.Properties;

public class ConfigurationUtils {
    private Properties properties , properties1;
    private static ConfigurationUtils configutils;
    String prop ;
    private ConfigurationUtils() {

        properties = PropertiesUtils.loadProperties("src/test/java/config/environment/Production.properties");
        properties1 = PropertiesUtils.loadProperties("src/test/java/config/environment/UserData.properties");
    }

    public static ConfigurationUtils getInstance()
    {
        if(configutils == null)
        {
            configutils = new ConfigurationUtils();
        }
        return configutils;
    }

    public String getBaseUrl()
    {
        prop = properties.getProperty("baseUrl");
        if ((prop != null))
        {
            return prop;
        }
        throw new RuntimeException("base url is not found in property file");
    }

    public String getEmail()
    {
        prop  =properties.getProperty("email");
        return prop ;
    }
    public String getPassword()
    {
        prop = properties.getProperty("password");
        return prop ;
    }

    public String get_firstName()
    {
        prop = properties1.getProperty("firstName");
        return prop;
    }

    public String get_middleName()
    {
        prop = properties1.getProperty("middleName");
        return prop;
    }

    public String get_lastName()
    {
        prop = properties1.getProperty("lastName");
        return prop;
    }
    public String getUserEmail()
    {
        prop  =properties1.getProperty("user_email");
        return prop ;
    }
    public String getUserPassword()
    {
        prop = properties1.getProperty("user_password");
        return prop ;
    }
    public String getUserConfirmPassword()
    {
        prop = properties1.getProperty("user_confirmPassword");
        return prop ;
    }


    public String get_first_name()
    {
        prop = properties.getProperty("first_name");
        return prop;
    }

    public String get_middle_name()
    {
        prop = properties.getProperty("middle_name");
        return prop;
    }

    public String get_last_name()
    {
        prop = properties.getProperty("last_name");
        return prop;
    }

    public String get_telephone()
    {
        prop = properties.getProperty("telephone");
        return prop;
    }

    public String get_company()
    {
        prop = properties.getProperty("company");
        return prop;
    }

    public String get_city()
    {
        prop = properties.getProperty("city");
        return prop;
    }

    public String get_address1()
    {
        prop = properties.getProperty("address1");
        return prop;
    }

    public String get_address2()
    {
        prop = properties.getProperty("address2");
        return prop;
    }

    public String get_state()
    {
        prop = properties.getProperty("state");
        return prop;
    }
    public String get_zipCode()
    {
        prop = properties.getProperty("zipCode");
        return prop;
    }
    public String  get_fax()
    {
        prop = properties.getProperty("fax");
        return prop;
    }
    public String  get_country()
    {
        prop = properties.getProperty("country");
        return prop;
    }

    public String get_em()
    {
        prop = properties.getProperty("em");
        return prop;
    }

    public String get_pass()
    {
        prop = properties.getProperty("pass");
        return prop;
    }




}
