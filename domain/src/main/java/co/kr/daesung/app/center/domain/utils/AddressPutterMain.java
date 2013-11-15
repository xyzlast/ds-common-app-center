package co.kr.daesung.app.center.domain.utils;

import co.kr.daesung.app.center.domain.configs.DomainConfiguration;
import co.kr.daesung.app.center.domain.services.AddressService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;

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
        AddressService addressService = context.getBean(AddressService.class);
        System.out.println("clear all data.....");
        addressService.clearAllAddresses();
        File directory = new File(args[0]);
        File[] files = directory.listFiles();

        for(File file : files) {
            if(!file.isDirectory() && file.getAbsolutePath().endsWith(".txt")) {
                System.out.println(file.getAbsolutePath());
                System.out.print("add Si, SiGunGu, Road, EupMyon Data");
                addressService.insertBaseDataFromFile(file.getAbsolutePath(), "CP949");
                System.out.print("add detail addresses and post codes");
                addressService.insertAddressFromFile(file.getAbsolutePath(), "CP949");
                System.out.println("insert ok. move next..");
            }
        }
    }
}
