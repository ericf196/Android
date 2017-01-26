package com.optimussoftware.beacons.utils;

import com.optimussoftware.beacons.Configuration;

import java.util.UUID;

/**
 * Created by william.castillo@optimus-software.com
 */
public class BeaconRegister {
    private UUID uuid;
    private String namespace;
    private String instance;
    private String mac;
    private int type;
    private long time = -1;
    public BeaconRegister(String _uuid, String mac, int type){
        uuid = UUID.fromString(_uuid);
        this.type = type;
        this.mac = mac;
        time = System.currentTimeMillis();
    }

    public BeaconRegister(String namespace, String instance, String mac, int type){
        this.namespace = namespace;
        this.instance = instance;
        this.type = type;
        this.mac = mac;
        time = System.currentTimeMillis();
    }

    private boolean checkTime(int __time){
        int mark = (int)(System.currentTimeMillis() - time)/1000;
        return mark > __time;
    }
    public boolean check(Configuration conf){
        boolean sw = false;
        if(checkTime(conf.getSeconds())){
            time = System.currentTimeMillis();
            sw = true;
        }
        return sw;
    }

    public boolean exist(String _uuid){
        return uuid.compareTo(UUID.fromString(_uuid)) == 0;
    }

    public boolean exist(String namespace, String instance){
        return this.namespace.compareTo(namespace) == 0 && this.instance.compareTo(instance) == 0;
    }
}
