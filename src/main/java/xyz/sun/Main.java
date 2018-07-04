package xyz.sun;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.util.List;

/*
 * 日志测试
 * @ author sundw
 * @ date 2018/7/4
 */
public class Main {
    public static void main(String[] args) {
        readXML();
        System.out.println("-------------------------");
        saveXML();
        System.out.println("-------------------------");
        readXML();
    }

    public static void readXML() {
        Configurations configs = new Configurations();
        try {
            XMLConfiguration config = configs.xml("paths.xml");
            String stage = config.getString("processing[@stage]");
            System.out.println(stage);
            List<String> paths = config.getList(String.class, "processing.paths.path");
            System.out.println(paths);
            System.out.println(config.getString("processing.paths.path(0)"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * save不成功
     */
    public static void saveXML() {
        Configurations configs = new Configurations();
        FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder("paths.xml");
        try {
            XMLConfiguration config = builder.getConfiguration();

            config.addProperty("newProperty", "newValue");
            builder.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

    }

}
