package com.doanjava.gradehub.repository;

import com.doanjava.gradehub.entity.ChuyenNganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChuyenNganhRepository extends JpaRepository<ChuyenNganh, String> {

    @Query("SELECT c FROM ChuyenNganh c WHERE c.maChuyenNganh = :maChuyenNganh")
    Optional<ChuyenNganh> findByMaChuyenNganh(@Param("maChuyenNganh") String maChuyenNganh);

    @Query("SELECT c FROM ChuyenNganh c WHERE c.nganh.maNganh = :maNganh")
    List<ChuyenNganh> findByMaNganh(@Param("maNganh") String maNganh);
}
