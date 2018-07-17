package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dao.DeviceMapper;
import com.xz.managersystem.entity.TDeviceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    DeviceMapper devMapper;

    public int getCount(String group) {
        return devMapper.getCount(group);
    }

    public List<TDeviceInfo> getDeviceList(ConditionParams params) {
        if (params.getStart() == null || params.getRows() == null) {
            return devMapper.selectList(params.getGroup());
        } else {
            return devMapper.selectPage(params);
        }
    }

    public TDeviceInfo getDevice(String label) {
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException("无效的设备名");
        }

        TDeviceInfo devInfo = devMapper.selectByLabel(label);
        if (devInfo == null){
            throw new EntityNotFoundException("设备" + label + "不存在");
        }
        return devInfo;
    }

    public void addDevice(TDeviceInfo devInfo) {
        devInfo.setLabel(UtilTools.getUUID(8));
        if (devInfo.getName() == null)
            devInfo.setName("");
        if (devInfo.getBrand() == null)
            devInfo.setBrand("");
        if (devInfo.getModel() == null)
            devInfo.setModel("");
        if (devMapper.insert(devInfo) <= 0) {
            throw new RuntimeException("添加设备失败");
        }
    }

    public void updateDevice(TDeviceInfo devInfo) {
        TDeviceInfo devBase = getDevice(devInfo.getLabel());
        if (devInfo.getName() != null)
            devBase.setName(devInfo.getName());
        if (devInfo.getBrand() != null)
            devBase.setBrand(devInfo.getBrand());
        if (devInfo.getModel() != null)
            devBase.setModel(devInfo.getModel());
        if (devMapper.updateByLabel(devBase) <= 0) {
            throw new RuntimeException("更新设备" + devBase.getLabel() + "失败");
        }
    }

    public void deleteDevice(String label){
        TDeviceInfo devInfo = getDevice(label);
        if (devMapper.deleteByLabel(label) <= 0) {
            throw new RuntimeException("删除设备" + label + "失败");
        }
    }
}
