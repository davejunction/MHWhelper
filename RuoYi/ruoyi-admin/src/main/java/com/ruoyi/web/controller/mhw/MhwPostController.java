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
import com.ruoyi.mhw.domain.MhwPost;
import com.ruoyi.mhw.domain.MhwReply;
import com.ruoyi.mhw.service.IMhwPostService;
import com.ruoyi.mhw.service.IMhwReplyService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.util.ShiroUtils;

/**
 * 帖子 信息操作处理
 * 
 * @author ruoyi
 * @date 2019-06-20
 */
@Controller
@RequestMapping("/mhw/mhwPost")
public class MhwPostController extends BaseController
{
    private String prefix = "mhw/mhwPost";
	
	@Autowired
	private IMhwPostService mhwPostService;
	
	@Autowired
	private IMhwReplyService mhwReplyService;
	
	@RequiresPermissions("mhw:mhwPost:view")
	@GetMapping()
	public String mhwPost()
	{
	    return prefix + "/mhwPost";
	}
	
	/**
	 * 查询帖子列表
	 */
	@RequiresPermissions("mhw:mhwPost:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(MhwPost mhwPost)
	{
		startPage();
        List<MhwPost> list = mhwPostService.selectMhwPostList(mhwPost);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出帖子列表
	 */
	@RequiresPermissions("mhw:mhwPost:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MhwPost mhwPost)
    {
    	List<MhwPost> list = mhwPostService.selectMhwPostList(mhwPost);
        ExcelUtil<MhwPost> util = new ExcelUtil<MhwPost>(MhwPost.class);
        return util.exportExcel(list, "mhwPost");
    }
	
	/**
	 * 新增帖子
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存帖子
	 */
	@RequiresPermissions("mhw:mhwPost:add")
	@Log(title = "帖子", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(MhwPost mhwPost)
	{	
		mhwPost.setCreateBy(ShiroUtils.getLoginName());
		mhwPost.setCreateTime(new Date());
		return toAjax(mhwPostService.insertMhwPost(mhwPost));
	}

	/**
	 * 修改帖子
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap)
	{
		MhwPost mhwPost = mhwPostService.selectMhwPostById(id);
		mmap.put("mhwPost", mhwPost);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存帖子
	 */
	@RequiresPermissions("mhw:mhwPost:edit")
	@Log(title = "帖子", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(MhwPost mhwPost)
	{	
		mhwPost.setUpdateBy(ShiroUtils.getLoginName());
		mhwPost.setUpdateTime(new Date());
		return toAjax(mhwPostService.updateMhwPost(mhwPost));
	}
	
	/**
	 * 删除帖子
	 */
	@RequiresPermissions("mhw:mhwPost:remove")
	@Log(title = "帖子", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(mhwPostService.deleteMhwPostByIds(ids));
	}
	
	/**
     * 查看详细
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap)
    {
    	MhwPost mhwPost = mhwPostService.selectMhwPostById(id);
		mmap.put("mhwPost", mhwPost);
		
		MhwReply mhwReply = new MhwReply();
		mhwReply.setPostId(mhwPost.getId());		
		List<MhwReply> list = mhwReplyService.selectMhwReplyList(mhwReply);
		mmap.put("list", list);
        return prefix + "/detail";
    }
    
    /**
     * 回帖
     */
    @GetMapping("/reply/{id}")
    public String reply(@PathVariable("id") Long id, ModelMap mmap)
    {
    	MhwPost mhwPost = mhwPostService.selectMhwPostById(id);
		mmap.put("mhwPost", mhwPost);
        return prefix + "/reply";
    }
    
    /**
	 * 保存回帖
	 */
	@RequiresPermissions("mhw:mhwPost:reply")
	@Log(title = "保存回帖", businessType = BusinessType.INSERT)
	@PostMapping("/reply")
	@ResponseBody
	public AjaxResult replySave(MhwReply mhwReply)
	{	
		mhwReply.setCreateBy(ShiroUtils.getLoginName());
		mhwReply.setCreateTime(new Date());
		return toAjax(mhwReplyService.insertMhwReply(mhwReply));
	}
	
}
