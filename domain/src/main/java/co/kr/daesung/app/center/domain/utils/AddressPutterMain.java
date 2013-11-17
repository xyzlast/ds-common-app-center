package co.kr.daesung.app.center.domain.utils;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.services.AddressPutService;
import co.kr.daesung.app.center.domain.services.AddressSearchService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ykyoon
 * Date: 11/15/13
 * Time: 2:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressPutterMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(DomainConfiguration.class);
        AddressPutService addressPutService = context.getBean(AddressPutService.class);

        System.out.println("clear all data.....");
        addressPutService.clearAllAddresses();
        File directory = new File(args[0]);
        File[] files = directory.listFiles();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        for(File file : files) {
            if(!file.isDirectory() && file.getAbsolutePath().endsWith(".txt")) {
                System.out.println(file.getAbsolutePath());
                System.out.print(String.format("[%s] add Base Data", dateFormat.format(new Date())));
                addressPutService.insertBaseDataFromFile(file.getAbsolutePath(), "CP949");
                System.out.print(String.format("[%s] add detail addresses", dateFormat.format(new Date())));
                addressPutService.insertAddressFromFile(file.getAbsolutePath(), "CP949");
                System.out.println(String.format("[%s] end ==", dateFormat.format(new Date())));
            }
        }
        return;
    }
}
