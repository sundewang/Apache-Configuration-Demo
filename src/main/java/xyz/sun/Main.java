package xyz.sun;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 测试
 * @author sundw
 * @date 2018/7/4
 */
public class Main {
    public static void main(String[] args) throws Exception {
        readXML();
        System.out.println("-------------------------");
        saveXML();
        System.out.println("-------------------------");
        readXML();
//        reloadFile();
    }

    /**
     * 读取xml文件，可以使用XPath
     */
    public static void readXML() {
        Configurations configs = new Configurations();
        try {
            XMLConfiguration config = configs.xml("conf/paths.xml");
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
     * 调用保存的方法
     */
    public static void saveXML() {
        Configurations configs = new Configurations();
        FileBasedConfigurationBuilder<XMLConfiguration> builder = configs.xmlBuilder("conf/paths.xml");
        try {
            XMLConfiguration config = builder.getConfiguration();

            config.addProperty("newProperty", "newValue");
            builder.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

    }

    /**
     * 定时加载配置文件
     * @throws Exception
     */
    public static void reloadFile() throws Exception {
        Parameters parameters = new Parameters();
        // 不要使用resources下的文件，它们会被复制到target中
        File file = new File("conf/test.properties");
        ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(parameters.fileBased()
                                .setFile(file));
        PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
                null, 1, TimeUnit.SECONDS);
        trigger.start();
        // 添加事件监听器
        /*builder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST,
                new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        builder.getReloadingController().checkForReloading(null);
                    }
                });*/
        while (true) {
            /**
             * 需要重新从 builder 中获取 Configuration 才会得到重新加载的配置项
             */
            FileBasedConfiguration configuration = builder.getConfiguration();
            System.out.println(configuration.getProperty("database.url"));
            Thread.sleep(2000);
        }
    }

}
