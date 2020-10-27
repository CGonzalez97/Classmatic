package com.salesianostriana.classmatic.ficheros;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="storage")
public class StorageProperties {


    private String location = "archivos-dir";//ruta de almacenamiento de los archivos

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}