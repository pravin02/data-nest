package org.pk.datanest.puller.service;

import org.pk.datanest.commons.constant.ConnectionMode;
import org.pk.datanest.puller.model.Client;

import java.util.Arrays;
import java.util.List;


public class StaticDataService {

    public static List<Client> getClients() {
        return Arrays.asList(
                new Client("1", "Pravin", ConnectionMode.API),
                new Client("2", "Mahesh", ConnectionMode.WEB_SOCKET),
                new Client("3", "Samadhan", ConnectionMode.SFT),
                new Client("4", "Ramesh", ConnectionMode.FILE_SYSTEM)
        );
    }

}
