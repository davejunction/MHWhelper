package com.ruoyi.web.controller.mhw;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.mhw.domain.MhwReply;
import com.ruoyi.mhw.service.IMhwReplyService;

/**
 * 回帖 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-20
 */
@Controller
@RequestMapping("/mhw/mhwReply")
public class MhwReplyController extends BaseController {
	private String prefix = "mhw/mhwReply";

	@Autowired
	private IMhwReplyService mhwReplyService;

	@RequiresPermissions("mhw:mhwReply:view")
	@GetMapping()
	public String mhwReply() {
		return prefix + "/mhwReply";
	}

	/**
	 * 查询回帖列表
	 */
	@RequiresPermissions("mhw:mhwReply:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MhwReply mhwReply) {
		startPage();
		List<MhwReply> list = mhwReplyService.selectMhwReplyList(mhwReply);
		return getDataTable(list);
	}

	/**
	 * 导出回帖列表
	 */
	@RequiresPermissions("mhw:mhwReply:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(MhwReply mhwReply) {
		List<MhwReply> list = mhwReplyService.selectMhwReplyList(mhwReply);
		ExcelUtil<MhwReply> util = new ExcelUtil<MhwReply>(MhwReply.class);
		return util.exportExcel(list, "mhwReply");
	}

	/**
	 * 删除回帖
	 */
	@RequiresPermissions("mhw:mhwReply:remove")
	@Log(title = "回帖", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(mhwReplyService.deleteMhwReplyByIds(ids));
	}

}
