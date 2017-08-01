package cn.itcast.bos.web.action.transit;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.transit.TransitInfo;
import cn.itcast.bos.service.transit.TransitInfoService;
import cn.itcast.bos.web.action.common.BaseAction;

@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class transitAction extends BaseAction<TransitInfo>{
	
	@Autowired
	private TransitInfoService TransitInfoService;
	
	private String wayBillIds;
	
	
	public void setWayBillIds(String wayBillIds) {
		this.wayBillIds = wayBillIds;
	}


	@Action(value="transit_creat",results={@Result(name="success",type="json")})
	public String creat(){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			
			TransitInfoService.createTransits(wayBillIds);
			result.put("success", true);
			result.put("msg", "成功");
		} catch (Exception e) {
			
			result.put("success", false);
			result.put("msg", "失败");
		}
		return SUCCESS;
	}
}
