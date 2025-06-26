package com.doanjava.gradehub.repository;

import com.doanjava.gradehub.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {

    @Query("SELECT s FROM SinhVien s WHERE s.nguoiDung.id = :nguoiDungId")
    Optional<SinhVien> findByNguoiDungId(@Param("nguoiDungId") Integer nguoiDungId);

    @Query("SELECT s FROM SinhVien s WHERE s.maSinhVien = :maSinhVien")
    Optional<SinhVien> findByMaSinhVien(@Param("maSinhVien") String maSinhVien);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM SinhVien s WHERE s.maSinhVien = :maSinhVien")
    boolean existsByMaSinhVien(@Param("maSinhVien") String maSinhVien);
}
