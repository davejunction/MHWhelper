package com.ruoyi.web.controller.mhw;

import java.util.Date;
import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mhw.domain.MhwMonster;
import com.ruoyi.mhw.service.IMhwMonsterService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;

/**
 * 怪物 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-19
 */
@Controller
@RequestMapping("/mhw/mhwMonster")
public class MhwMonsterController extends BaseController
{
    private String prefix = "mhw/mhwMonster";
	
	@Autowired
	private IMhwMonsterService mhwMonsterService;
	
	@RequiresPermissions("mhw:mhwMonster:view")
	@GetMapping()
	public String mhwMonster()
	{
	    return prefix + "/mhwMonster";
	}
	
	/**
	 * 查询怪物列表
	 */
	@RequiresPermissions("mhw:mhwMonster:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MhwMonster mhwMonster)
	{
		startPage();
        List<MhwMonster> list = mhwMonsterService.selectMhwMonsterList(mhwMonster);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出怪物列表
	 */
	@RequiresPermissions("mhw:mhwMonster:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MhwMonster mhwMonster)
    {
    	List<MhwMonster> list = mhwMonsterService.selectMhwMonsterList(mhwMonster);
        ExcelUtil<MhwMonster> util = new ExcelUtil<MhwMonster>(MhwMonster.class);
        return util.exportExcel(list, "mhwMonster");
    }
	
	/**
	 * 新增怪物
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存怪物
	 */
	@RequiresPermissions("mhw:mhwMonster:add")
	@Log(title = "怪物", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(MhwMonster mhwMonster)
	{	
		mhwMonster.setCreateBy(ShiroUtils.getLoginName());
		mhwMonster.setCreateTime(new Date());
		return toAjax(mhwMonsterService.insertMhwMonster(mhwMonster));
	}

	/**
	 * 修改怪物
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		MhwMonster mhwMonster = mhwMonsterService.selectMhwMonsterById(id);
		mmap.put("mhwMonster", mhwMonster);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存怪物
	 */
	@RequiresPermissions("mhw:mhwMonster:edit")
	@Log(title = "怪物", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(MhwMonster mhwMonster)
	{	
		mhwMonster.setUpdateBy(ShiroUtils.getLoginName());
		mhwMonster.setUpdateTime(new Date());
		return toAjax(mhwMonsterService.updateMhwMonster(mhwMonster));
	}
	
	/**
	 * 删除怪物
	 */
	@RequiresPermissions("mhw:mhwMonster:remove")
	@Log(title = "怪物", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(mhwMonsterService.deleteMhwMonsterByIds(ids));
	}
	
}
