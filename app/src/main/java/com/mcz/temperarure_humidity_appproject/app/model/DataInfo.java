package com.mcz.temperarure_humidity_appproject.app.model;

import java.io.Serializable;

/**
 * Created by mcz on 2018/1/10.
 */

public class DataInfo implements Serializable{
    private String DeviceId ;
    private String DeviceName;
    private String DeviceStatus;
    private String DeviceType;
    private String Devicetemperature;
    private String Devicehumidity;
    private String error_code;
    ////////////////////////////////
    private String lasttime;
    private String Devicetimestamp;
    private String network;
    private String software;
    private String battery;

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public DataInfo(){

    }

    public String getAlldata(){
        return DeviceId+"-"+DeviceName+"-"+DeviceStatus+"-"+DeviceType+"-"+Devicetemperature+"-"+Devicehumidity+"-"+error_code+"-"+lasttime+"-"+Devicetimestamp+"-"+message;
    }
    public void setDevicetemperature(String dv_temperature) {
        Devicetemperature = dv_temperature;
    }

    public String getDevicetemperature() {
        return Devicetemperature;
    }


    public void setDevicehumidity(String dv_humidity) {
        Devicehumidity = dv_humidity;
    }

    public String getDevicehumidity() {
        return Devicehumidity;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }



    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }


    public String getDeviceStatus() {
        return DeviceStatus;
    }

    public void setDevicetimestamp(String timestamp) {
        Devicetimestamp = timestamp;
    }



    public String getDevicetimestamp() {
        return Devicetimestamp;
    }

    public void setDeviceStatus(String deviceStatus) {
        DeviceStatus = deviceStatus;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        this.DeviceId = deviceId;
    }
    public String getGatewayId() {
        return DeviceId;
    }

    public void setGatewayId(String deviceId) {
        this.DeviceId = deviceId;
    }
}
