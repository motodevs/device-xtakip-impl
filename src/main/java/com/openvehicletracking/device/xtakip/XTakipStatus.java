package com.openvehicletracking.device.xtakip;

import com.openvehicletracking.core.GsonFactory;
import com.openvehicletracking.core.JsonDeserializeable;
import com.openvehicletracking.core.JsonSerializeable;

/**
 * Created by yo on 08/06/2017.
 */
public class XTakipStatus implements JsonSerializeable, JsonDeserializeable<XTakipStatus> {

    private String raw;
    private Boolean input1Active;
    private Boolean input2Active;
    private Boolean input3Active;
    private Boolean ignitiKeyOff;
    private Boolean batteryCutted;
    private Boolean output1Active;
    private Boolean output2Active;
    private Boolean output3Active;
    private Boolean outOfTempLimit;
    private Boolean outOfSpeedLimit;
    private Boolean gprsOpendOnOversea;
    private Boolean deltaDistinaceOpened;
    private Boolean offlineRecord;
    private Boolean invalidRTC;
    private Boolean engineStopActive;
    private Boolean maxStopResuming;
    private Boolean idleStatusResuming;
    private Boolean gSensorAlarmResuming;
    private Boolean input4Active;
    private Boolean input5Active;
    private Boolean externalPowerCut;

    @Override
    public XTakipStatus fromJsonString(String json) {
        return GsonFactory.getGson().fromJson(json, this.getClass());
    }

    @Override
    public String asJsonString() {
        return GsonFactory.getGson().toJson(this);
    }

    public Boolean getInput1Active() {
        return input1Active;
    }

    public void setInput1Active(Boolean input1Active) {
        this.input1Active = input1Active;
    }

    public Boolean getInput2Active() {
        return input2Active;
    }

    public void setInput2Active(Boolean input2Active) {
        this.input2Active = input2Active;
    }

    public Boolean getIgnitiKeyOff() {
        return ignitiKeyOff;
    }

    public void setIgnitiKeyOff(Boolean ignitiKeyOff) {
        this.ignitiKeyOff = ignitiKeyOff;
    }

    public Boolean getBatteryCutted() {
        return batteryCutted;
    }

    public void setBatteryCutted(Boolean batteryCutted) {
        this.batteryCutted = batteryCutted;
    }

    public Boolean getOutput1Active() {
        return output1Active;
    }

    public void setOutput1Active(Boolean output1Active) {
        this.output1Active = output1Active;
    }

    public Boolean getOutput2Active() {
        return output2Active;
    }

    public void setOutput2Active(Boolean output2Active) {
        this.output2Active = output2Active;
    }

    public Boolean getOutput3Active() {
        return output3Active;
    }

    public void setOutput3Active(Boolean output3Active) {
        this.output3Active = output3Active;
    }

    public Boolean getOutOfTempLimit() {
        return outOfTempLimit;
    }

    public void setOutOfTempLimit(Boolean outOfTempLimit) {
        this.outOfTempLimit = outOfTempLimit;
    }

    public Boolean getOutOfSpeedLimit() {
        return outOfSpeedLimit;
    }

    public void setOutOfSpeedLimit(Boolean outOfSpeedLimit) {
        this.outOfSpeedLimit = outOfSpeedLimit;
    }

    public Boolean getGprsOpendOnOversea() {
        return gprsOpendOnOversea;
    }

    public void setGprsOpendOnOversea(Boolean gprsOpendOnOversea) {
        this.gprsOpendOnOversea = gprsOpendOnOversea;
    }

    public Boolean getDeltaDistinaceOpened() {
        return deltaDistinaceOpened;
    }

    public void setDeltaDistinaceOpened(Boolean deltaDistinaceOpened) {
        this.deltaDistinaceOpened = deltaDistinaceOpened;
    }

    public Boolean getOfflineRecord() {
        return offlineRecord;
    }

    public void setOfflineRecord(Boolean offlineRecord) {
        this.offlineRecord = offlineRecord;
    }

    public Boolean getInvalidRTC() {
        return invalidRTC;
    }

    public void setInvalidRTC(Boolean invalidRTC) {
        this.invalidRTC = invalidRTC;
    }

    public Boolean getEngineStopActive() {
        return engineStopActive;
    }

    public void setEngineStopActive(Boolean engineStopActive) {
        this.engineStopActive = engineStopActive;
    }

    public Boolean getMaxStopResuming() {
        return maxStopResuming;
    }

    public void setMaxStopResuming(Boolean maxStopResuming) {
        this.maxStopResuming = maxStopResuming;
    }

    public Boolean getIdleStatusResuming() {
        return idleStatusResuming;
    }

    public void setIdleStatusResuming(Boolean idleStatusResuming) {
        this.idleStatusResuming = idleStatusResuming;
    }

    public Boolean getgSensorAlarmResuming() {
        return gSensorAlarmResuming;
    }

    public void setgSensorAlarmResuming(Boolean gSensorAlarmResuming) {
        this.gSensorAlarmResuming = gSensorAlarmResuming;
    }

    public Boolean getInput4Active() {
        return input4Active;
    }

    public void setInput4Active(Boolean input4Active) {
        this.input4Active = input4Active;
    }

    public Boolean getInput5Active() {
        return input5Active;
    }

    public void setInput5Active(Boolean input5Active) {
        this.input5Active = input5Active;
    }

    public Boolean getExternalPowerCut() {
        return externalPowerCut;
    }

    public void setExternalPowerCut(Boolean externalPowerCut) {
        this.externalPowerCut = externalPowerCut;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Boolean getInput3Active() {
        return input3Active;
    }

    public void setInput3Active(Boolean input3Active) {
        this.input3Active = input3Active;
    }
}
