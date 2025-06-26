package com.doanjava.gradehub.repository;

import com.doanjava.gradehub.entity.GiangVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {

    @Query("SELECT g FROM GiangVien g WHERE g.nguoiDung.id = :nguoiDungId")
    Optional<GiangVien> findByNguoiDungId(@Param("nguoiDungId") Integer nguoiDungId);

    @Query("SELECT g FROM GiangVien g WHERE g.maGiangVien = :maGiangVien")
    Optional<GiangVien> findByMaGiangVien(@Param("maGiangVien") String maGiangVien);

    @Query("SELECT CASE WHEN COUNT(g) > 0 THEN true ELSE false END FROM GiangVien g WHERE g.maGiangVien = :maGiangVien")
    boolean existsByMaGiangVien(@Param("maGiangVien") String maGiangVien);
}
