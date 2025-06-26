package com.doanjava.gradehub.service;

import com.doanjava.gradehub.dto.NganhDto;
import com.doanjava.gradehub.dto.ChuyenNganhDto;
import java.util.List;

public interface ReferenceDataService {
    List<NganhDto> getAllNganh();
    List<ChuyenNganhDto> getChuyenNganhByNganh(String maNganh);
}
