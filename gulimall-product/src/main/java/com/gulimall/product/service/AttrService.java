package com.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulimall.common.vo.PageVo;
import com.gulimall.product.entity.AttrEntity;
import com.gulimall.product.vo.AttrGroupRelationVo;
import com.gulimall.product.vo.AttrRespVo;
import com.gulimall.product.vo.AttrVo;
import com.gulimall.service.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author aqiang9
 * @email 2903780002@qq.com
 * @date 2020-06-10 11:26:28
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 获取 规格参数
     * @param pageParams 分页参数
     * @param attrType 参数类型
     * @param categoryId 分类 id
     */
    PageUtils queryList(PageVo pageParams, int attrType, Long categoryId);

    /**
     * 保存属性
     * @param attrVo 属性信息
     */
    void saveAttrInfo(AttrVo attrVo);

    /**
     * 获取attr详细信息
     * @param attrId 属性id
     */
    AttrRespVo getAttrInfo(Long attrId);

    /**
     * 修改详细信息
     * @param attrVo 修改后的数据
     */
    void updateAttrInfo(AttrVo attrVo);

    /**
     * 删除属性
     * @param attrIds 属性id
     */

    void removeAttrInfo(List<Long> attrIds, Long attrType);


    List<AttrEntity> getAttrRelationByAttrGroupId(Long attrGroupId);


    void removeRelation(List<AttrGroupRelationVo> relationVo);

    /**
     *
    */
    PageUtils getNoRelationAttr(PageVo pageParams, Long attrGroupId);
}

