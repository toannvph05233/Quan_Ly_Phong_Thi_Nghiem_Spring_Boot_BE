package com.example.quan_ly_phong_thi_nghiem.service;

import com.example.quan_ly_phong_thi_nghiem.model.Chemistry;
import com.example.quan_ly_phong_thi_nghiem.model.Device;
import com.example.quan_ly_phong_thi_nghiem.repository.IChemistryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChemistryService {
    @Autowired
    IChemistryRepo iChemistryRepo;

    public Page<Chemistry> findAllBySubjectName(String name, Pageable pageable){
        return iChemistryRepo.findAllBySubjectName(name, pageable);
    }
    public List<Chemistry> findAllBySubjectNameAndUsername(String name,String username){
        return iChemistryRepo.findAllBySubjectNameAndUsername(name,username);
    }

    public List<Chemistry> findAllByAndUsername(String username) {
        return iChemistryRepo.findAllByAndUsername(username);
    }

    public Page<Chemistry> findAll(Pageable pageable){
        return  iChemistryRepo.findAll(pageable);
    }


    public List<Chemistry> findAllByName(String name){
        return iChemistryRepo.findAllByNameContains(name);
    }


    public  Chemistry findById(long id){
        return iChemistryRepo.findById(id);
    }
    public  Chemistry save(Chemistry chemistry){
        return iChemistryRepo.save(chemistry);
    }


}
