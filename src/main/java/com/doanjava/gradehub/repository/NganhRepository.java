package com.doanjava.gradehub.repository;

import com.doanjava.gradehub.entity.Nganh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface NganhRepository extends JpaRepository<Nganh, String> {

    @Query("SELECT n FROM Nganh n WHERE n.maNganh = :maNganh")
    Optional<Nganh> findByMaNganh(@Param("maNganh") String maNganh);

    @Query("SELECT n FROM Nganh n WHERE n.tenNganh = :tenNganh")
    Optional<Nganh> findByTenNganh(@Param("tenNganh") String tenNganh);
}
