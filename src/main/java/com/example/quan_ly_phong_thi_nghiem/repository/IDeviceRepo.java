package com.example.quan_ly_phong_thi_nghiem.repository;

import com.example.quan_ly_phong_thi_nghiem.model.Chemistry;
import com.example.quan_ly_phong_thi_nghiem.model.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDeviceRepo extends JpaRepository<Device, Long> {
    Page<Device> findAllBySubjectName(String name, Pageable pageable);
    @Query(value = "select d.* from Device as d join subject s on s.id = d.subject_id where s.name=:nameS and d.id not in " +
            "(select rd.devices_id from receiptdto_devices rd join receiptdto on receiptdto.id = rd.receiptdto_id " +
            "join account a on a.id = receiptdto.account_id where a.username=:username)", nativeQuery = true)
    List<Device> findAllBySubjectNameAndUsername(@Param("nameS") String nameS, @Param("username") String username);

    @Query(value = "select d.* from Device as d  where d.id not in " +
            "(select rd.devices_id from receiptdto_devices rd join receiptdto on receiptdto.id = rd.receiptdto_id " +
            "join account a on a.id = receiptdto.account_id where a.username=:username)", nativeQuery = true)
    List<Device> findAllByAndUsername(@Param("username") String username);


    List<Device> findAllByNameContains(String name);

    Device findById(long id);

}
