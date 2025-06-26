package com.doanjava.gradehub.repository;

import com.doanjava.gradehub.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Integer> {

    @Query("SELECT n FROM NguoiDung n WHERE n.email = :email")
    Optional<NguoiDung> findByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM NguoiDung n WHERE n.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT n FROM NguoiDung n WHERE n.email = :email AND n.matKhauHash = :matKhauHash")
    Optional<NguoiDung> findByEmailAndMatKhauHash(@Param("email") String email, @Param("matKhauHash") String matKhauHash);

    @Query("SELECT n FROM NguoiDung n WHERE " +
           "(:search IS NULL OR LOWER(n.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:vaiTro IS NULL OR n.vaiTro = :vaiTro) " +
           "ORDER BY n.ngayTao DESC")
    List<NguoiDung> findBySearchAndVaiTro(@Param("search") String search, @Param("vaiTro") NguoiDung.VaiTro vaiTro);

    @Query("SELECT n FROM NguoiDung n WHERE n.vaiTro = :vaiTro")
    List<NguoiDung> findByVaiTro(@Param("vaiTro") NguoiDung.VaiTro vaiTro);
}
