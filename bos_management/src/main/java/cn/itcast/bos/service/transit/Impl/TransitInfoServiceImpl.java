package cn.itcast.bos.service.transit.Impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillRepository;
import cn.itcast.bos.dao.transitInfo.TransitInfoRepository;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.TransitInfoService;
@Service
@Transactional
public class TransitInfoServiceImpl implements TransitInfoService {
	
	@Autowired
	private TransitInfoRepository transitInfoRepository;
	@Autowired
	private WayBillRepository wayBillRepository;

	@Override
	public void createTransits(String wayBillIds) {
		if (StringUtils.isNotEmpty(wayBillIds)) {
			String[] strings = wayBillIds.split(",");
			for (String string : strings) {
				WayBill wayBill = wayBillRepository.findOne(Integer.parseInt(string));
				if (wayBill.getSignStatus()==1) {
					//可以中转
					TransitInfo transitInfo = new TransitInfo();
					transitInfo.setWayBill(wayBill);
					transitInfo.setStatus("出入库中转");
					transitInfoRepository.save(transitInfo);
					
					wayBill.setSignStatus(2);
					
				}
			}
		}
	}

}
