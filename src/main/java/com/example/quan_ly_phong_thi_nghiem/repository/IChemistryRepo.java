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

public interface IChemistryRepo extends JpaRepository<Chemistry, Long> {
    Page<Chemistry> findAllBySubjectName(String name, Pageable pageable);

    @Query(value = "select c.* from Chemistry c join subject s on s.id = c.subject_id where s.name=:nameS and c.id not in " +
            "( select rd.chemistry_id from receiptdto_chemistry rd join receiptdto on receiptdto.id = rd.receiptdto_id " +
            "join account a on a.id = receiptdto.account_id where a.username=:username)", nativeQuery = true)
    List<Chemistry> findAllBySubjectNameAndUsername(@Param("nameS") String nameS, @Param("username") String username);

    @Query(value = "select c.* from Chemistry c where c.id not in " +
            "( select rd.chemistry_id from receiptdto_chemistry rd join receiptdto on receiptdto.id = rd.receiptdto_id " +
            "join account a on a.id = receiptdto.account_id where a.username=:username)", nativeQuery = true)
    List<Chemistry> findAllByAndUsername(@Param("username") String username);


    List<Chemistry> findAllByNameContains(String name);
    Chemistry findById(long id);

}
