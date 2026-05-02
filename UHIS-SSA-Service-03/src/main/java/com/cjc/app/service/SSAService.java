package com.cjc.app.service;

import java.util.List;
import com.cjc.app.entity.SSAData;

public interface SSAService {

  SSAData addSSAData(SSAData ssaData);

  List<SSAData> getSSAData();

  SSAData getSSA(String ssa);
}
