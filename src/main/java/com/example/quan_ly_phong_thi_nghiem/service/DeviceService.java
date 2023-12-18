package com.example.quan_ly_phong_thi_nghiem.service;

import com.example.quan_ly_phong_thi_nghiem.model.Chemistry;
import com.example.quan_ly_phong_thi_nghiem.model.Device;
import com.example.quan_ly_phong_thi_nghiem.repository.IDeviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    @Autowired
    IDeviceRepo iDeviceRepo;


    public Page<Device> findAllBySubjectName(String name, Pageable pageable) {
        return iDeviceRepo.findAllBySubjectName(name, pageable);
    }

    public List<Device> findAllBySubjectNameAndUsername(String name,String username) {
        return iDeviceRepo.findAllBySubjectNameAndUsername(name,username);
    }

    public List<Device> findAllByAndUsername(String username) {
        return iDeviceRepo.findAllByAndUsername(username);
    }

    public Page<Device> findAll(Pageable pageable) {
        return iDeviceRepo.findAll(pageable);
    }

    public Device findById(long id) {
        return iDeviceRepo.findById(id);
    }

    public List<Device> findAllByName(String name) {
        return iDeviceRepo.findAllByNameContains(name);
    }
    public  Device save(Device device){
        return iDeviceRepo.save(device);
    }
}
